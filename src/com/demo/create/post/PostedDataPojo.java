package com.demo.create.post;

public class PostedDataPojo {

	public String username;
	public String postDetail;
	public String dateTime;
	public String imageUrl;
	public String videoUrl;
	public boolean myPost;
	public String privacy;
	
	
	

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public boolean isMyPost() {
		return myPost;
	}

	public void setMyPost(boolean myPost) {
		this.myPost = myPost;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPostDetail() {
		return postDetail;
	}

	public void setPostDetail(String postDetail) {
		this.postDetail = postDetail;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
