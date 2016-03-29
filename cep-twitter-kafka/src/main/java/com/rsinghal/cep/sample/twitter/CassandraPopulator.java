package com.rsinghal.cep.sample.twitter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.rsinghal.cep.sample.twitter.avro.v11.TwitterStatusUpdate;

public class CassandraPopulator {

	public static void main(String[] args) {
		Schema schema11 = null;
		Schema.Parser parser = new Schema.Parser();
		try {
			schema11 = parser.parse(CassandraPopulator.class.getResourceAsStream("/TwitterSchema-v1.1.avsc"));
			FileInputStream inp = new FileInputStream("/Users/rsinghal/messages.txt");
			//FileReader fr = new FileReader(new File("/Users/rsinghal/messages.txt"));
			 Schema s  = Schema.createArray(TwitterStatusUpdate.getClassSchema());
	        DatumReader<List<TwitterStatusUpdate>> reader = new SpecificDatumReader<List<TwitterStatusUpdate>>(s);
	        List<TwitterStatusUpdate> tstList = new ArrayList<>();
	        //Encoder encoder = EncoderFactory.get().jsonEncoder(schema11, out, true);
	        Decoder dec = DecoderFactory.get().jsonDecoder(s, inp);
	        //TwitterStatusUpdate [] tt = new TwitterStatusUpdate[500];
	       reader.read(tstList,dec);
	       Set<Long> seenTweets = new HashSet<>();
	       AtomicInteger key = new AtomicInteger(0);
	       CodecRegistry cr = new CodecRegistry();
	      
	       Cluster cluster = Cluster.builder().addContactPoint("137.135.186.245").build();
	       Session session = cluster.connect("spark_streaming_twitter_1");
	       Mapper<Tweet> mapper = new MappingManager(session).mapper(Tweet.class);
	       AtomicInteger inserted = new AtomicInteger(0);
	       AtomicInteger discarded = new AtomicInteger(0);
	       for(TwitterStatusUpdate ts : tstList)
	       {
	    	  if(!seenTweets.contains(ts.getId()))
	    	  {
	    		  seenTweets.add(ts.getId());
	    		     System.out.println("started inserting tweet - "+ts.getId());
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
	    		  ByteArrayOutputStream out = new ByteArrayOutputStream();
	  	        DatumWriter<TwitterStatusUpdate> writer = new SpecificDatumWriter<TwitterStatusUpdate>(TwitterStatusUpdate.class);
	  	        Encoder encoder = EncoderFactory.get().jsonEncoder(schema11, out, true);
	  	        writer.write(ts, encoder);
	  	        encoder.flush();
	  	        t.setRaw(out.toString());
	  	        out.close();
	  	        mapper.save(t);
	  	        System.out.println("inserted tweet - "+ts.getId());
	  	      inserted.incrementAndGet();
	    	  
	    	  }
	    	  else
	    	  {
	    		  System.out.println("discarding duplicate - "+ts.getId());
	    		  discarded.getAndIncrement();
	    	  }
	    	   
	       }
	       
	       System.out.println("inserted - "+inserted.get());
	       System.out.println("discarded - "+discarded.get());
	        //dec.
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
       // writer.write(update, encoder);
        //encoder.flush();
        //out.close();
	}

}
