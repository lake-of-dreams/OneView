package com.rsinghal.cep.sample.storm.bolt;

import org.apache.log4j.Logger;
import org.elasticsearch.storm.EsBolt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StaticHosts;
import storm.kafka.ZkHosts;
import storm.kafka.trident.GlobalPartitionInformation;
import storm.kafka.StringScheme;
import static org.elasticsearch.hadoop.cfg.ConfigurationOptions.*;
import static org.elasticsearch.storm.cfg.StormConfigurationOptions.*;
public class SentimentAnalysisTopology
{
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private static final String KAFKA_TOPIC =
        Properties.getString("rts.storm.kafka_topic");

    public static void main(String[] args) throws Exception
    {
        BasicConfigurator.configure();

      //  if (args != null && args.length > 0)
        //{
       
            StormSubmitter.submitTopology(
            		"sentiment-analysis",
                createConfig(false),
                createTopology());
      //  }
//        else
//        {
//            LocalCluster cluster = new LocalCluster();
//            cluster.submitTopology(
//                "sentiment-analysis",
//                createConfig(true),
//                createTopology());
//            Thread.sleep(60000);
//            cluster.shutdown();
//        }
    }

    private static StormTopology createTopology()
    {
        SpoutConfig kafkaConf = new SpoutConfig(
            new ZkHosts(Properties.getString("rts.storm.zkhosts")),
            KAFKA_TOPIC,
            "/kafka",
            "KafkaSpout");
        kafkaConf.scheme = new SchemeAsMultiScheme(new StringScheme());
    /*    SpoutConfig kafkaConfig = new SpoutConfig(new StaticHosts(new GlobalPartitionInformation()), topic, offsetPath, consumerId);
        kafkaConfig.bufferSizeBytes = 1024*1024*4;
        kafkaConfig.fetchSizeBytes = 1024*1024*4;
        kafkaConfig.forceFromStart = true;*/
        TopologyBuilder topology = new TopologyBuilder();

        topology.setSpout("kafka_spout", new KafkaSpout(kafkaConf), 4);

        topology.setBolt("tweet_storage", new TweetStorageCassandraNew("137.135.186.245", "spark_streaming_twitter_1"), 4)
        .shuffleGrouping("kafka_spout");
        topology.setBolt("tweet_classify", new TweetClassificationStorageCassandra("137.135.186.245", "spark_streaming_twitter_1"), 4)
        .shuffleGrouping("tweet_storage");
        Map config = new HashMap();
        config.put(ES_NODES, "azcep05");
        config.put(ES_INDEX_AUTO_CREATE, "true");
       
      
        topology.setBolt("es_bolt", new EsBolt("storm/cep", config)).shuffleGrouping("tweet_classify");
        
        
      /*  topology.setBolt("twitter_filter", new TwitterFilterBolt(), 4)
                .shuffleGrouping("tweet_storage");*/

       /* topology.setBolt("text_filter", new TextFilterBolt(), 4)
                .shuffleGrouping("tweet_storage");

        topology.setBolt("stemming", new StemmingBolt(), 4)
                .shuffleGrouping("text_filter");

        topology.setBolt("positive", new PositiveSentimentBolt(), 4)
                .shuffleGrouping("stemming");
        topology.setBolt("negative", new NegativeSentimentBolt(), 4)
                .shuffleGrouping("stemming");

        topology.setBolt("join", new JoinSentimentsBolt(), 4)
                .fieldsGrouping("positive", new Fields("tweet_id"))
                .fieldsGrouping("negative", new Fields("tweet_id"));

        topology.setBolt("score", new SentimentScoringBolt(), 4)
                .shuffleGrouping("join");*/

       // topology.setBolt("hdfs", new HDFSBolt(), 4)
         //       .shuffleGrouping("score");
      //  topology.setBolt("nodejs", new NodeNotifierBolt(), 4)
          //      .shuffleGrouping("score");
      /* topology.setBolt("console", new ConsoleBolt(), 4)
        .shuffleGrouping("score");*/

        return topology.createTopology();
    }

    private static Config createConfig(boolean local)
    {
        int workers = Properties.getInt("rts.storm.workers");
        Config conf = new Config();
        conf.setDebug(true);
        List<String> zk = new ArrayList<String>();
        zk.add("13.69.153.214");
        conf.put(Config.STORM_ZOOKEEPER_SERVERS,zk);
        conf.put(Config.STORM_ZOOKEEPER_PORT, 2181);
        conf.put(Config.STORM_ZOOKEEPER_ROOT, "/storm");
        conf.put(Config.NIMBUS_HOST, "13.69.148.82");
        System.setProperty("storm.jar", "/Users/rsinghal/git/cep-twitter-kafka-storm/cep-storm/target/cep-storm-1.0-SNAPSHOT-jar-with-dependencies.jar");
     
        if (local)
            conf.setMaxTaskParallelism(workers);
        else
            conf.setNumWorkers(workers);
        return conf;
    }
}
