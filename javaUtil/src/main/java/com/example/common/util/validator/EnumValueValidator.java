package com.example.common.util.validator;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueValidator.Validator.class)
public @interface EnumValueValidator {

    Logger log = LoggerFactory.getLogger(EnumValueValidator.class);

    String message() default "参数有误";

    Class<? extends Enum<?>> enumClass();

    String enumMethod();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<EnumValueValidator , Object> {
        private Class<? extends Enum<?>> enumClass;
        private String enumMethod;

        @Override
        public void initialize(EnumValueValidator constraintAnnotation) {
            enumMethod = constraintAnnotation.enumMethod();
            enumClass = constraintAnnotation.enumClass();
        }
        @Override
        public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
            if (StringUtils.isEmpty(o)) return Boolean.TRUE;
            if (enumClass == null || StringUtils.isEmpty(enumMethod)) return Boolean.TRUE;
            Class<?> vclass = o.getClass();
            try {
                Object obj = vclass.newInstance();
                Method method = vclass.getMethod(enumMethod);
                if (!Boolean.TYPE.equals(method.getReturnType()) &&
                        !Boolean.class.equals(method.getReturnType())) {
                    throw new RuntimeException("校验方法不是布尔类型!");
                }
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException("校验方法不是静态方法!");
                }
                method.setAccessible(true);
                Boolean res = (Boolean) method.invoke(obj);
                return res != null ? res : false;
            } catch (NoSuchMethodException e) {
                log.error("NoSuchMethodException:{}" ,e);
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                log.error("IllegalAccessException:{}" ,e);
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                log.error("InvocationTargetException:{}" ,e);
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                log.error("InstantiationException:{}" ,e);
                throw new RuntimeException(e);
            }
        }
    }

}
