import acdc.ACDC;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.Date;

public class ACDC_Test {
    public static void main(String[] args) {
//        String project="activemq";
//        String version="activemq-5.3.0";

        Map<String, String> map = new HashMap<String, String>(){{
            put("avro","avro-1.2.0");
        }};


        for (String key : map.keySet()) {
            long begin=System.currentTimeMillis();
            System.out.println("key = " + key);

            String project=key;
            String version = map.get(key);

//            String RootSrcPath="D:\\program\\code\\python\\CSEC\\input\\Github";
//            String SubjectPath="D:\\program\\java\\arcade\\subject_systems";
//
//            String RootOutputPath=SubjectPath+"\\output";
//            String depsRsfFilename =  RootSrcPath+"\\"+project+"\\"+version+ "_deps.rsf";
//            String acdcClusteredFile=RootOutputPath+"\\acdc\\"+project+"_acdc_clustered.rsf";

            String RootSrcPath="D:\\program\\SC_Related\\Metric-Tool\\example_project";
            String SubjectPath="D:\\program\\SC_Related\\Metric-Tool";

            String RootOutputPath=SubjectPath+"\\output";
            String depsRsfFilename =  RootOutputPath+"\\"+version+ "-deps.rsf";
//            路径需要存在
            String acdcClusteredFile=RootOutputPath+"\\acdc\\"+version+"_acdc_clustered.rsf";


            File depsRsfFile = new File(depsRsfFilename);
            String[] acdcArgs = {depsRsfFile.getAbsolutePath(),acdcClusteredFile};
            ACDC.main(acdcArgs);
            long end=System.currentTimeMillis();
//            System.out.println("程序运行的时间为："+(end-begin)/1000.0+"s");

//            try { Thread.sleep ( 20*3000 ) ;
//            } catch (InterruptedException ie){}

            Date d = new Date();
            System.out.println(d);

        }


    }
}