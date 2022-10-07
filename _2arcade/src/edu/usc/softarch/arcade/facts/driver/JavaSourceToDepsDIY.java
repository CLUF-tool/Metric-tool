package edu.usc.softarch.arcade.facts.driver;

import edu.usc.softarch.arcade.clustering.FastFeatureVectors;
import edu.usc.softarch.arcade.clustering.FeatureVectorMap;
import edu.usc.softarch.arcade.functiongraph.TypedEdgeGraph;
import edu.usc.softarch.arcade.util.FileUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class JavaSourceToDepsDIY implements SourceToDepsBuilder{
    public Set<Pair<String,String>> edges;
    public static FastFeatureVectors ffVecs = null;
    public int numSourceEntities = 0;

    @Override
    public void build(String[] args) throws IOException, FileNotFoundException {
        String depsRsfFilename = FileUtil.tildeExpandPath(args[0]);

        edges = new LinkedHashSet<Pair<String,String>>();
        Set<String> sources = new HashSet<String>();
        TypedEdgeGraph typedEdgeGraph = new TypedEdgeGraph();

        try {
            File file = new File(depsRsfFilename);
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
            BufferedReader bf = new BufferedReader(inputReader);
            String str;

            while ((str = bf.readLine()) != null) {

                String[] numbersArray=str.split(" ");
                Pair<String,String> edge= new ImmutablePair<String,String>(numbersArray[1],numbersArray[2]);
                typedEdgeGraph.addEdge(numbersArray[0],numbersArray[1],numbersArray[2]);
                sources.add(numbersArray[1]);
                edges.add(edge);
            }

            bf.close();
            inputReader.close();

            numSourceEntities = sources.size();

            FeatureVectorMap fvMap = new FeatureVectorMap(typedEdgeGraph);
            ffVecs = fvMap.convertToFastFeatureVectors();
            System.out.println("ffVecs = " + ffVecs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Pair<String, String>> getEdges() {
        return this.edges;
    }

    @Override
    public int getNumSourceEntities() {
        return this.numSourceEntities;
    }

    @Override
    public FastFeatureVectors getFfVecs() {
        return this.ffVecs;
    }
}
