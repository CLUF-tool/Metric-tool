import java.io.*;
import java.util.*;

public class Arc_Base {
    public static void main(String[] args) {

        Map<String, String> map = new HashMap<String, String>(){{

           put("avro","avro-1.2.0");

        }};


        for (String key : map.keySet()) {
            long begin = System.currentTimeMillis();
            System.out.println("key = " + key);

            String project = key;
            String version = map.get(key);


            String RootSrcPath = "D:\\program\\SC_Related\\Metric-Tool\\example_project\\"+version;
            String RootOutPath = "D:\\program\\SC_Related\\Metric-Tool\\output\\ARC";
            String ArcadePath="D:\\program\\SC_Related\\Metric-Tool\\_2arcade";
            String outputDirName = RootOutPath + "\\" + version;

            File arc_proj = new File(outputDirName);
            if (!arc_proj.exists()) {
                arc_proj.mkdirs();
            }

            List<String> cmds = new ArrayList<>();

            String output_pipe = "java -jar .\\edu.usc.softarch.arcade.util.ldasupport.PipeExtractor.jar " + RootSrcPath + ' ' + outputDirName;
            String topic_model = "cmd /c "+ArcadePath+"\\ext-tools\\mallet-2.0.7\\bin\\mallet import-dir --input " + RootSrcPath + ' ' + " --remove-stopwords TRUE --keep-sequence TRUE --output " + outputDirName + "\\topicmodel.data";
            String infer_mallet = "cmd /c "+ArcadePath+"\\ext-tools\\mallet-2.0.7\\bin\\mallet train-topics --input " + outputDirName + "\\topicmodel.data" + " --inferencer-filename " + outputDirName + "\\infer.mallet" + " --num-top-words 50 --num-topics 100 --num-threads 3 --num-iterations 100 --doc-topics-threshold 0.1";

            cmds.add(output_pipe);
            cmds.add(topic_model);
            cmds.add(infer_mallet);

//            System.out.println(cmds.get(0));
//            System.out.println(cmds.get(1));
//            System.out.println(cmds.get(2)+'\n');

            try {
                for (int i = 0; i < cmds.size(); i++) {
                    System.out.println("cmds = " + cmds.get(i));
                    Runtime runtime = Runtime.getRuntime();
                    Process process = runtime.exec(cmds.get(i));
                    if (i != cmds.size() - 1) {
                        String outStr = getStreamStr(process.getInputStream());
                        // 错误结果，必须写在 waitFor 之前
                        String errStr = getStreamStr(process.getErrorStream());
                        int exitValue = process.waitFor(); // 退出值 0 为正常，其他为异常
//                        System.out.println("exitValue: " + exitValue);
//                        System.out.println("outStr: " + outStr);
//                        System.out.println("errStr: " + errStr);
                    }

                    process.destroy();
                }
            } catch (IOException | InterruptedException e1) { // 改自己的异常类
                e1.printStackTrace();
            }
        }

    }
    public static String getStreamStr(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        br.close();
        return sb.toString();
    }
}
