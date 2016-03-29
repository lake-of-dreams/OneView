package com.rsinghal.cep.sample.storm.bolt;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Computed;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.google.common.base.Objects;

	@Table(keyspace = "spark_streaming_twitter_1", name = "all_tweets")
	public class Tweet {
	  
	
		 public String contributors;
		   public String createdAt;
		   public Long createdAtAsLong;
		   public Long currentUserRetweetId;
		   public Long id;
		   public String inReplyToScreenName;
		   public Long inReplyToStatusId;
		   public Long inReplyToUserId;
		   public Long retweetCount;
		   public String source;
		   public String text;
		   public Boolean isFavorited;
		   public Boolean isPossiblySensitive;
		   public Boolean isRetweet;
		   public Boolean isRetweetedByMe;
		   public Boolean isTruncated;
		   public Double coordinatesLatitude;
		   public Double coordinatesLongitude;
		   public String location_URL;
		   public String location_streetAddress;
		   public String location_placeType;
		   public String location_name;
		   public String location_fullName;
		   public String location_id;
		   public String location_country;
		   public String location_countryCode;
		 public Long user_id;
		 public String user_screenName;
		 public String user_createdAt;
		 public String getContributors() {
			return contributors;
		}

		public void setContributors(String contributors) {
			this.contributors = contributors;
		}

		public String getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(String createdAt) {
			this.createdAt = createdAt;
		}

		public Long getCreatedAtAsLong() {
			return createdAtAsLong;
		}

		public void setCreatedAtAsLong(Long createdAtAsLong) {
			this.createdAtAsLong = createdAtAsLong;
		}

		public Long getCurrentUserRetweetId() {
			return currentUserRetweetId;
		}

		public void setCurrentUserRetweetId(Long currentUserRetweetId) {
			this.currentUserRetweetId = currentUserRetweetId;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getInReplyToScreenName() {
			return inReplyToScreenName;
		}

		public void setInReplyToScreenName(String inReplyToScreenName) {
			this.inReplyToScreenName = inReplyToScreenName;
		}

		public Long getInReplyToStatusId() {
			return inReplyToStatusId;
		}

		public void setInReplyToStatusId(Long inReplyToStatusId) {
			this.inReplyToStatusId = inReplyToStatusId;
		}

		public Long getInReplyToUserId() {
			return inReplyToUserId;
		}

		public void setInReplyToUserId(Long inReplyToUserId) {
			this.inReplyToUserId = inReplyToUserId;
		}

		public Long getRetweetCount() {
			return retweetCount;
		}

		public void setRetweetCount(Long retweetCount) {
			this.retweetCount = retweetCount;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public Boolean getIsFavorited() {
			return isFavorited;
		}

		public void setIsFavorited(Boolean isFavorited) {
			this.isFavorited = isFavorited;
		}

		public Boolean getIsPossiblySensitive() {
			return isPossiblySensitive;
		}

		public void setIsPossiblySensitive(Boolean isPossiblySensitive) {
			this.isPossiblySensitive = isPossiblySensitive;
		}

		public Boolean getIsRetweet() {
			return isRetweet;
		}

		public void setIsRetweet(Boolean isRetweet) {
			this.isRetweet = isRetweet;
		}

		public Boolean getIsRetweetedByMe() {
			return isRetweetedByMe;
		}

		public void setIsRetweetedByMe(Boolean isRetweetedByMe) {
			this.isRetweetedByMe = isRetweetedByMe;
		}

		public Boolean getIsTruncated() {
			return isTruncated;
		}

		public void setIsTruncated(Boolean isTruncated) {
			this.isTruncated = isTruncated;
		}

		public Double getCoordinatesLatitude() {
			return coordinatesLatitude;
		}

		public void setCoordinatesLatitude(Double coordinatesLatitude) {
			this.coordinatesLatitude = coordinatesLatitude;
		}

		public Double getCoordinatesLongitude() {
			return coordinatesLongitude;
		}

		public void setCoordinatesLongitude(Double coordinatesLongitude) {
			this.coordinatesLongitude = coordinatesLongitude;
		}

		public String getLocation_URL() {
			return location_URL;
		}

		public void setLocation_URL(String location_URL) {
			this.location_URL = location_URL;
		}

		public String getLocation_streetAddress() {
			return location_streetAddress;
		}

		public void setLocation_streetAddress(String location_streetAddress) {
			this.location_streetAddress = location_streetAddress;
		}

		public String getLocation_placeType() {
			return location_placeType;
		}

		public void setLocation_placeType(String location_placeType) {
			this.location_placeType = location_placeType;
		}

		public String getLocation_name() {
			return location_name;
		}

		public void setLocation_name(String location_name) {
			this.location_name = location_name;
		}

		public String getLocation_fullName() {
			return location_fullName;
		}

		public void setLocation_fullName(String location_fullName) {
			this.location_fullName = location_fullName;
		}

		public String getLocation_id() {
			return location_id;
		}

		public void setLocation_id(String location_id) {
			this.location_id = location_id;
		}

		public String getLocation_country() {
			return location_country;
		}

		public void setLocation_country(String location_country) {
			this.location_country = location_country;
		}

		public String getLocation_countryCode() {
			return location_countryCode;
		}

		public void setLocation_countryCode(String location_countryCode) {
			this.location_countryCode = location_countryCode;
		}

		public Long getUser_id() {
			return user_id;
		}

		public void setUser_id(Long user_id) {
			this.user_id = user_id;
		}

		public String getUser_screenName() {
			return user_screenName;
		}

		public void setUser_screenName(String user_screenName) {
			this.user_screenName = user_screenName;
		}

		public String getUser_createdAt() {
			return user_createdAt;
		}

		public void setUser_createdAt(String user_createdAt) {
			this.user_createdAt = user_createdAt;
		}

		public Integer getUser_followersCount() {
			return user_followersCount;
		}

		public void setUser_followersCount(Integer user_followersCount) {
			this.user_followersCount = user_followersCount;
		}

		public Integer getUser_friendsCount() {
			return user_friendsCount;
		}

		public void setUser_friendsCount(Integer user_friendsCount) {
			this.user_friendsCount = user_friendsCount;
		}

		public Integer getUser_listedCount() {
			return user_listedCount;
		}

		public void setUser_listedCount(Integer user_listedCount) {
			this.user_listedCount = user_listedCount;
		}

		public String getUser_profileImageURL() {
			return user_profileImageURL;
		}

		public void setUser_profileImageURL(String user_profileImageURL) {
			this.user_profileImageURL = user_profileImageURL;
		}

		public Integer getUser_statusesCount() {
			return user_statusesCount;
		}

		public void setUser_statusesCount(Integer user_statusesCount) {
			this.user_statusesCount = user_statusesCount;
		}

		public Boolean getUser_verified() {
			return user_verified;
		}

		public void setUser_verified(Boolean user_verified) {
			this.user_verified = user_verified;
		}

		public Integer user_followersCount;
		 public Integer user_friendsCount;
		 public Integer user_listedCount;
		 public String user_profileImageURL;
		 public Integer user_statusesCount;
		 public Boolean user_verified;
//		 public String raw;
//
//	    public String getRaw() {
//			return raw;
//		}
//
//		public void setRaw(String raw) {
//			this.raw = raw;
//		}

		public Tweet() {
	    }

	   
	}


