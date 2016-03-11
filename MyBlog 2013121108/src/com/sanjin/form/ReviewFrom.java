package com.sanjin.form;

public class ReviewFrom {
	public int reviewID;
	public String review_author;
	public String review_content;
	public String review_sdTime;
	public String  article_typeID;
	public String getReview_author() {
		return review_author;
	}
	public void setReview_author(String review_author) {
		this.review_author = review_author;
	}
	public String getReview_content() {
		return review_content;
	}
	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	public String getReview_sdTime() {
		return review_sdTime;
	}
	public void setReview_sdTime(String review_sdTime) {
		this.review_sdTime = review_sdTime;
	}
	public String getArticle_typeID() {
		return article_typeID;
	}
	public void setArticle_typeID(String article_typeID) {
		this.article_typeID = article_typeID;
	}
	public int getReviewID() {
		return reviewID;
	}
	public void setReviewID(int reviewID) {
		this.reviewID = reviewID;
	}
	
}
