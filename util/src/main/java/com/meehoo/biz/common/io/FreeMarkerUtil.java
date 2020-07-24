package com.meehoo.biz.common.io;

//import freemarker.template.Configuration;
//import freemarker.template.Template;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * lib jar引用
 * Created by zc on 2017/12/21 0021.
 */
public class FreeMarkerUtil {
//    // 模板相对路径
//    //private static String TEMPLATE_PATH = "/src/main/java/com/meehoo/bos/util/applyTable.ftl";
//    private static String TEMPLATE_PATH = "applyTable.ftl";
//    private static String TEMPLATE_PATH1 = "applyTable1.ftl";
//    private static String TEMPLATE_PATH_SIMPLE_FIRE_REPORT = "simpleFireReport.ftl";
//    private static String TEMPLATE_PATH_INQUEST_RECORD = "InquestRecord.ftl";
//    private static String TEMPLATE_PATH_INQUIRE_RECORD = "InquireRecord.ftl";
//    // 项目固定路径
////    private static String APPLICATION_PATH = "D:/project/purchase-re-server/purchase-platform/src/main/webapp/upload";
////    private static String APPLICATION_PATH = "/opt/apache-tomcat-8.5.5/webapps/admin/upload";
//    // 若干模板名字
//    public static String TEMPLATE_APPLY_TABLE = "applyTable";
//    public static String TEMPLATE_APPLY_TABLE1 = "applyTable1";
//    //火灾事故简易调查卷
//    public static String TEMPLATE_SIMPLE_FIRE_REPORT = "simpleFireReport";
//    //勘验笔录
//    public static String TEMPLATE_INQUEST_RECORD = "inquestRecord";
//    //询问笔录
//    public static String TEMPLATE_INQUIRE_RECORD = "inquireRecord";
//
//    //freemarker模板存放路径
//    public final static String  TEMPLATE_PATH_FREEMAKER ="C:\\doctemplate";
//
//    private static Configuration configuration = null;
//    private static Map<String, Template> allTemplates = new HashMap<>();
////
//    static{
//        configuration = new Configuration(Configuration.VERSION_2_3_26);
//        configuration.setDefaultEncoding("utf-8");
//        //后面这个地址保留疑问
////        configuration.setClassForTemplateLoading(FreeMarkerUtil.class, "/com/meehoo/bos/util/../template/");
////        allTemplates = new HashMap<>();
//        try{
//            //-------
////            ResourceBundle bundle = ResourceBundle.getBundle("freemarker");
////            String clazzPath = new File("").getCanonicalPath();
////            String path = clazzPath.substring(0, clazzPath.indexOf("fireInvestigation-server-refactor"));
////            String directoryPath = path+bundle.getString("directoryForTemplateLoading");
//            //--------
////            Template template = configuration.getTemplate("applyTable.ftl");
////            TemplateLoader templateLoader = new FileTemplateLoader(new File(APPLICATION_PATH));
////            TemplateLoader templateLoader = new FileTemplateLoader(new File("D:/Code/fireInvestigation-server/util/src/main/java/com/meehoo/biz/bos/util/template"));
////            configuration.setTemplateLoader(templateLoader);
//
////            Template template = configuration.getTemplate(TEMPLATE_PATH);
////            Template template = configuration.getTemplate("/com/meehoo/bos/template/applyTable.ftl");
////            allTemplates.put(TEMPLATE_APPLY_TABLE, template);
////
////            Template template1 = configuration.getTemplate(TEMPLATE_PATH1);
////            allTemplates.put(TEMPLATE_APPLY_TABLE1, template1);
//            configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH_FREEMAKER));
////            configuration.setDirectoryForTemplateLoading(new File("D:\\docFtl"));
//            allTemplates.put(TEMPLATE_SIMPLE_FIRE_REPORT, configuration.getTemplate(TEMPLATE_PATH_SIMPLE_FIRE_REPORT));
//            allTemplates.put(TEMPLATE_INQUEST_RECORD,configuration.getTemplate(TEMPLATE_PATH_INQUEST_RECORD));
//            allTemplates.put(TEMPLATE_INQUIRE_RECORD,configuration.getTemplate(TEMPLATE_PATH_INQUIRE_RECORD));
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new RuntimeException();
//        }
//    }
//
//    public FreeMarkerUtil() {
//        //断言错。用来指示一个断言失败的情况。
////        throw new AssertionError();
//    }
//
//    /**
//     * 添加年月日到dataMap
//     * @param date
//     * @param dataMap
//     * @return
//     */
//    public static Map<String,Object> addYearAndMonthAndDay(Date date, Map<String,Object> dataMap){
//        if(!BaseUtil.objectNotNull(dataMap)){
//            return null;
//        }
//        if(BaseUtil.objectNotNull(date)){
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            dataMap.put("year",calendar.get(Calendar.YEAR));
//            dataMap.put("month",calendar.get(Calendar.MONTH)+1);
//            dataMap.put("day",calendar.get(Calendar.DATE));
//        }
//        return dataMap;
//    }
//
//    /**
//     *
//     * @param dataMap 替换数据
//     * @param name 模板名字
//     * @return
//     * @throws Exception
//     */
//    public static File createDoc(Map<?, ?> dataMap, String name, HttpServletRequest request,
//                                 HttpServletResponse response, String contractName) throws Exception{
//        //稍后把名字改了
//        //File f = new File(path);
////        Template t =  allTemplates.get(type);
////        Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
////        configuration.setDefaultEncoding("utf-8");
////        TemplateLoader templateLoader = new FileTemplateLoader(new File(APPLICATION_PATH));
////        configuration.setTemplateLoader(templateLoader);
////        Template template = configuration.getTemplate("/com/meehoo/bos/template/applyTable.ftl");
//        response.setContentType("application/x-msdownload");
//        try {
//            response.addHeader("Content-Disposition",
//                    "attachment; filename="+ (new String(contractName.getBytes("gb2312"), "ISO8859-1")));
//        } catch (UnsupportedEncodingException e1) {
//            e1.printStackTrace();
//        }
//
//        Template template = allTemplates.get(name);
//        if (template==null)
//            return null;
//        OutputStream os;
//        try{
//            //这个地方不能使用FileWrite 因为需要指明编码类型，否则生成的word文档会因为有无法识别的编码而无法打开。
//            os = response.getOutputStream();
//            Writer writer = new OutputStreamWriter(os, "utf-8");
//            template.process(dataMap, writer);
//            if(writer != null){
//                writer.close();
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//            throw new RuntimeException();
//        }
//        return null;
//    }
//
//    /**
//     * 生成.doc到临时文件中
//     * @param dataMap
//     * @param name  模板名
//     * @param contractName
//     * @return
//     * @throws Exception
//     */
//    public static File createDoc(Map<?, ?> dataMap, String name,String investorId,String contractName) throws Exception{
//        Template template = allTemplates.get(name);
//        if (template==null)
//            return null;
//        OutputStream os;
//        try{
//            ZipUtil.buildPath(investorId);
//            Writer writer = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream(ZipUtil.getTempPath(investorId)+"\\"+contractName),"utf-8"));
//            template.process(dataMap, writer);
//            if(writer != null){
//                writer.close();
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//            throw new RuntimeException();
//        }
//        return null;
//    }
//
//    public static String getImageString(byte[] data) throws IOException {
//        return data != null ? new BASE64Encoder().encode(data) : "";
//    }
//
//    public static boolean generateImage(String imgStr, String imgFilePath){
//        if(imgStr == null)
//            return false;
//        BASE64Decoder decoder = new BASE64Decoder();
//        try{
//            byte[] bytes = decoder.decodeBuffer(imgStr);
//            for(int i = 0; i < bytes.length; i++){
//                if(bytes[i] < 0){
//                    bytes[i] += 256;
//                }
//            }
//            OutputStream out = new FileOutputStream(imgFilePath);
//            out.write(bytes);
//            out.flush();
//            out.close();
//            return true;
//        }catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//
//    }
}
