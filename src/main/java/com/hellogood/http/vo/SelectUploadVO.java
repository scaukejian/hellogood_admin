package com.hellogood.http.vo;

public class SelectUploadVO {

	private Integer startX;
	private Integer startY;
	private Integer endX;
	private Integer endY;
	private Integer userId;
	private String fileName;
	private String originalImgName;
	private String certificationType; //认证类型：身份证或者工作证
	private Integer wonderfulReviewId;
	private Integer speciallyVisitId;

	public String getOriginalImgName() {
		return originalImgName;
	}

	public void setOriginalImgName(String originalImgName) {
		this.originalImgName = originalImgName;
	}

	public String getCertificationType() {
		return certificationType;
	}

	public void setCertificationType(String certificationType) {
		this.certificationType = certificationType;
	}

	public Integer getSpeciallyVisitId() {
		return speciallyVisitId;
	}

	public void setSpeciallyVisitId(Integer speciallyVisitId) {
		this.speciallyVisitId = speciallyVisitId;
	}

	public Integer getWonderfulReviewId() {
		return wonderfulReviewId;
	}

	public void setWonderfulReviewId(Integer wonderfulReviewId) {
		this.wonderfulReviewId = wonderfulReviewId;
	}

	public Integer getStartX() {
		return startX;
	}

	public void setStartX(Integer startX) {
		this.startX = startX;
	}

	public Integer getStartY() {
		return startY;
	}

	public void setStartY(Integer startY) {
		this.startY = startY;
	}

	public Integer getEndX() {
		return endX;
	}

	public void setEndX(Integer endX) {
		this.endX = endX;
	}

	public Integer getEndY() {
		return endY;
	}

	public void setEndY(Integer endY) {
		this.endY = endY;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
