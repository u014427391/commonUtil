package com.example.common.util.office.word;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFComment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * <pre>
 *      word工具类
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/11/02 16:40  修改内容:
 * </pre>
 */
public class WordUtils {


    public static void createDocument() throws IOException {
        //Blank Document
        XWPFDocument document= new XWPFDocument();
        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(
                new File("D://createdocument.docx"));
        document.write(out);
        out.close();
        System.out.println(
                "createdocument.docx written successully");
    }


//    public static void printWordComment() throws IOException {
//        StringBuffer value = new StringBuffer();
//        File file = new File("D://test.docx");
//        XWPFDocument docx = new XWPFDocument(POIXMLDocument.openPackage(file
//                .getCanonicalPath()));
//        XWPFComment[] comments = docx.getComments();
//        String maxCommentIndex = String.valueOf(comments.length - 1);
//        Map<String,String> commentRefs = new HashMap<String, String>();
//        fillCommentRef(docx.getDocument().getDomNode(),
//                new StringBuilder(), new StringBuilder(),
//                new StringBuilder(), commentRefs);
//        initAttributes();
//        XWPFComment[] comments = wh.getComments();
//        Map<String, String> commenRefMap = wh.getCommentRefs();
//        List<Date> l = wh.getSubmitDateList();
//        SimpleDateFormat sdf = wh.getSdf();
//        XWPFComment comment;
//        for (int i = 0; i < comments.length; i++) {
//            comment = comments[i];
//            value.append("批注Id:").append(comment.getId()).append(", ")
//                    .append("批注作者:").append(comment.getAuthor()).append(", ")
//                    .append("批注日期:").append(sdf.format(l.get(i))).append(", ")
//                    .append("批注内容:").append(comment.getText()).append(", ")
//                    .append("批注引用正文:")
//                    .append(commenRefMap.get(comment.getId()));
//            value.append("\n");
//        }
//        System.out.println(value);
//    }

}
