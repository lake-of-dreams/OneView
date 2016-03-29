package com.rsinghal.cep.sample.storm.bolt;

import java.io.ByteArrayOutputStream;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.commons.io.IOUtils;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.rsinghal.cep.sample.twitter.avro.v11.TwitterStatusUpdate;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;
public class TweetClassificationStorageCassandra extends CassandraBaseBoltNew {

	 protected	Mapper<TweetClassification> mapper ;
	 private Classify classification;
	 public static int updated = 0;

	

	public TweetClassificationStorageCassandra(String cassandraHost, String keySpace) {
		this.cassandraHost = cassandraHost;
		this.keySpace = keySpace;
		
		
	}

	/* (non-Javadoc)
	 * @see backtype.storm.topology.IBasicBolt#execute(backtype.storm.tuple.Tuple, backtype.storm.topology.BasicOutputCollector)
	 */
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		try
		{
			
			 Long id = input.getLong(input.fieldIndex("tweet_id"));
		        String text = input.getString(input.fieldIndex("tweet_text"));
		        TweetClassification tc = classification.classify(id, text);
Statement statement = QueryBuilder.update("spark_streaming_twitter_1", "all_tweets")
						
				        .with(set("sentiment", tc.getSentiment()))
				        .and(set("classification", tc.getClassification()))
				        .and(set("sentiment_score", tc.getSentiment_score()))
				        .and(set("classification_score", tc.getClassification_score()))
				        .where(eq("id", tc.getId()));
				mapper.getManager().getSession().execute(statement);
	        collector.emit(new Values(id,text,
	        		input.getValueByField("tweet_userid"),
	        		input.getValueByField("tweet_screenname"),
	        		input.getValueByField("tweet_replytouserid"),
	        		input.getValueByField("tweet_replytotweet"),
	        		input.getValueByField("tweet_replytoscreen"),
	        		input.getValueByField("tweet_time"),
	        		
	        		tc.getSentiment(),tc.getClassification(),tc.getSentiment_score(),tc.getClassification_score()));
		}catch(Exception e)
		 {
			 throw new RuntimeException(e);
		 }
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("tweet_id", "tweet_text","tweet_userid","tweet_screenname","tweet_replytouserid","tweet_replytotweet","tweet_replytoscreen","tweet_time","tweet_sentiment","tweet_classification","tweet_sentiment_score","tweet_classify_score"));
	}
	
	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		
		super.prepare(stormConf, context);
		schema = TwitterStatusUpdate.getClassSchema();
		mapper = new MappingManager(session).mapper(TweetClassification.class);
		classification = new Classify();
		
		
		
	}
	
	public static void main(String[] args) {
		Cluster cluster1 = Cluster.builder().addContactPoint("137.135.186.245").build();
		Session session1 = cluster1.connect("spark_streaming_twitter_1");
		Mapper<TweetClassification> mapper1 = new MappingManager(session1).mapper(TweetClassification.class);
		Mapper<Tweet> mapper2 = new MappingManager(session1).mapper(Tweet.class);
		ResultSet rs = mapper2.getManager().getSession().execute("select * from spark_streaming_twitter_1.tweets_classification");
		Classify classification1 = new Classify();
		
		
		rs.forEach(new Consumer<Row>() {

			@Override
			public void accept(Row t) {
				Statement statement = QueryBuilder.update("spark_streaming_twitter_1", "all_tweets")
						
				        .with(set("sentiment", t.getString("sentiment")))
				        .and(set("classification", t.getString("classification")))
				        .and(set("sentiment_score", t.getDouble("sentiment_score")))
				        .and(set("classification_score", t.getDouble("classification_score")))
				        .where(eq("id", t.getLong("id")));
				mapper1.getManager().getSession().execute(statement);
				System.out.println(++TweetClassificationStorageCassandra.updated+"...."+t.getLong("id"));
			}
		});
		
	}
	

}
