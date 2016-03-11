package com.sanjin.form;

public class ReplyForm {

	public int Reply_id;
	public String reply_sdTime;
	public String Reply_Content;
	public int reviewID;
	
	
	public int getReply_id() {
		return Reply_id;
	}
	public void setReply_id(int reply_id) {
		Reply_id = reply_id;
	}
	public String getReply_sdTime() {
		return reply_sdTime;
	}
	public void setReply_sdTime(String reply_sdTime) {
		this.reply_sdTime = reply_sdTime;
	}
	public String getReply_Content() {
		return Reply_Content;
	}
	public void setReply_Content(String reply_Content) {
		Reply_Content = reply_Content;
	}
	public int getReviewID() {
		return reviewID;
	}
	public void setReviewID(int reviewID) {
		this.reviewID = reviewID;
	}
	
}
