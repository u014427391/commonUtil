package com.example.common.util.webservice.axis;

import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.types.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *      WebService工具类
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/12/17 14:07  修改内容:
 * </pre>
 */
public class WebServiceClientUtils {

    private static final Logger log = LoggerFactory.getLogger(WebServiceClientUtils.class);
    private static final Integer CONNET_TIME_OUT = 8000;

    public static void call(String point, String nameSpace, String methodName, Map<String, String> paramMap)
            throws Exception {
        // 创建call实例
        log.info("wsdl链接：{}，命名空间：{}，方法名：{}" , point , nameSpace , methodName);
        Call call = null;
        try {
            call = (Call) new Service().createCall();
        } catch (Throwable e) {
            log.error("new call失败",e);
        }
        call.setTargetEndpointAddress(point);
        call.setOperationName(new QName(nameSpace, methodName));
        call.setUseSOAPAction(true);
        call.setSOAPActionURI(nameSpace + methodName);
        // 调用超时时间
        call.setTimeout(CONNET_TIME_OUT);

        // 存放入参数
        List<Object> paramValues = new ArrayList<Object>();
        if (paramMap != null && paramMap.size() > 0) {
            for (Map.Entry<String, String> param : paramMap.entrySet()) {
                call.addParameter(new QName(nameSpace, param.getKey()), Constants.XSD_STRING, ParameterMode.IN);
                paramValues.add(param.getValue());
                if (log.isInfoEnabled()) {
                    log.info("webService参数封装，参数:{}，值为{}" , param.getKey(), param.getValue());
                }
            }
        }

        // 设置返回参数类型
        //call.setReturnType(Constants.XSD_STRING);
        //call.setReturnType(XMLType.SOAP_DOCUMENT);
        //call.setReturnType(XMLType.XSD_SCHEMA);
        call.setReturnClass(java.lang.String[].class);

        // 调用WebService服务
        if (log.isInfoEnabled()) {
            log.info("开始调用webService");
        }
        long start = System.currentTimeMillis();
        String[] res = (String[]) call.invoke(paramValues.toArray());
        long end = System.currentTimeMillis();
        if (log.isInfoEnabled()) {
            log.info("调用webService ;耗时：{}", (end - start) + "ms");
        }

        // WebService参数返回
        //String result = object.toString();
        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
            if (log.isInfoEnabled()) {
                log.info("WebService参数返回：{}", res[i]);
            }
        }

    }

    public static void main(String[] args) throws Exception {
        String point = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl";
        String nameSpace = "http://WebXml.com.cn/";
        String methodName = "getWeather";
        String theCityCode = "北京";
        String  theUserID = "";
        Map<String, String> paramMap = new HashMap<String, String>(2);
        paramMap.put("theCityCode", theCityCode);
        paramMap.put("theUserID", theUserID);
       call(point, nameSpace, methodName, paramMap);

    }
}