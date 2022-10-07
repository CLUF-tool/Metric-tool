import edu.usc.softarch.arcade.clustering.ConcernClusteringRunner;
import edu.usc.softarch.arcade.clustering.PreSelectedStoppingCriterion;
import edu.usc.softarch.arcade.clustering.util.ClusterUtil;
import edu.usc.softarch.arcade.config.Config;
import edu.usc.softarch.arcade.facts.driver.JavaSourceToDepsDIY;
import edu.usc.softarch.arcade.facts.driver.SourceToDepsBuilder;
import edu.usc.softarch.arcade.topics.TopicModelExtractionMethod;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ARC_Test {

    public static void main(String[] args) throws IOException {
        Map<String, String> map = new HashMap<String, String>(){{
            put("avro","avro-1.2.0");

        }};

        for (String key : map.keySet()) {

            String version = map.get(key);


            String RootSrcPath = "D:\\program\\SC_Related\\Metric-Tool\\example_project\\"+version;
            String RootOutPath = "D:\\program\\SC_Related\\Metric-Tool\\output";
//            String ArcadePath="D:\\program\\SC_Related\\Metric-Tool\\_2arcade";

//            String RootSrcPath="D:\\program\\code\\python\\CLUF\\input\\Github\\"+ key;
//        String RootOutPath="D:\\program\\java\\arcade\\subject_systems\\gobblin\\output";
//            String RootOutPath="D:\\program\\java\\arcade\\subject_systems\\arc";


//        String RootOutputPath=RootOutPath+"\\arc\\"+project;
//        String depsRsfFilename =  RootOutPath+"\\arc"+File.separatorChar+version+ "-deps.rsf";
            String depsRsfFilename =  RootOutPath+File.separatorChar+version+ "-deps.rsf";
//        String outputDirName=RootOutPath+"\\arc";
            String outputDirName=RootOutPath+"\\ARC"+"\\"+ version;



            File folder=new File(RootSrcPath);
//        System.out.println(folder);




            String[] builderArgs = { depsRsfFilename };
//            File depsRsfFile = new File(depsRsfFilename);
//            if (!depsRsfFile.getParentFile().exists())
//                depsRsfFile.getParentFile().mkdirs();


            SourceToDepsBuilder builder = new JavaSourceToDepsDIY();

            builder.build(builderArgs);
            if (builder.getEdges().size() == 0) {
                System.out.println("builder.getEdges().size() == 0 pause");
                return;
            }

            int numTopics = (int) ((double) builder.getNumSourceEntities() * 0.18);
        System.out.println("numTopics = " + numTopics);
            String fullSrcDir = folder.getAbsolutePath() + File.separatorChar;
            String topicModelFilename = outputDirName + "_" + numTopics + "_topics.mallet";
            String docTopicsFilename = outputDirName + "_" + numTopics + "_doc_topics.txt";
            String topWordsFilename = outputDirName + "_" + numTopics + "_top_words_per_topic.txt";
//        System.out.println("topicModelFilename = " +topicModelFilename);
            if (args.length == 4) {
                if (args[3].equals("c")) {
                    Config.selectedLanguage = Config.Language.c;
                }
            }

            ConcernClusteringRunner runner = new ConcernClusteringRunner(
                    builder.getFfVecs(),
                    TopicModelExtractionMethod.MALLET_API, fullSrcDir,
                    outputDirName+"/", numTopics, topicModelFilename, docTopicsFilename, topWordsFilename);

            // have to set some Config settings before executing the runner
            int numClusters = (int) ((double) runner.getFastClusters()
                    .size() * .20); // number of clusters to obtain is based
            // on the number of entities
//        System.out.println("runner.getFastClusters().size() = " + runner.getFastClusters().size());
            System.out.println("numClusters = " + numClusters);
            Config.setNumClusters(numClusters);
            Config.stoppingCriterion = Config.StoppingCriterionConfig.preselected;
            Config.setCurrSimMeasure(Config.SimMeasure.js);
            runner.computeClustersWithConcernsAndFastClusters(new PreSelectedStoppingCriterion());

            String arcClustersFilename = outputDirName + "_" + numTopics + "_topics_"
                    + runner.getFastClusters().size() + "_arc_clusters.rsf";
            // need to build the map before writing the file
            HashMap<String, Integer> clusterNameToNodeNumberMap = ClusterUtil
                    .createFastClusterNameToNodeNumberMap(runner
                            .getFastClusters());
            ClusterUtil.writeFastClustersRsfFile(
                    clusterNameToNodeNumberMap, runner.getFastClusters(),
                    arcClustersFilename);

        }
    }
}
