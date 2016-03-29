package com.rsinghal.cep.sample.storm.bolt;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

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

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.rsinghal.cep.sample.twitter.avro.v11.TwitterStatusUpdate;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class TweetStorageCassandraNew extends CassandraBaseBoltNew {

	 protected TwitterStatusUpdate ts ;
	 protected DatumReader<TwitterStatusUpdate> reader ;
	 protected Schema schema;
	 protected	Mapper<Tweet> mapper ;


	

	public TweetStorageCassandraNew(String cassandraHost, String keySpace) {
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
		 String json = input.getString(0);
	InputStream inp  = 	IOUtils.toInputStream(json);
		 Decoder dec = DecoderFactory.get().jsonDecoder(schema, inp);
		 ts = new TwitterStatusUpdate();
		 reader.read(ts,dec);
		 Tweet t = new Tweet();
		  t.setContributors(ts.getContributors()==null || ts.getContributors().isEmpty()?null:Arrays.toString(ts.getContributors().toArray(new Long[ts.getContributors().size()])));
		  t.setCoordinatesLatitude(ts.getCoordinatesLatitude());
		  t.setCoordinatesLongitude(ts.getCoordinatesLongitude());
		  t.setCreatedAt(ts.getCreatedAt()==null?null:ts.getCreatedAt().toString());
		  t.setCreatedAtAsLong(ts.getCreatedAtAsLong());
		  t.setCurrentUserRetweetId(ts.getCurrentUserRetweetId());
		  t.setIsFavorited(ts.getIsFavorited());
		  t.setId(ts.getId());
		  t.setInReplyToScreenName(ts.getInReplyToScreenName()==null?null:ts.getInReplyToScreenName().toString());
		  t.setInReplyToStatusId(ts.getInReplyToStatusId());
		  t.setInReplyToUserId(ts.getInReplyToUserId());
		  t.setLocation_country(ts.getPlace()==null?null:(ts.getPlace().getCountry()==null?null:ts.getPlace().getCountry().toString()));
		  t.setLocation_countryCode(ts.getPlace()==null?null:(ts.getPlace().getCountryCode()==null?null:ts.getPlace().getCountryCode().toString()));
		  t.setLocation_fullName(ts.getPlace()==null?null:(ts.getPlace().getFullName()==null?null:ts.getPlace().getFullName().toString()));
		  t.setLocation_id(ts.getPlace()==null?null:(ts.getPlace().getId()==null?null:ts.getPlace().getId().toString()));
		  t.setLocation_name(ts.getPlace()==null?null:(ts.getPlace().getName()==null?null:ts.getPlace().getName().toString()));
		  t.setLocation_placeType(ts.getPlace()==null?null:(ts.getPlace().getPlaceType()==null?null:ts.getPlace().getPlaceType().toString()));
		  t.setLocation_streetAddress(ts.getPlace()==null?null:(ts.getPlace().getStreetAddress()==null?null:ts.getPlace().getStreetAddress().toString()));
		  t.setLocation_URL(ts.getPlace()==null?null:(ts.getPlace().getURL()==null?null:ts.getPlace().getURL().toString()));
		  t.setIsPossiblySensitive(ts.getIsPossiblySensitive());
		  t.setIsRetweet(ts.getIsRetweet());
		  t.setRetweetCount(ts.getRetweetCount());
		  t.setIsRetweetedByMe(ts.getIsRetweetedByMe());
		  t.setSource(ts.getSource()==null?null:ts.getSource().toString());
		  t.setText(ts.getText()==null?null:ts.getText().toString());
		  t.setIsTruncated(ts.getIsTruncated());
		  t.setUser_createdAt(ts.getUser()==null?null:(ts.getUser().getCreatedAt()==null?null:ts.getUser().getCreatedAt().toString()));
		  t.setUser_followersCount(ts.getUser()==null?null:ts.getUser().getFollowersCount());
		  t.setUser_friendsCount(ts.getUser()==null?null:ts.getUser().getFriendsCount());
		  t.setUser_id(ts.getUser()==null?null:ts.getUser().getId());
		  t.setUser_listedCount(ts.getUser()==null?null:ts.getUser().getListedCount());
		  t.setUser_profileImageURL(ts.getUser()==null?null:(ts.getUser().getProfileImageURL()==null?null:ts.getUser().getProfileImageURL().toString()));
		  t.setUser_screenName(ts.getUser()==null?null:(ts.getUser().getScreenName()==null?null:ts.getUser().getScreenName().toString()));
		  t.setUser_statusesCount(ts.getUser()==null?null:ts.getUser().getStatusesCount());
		  t.setUser_verified(ts.getUser()==null?null:ts.getUser().getVerified());
		 /* ByteArrayOutputStream out = new ByteArrayOutputStream();
	        DatumWriter<TwitterStatusUpdate> writer = new SpecificDatumWriter<TwitterStatusUpdate>(TwitterStatusUpdate.class);
	        Encoder encoder = EncoderFactory.get().jsonEncoder(schema, out, true);
	        writer.write(ts, encoder);
	        encoder.flush();
	        t.setRaw(out.toString());
	        out.close();*/
	        mapper.save(t);
	        collector.emit(new Values(t.getId(),t.getText(),t.getUser_id(),t.getUser_screenName(),t.getInReplyToUserId(),t.getInReplyToStatusId(),t.getInReplyToScreenName(),t.getCreatedAt()));
		}catch(Exception e)
		 {
			 throw new RuntimeException(e);
		 }
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("tweet_id", "tweet_text","tweet_userid","tweet_screenname","tweet_replytouserid","tweet_replytotweet","tweet_replytoscreen","tweet_time"));
	}
	
	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		
		super.prepare(stormConf, context);
		schema = TwitterStatusUpdate.getClassSchema();
		mapper = new MappingManager(session).mapper(Tweet.class);
		
		reader = new SpecificDatumReader<TwitterStatusUpdate>(schema);
		
	}
	

}
