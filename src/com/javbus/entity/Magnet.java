package com.javbus.entity;

public class Magnet {
	//��������
	String MagnetUrl;
	//������С
	String MagnetSize;
	//��������
	String MagnetData;
	//��������
	String MagnetTitle;
	//��������
	String MagnetNum;
	public String getMagnetUrl() {
		return MagnetUrl;
	}
	public void setMagnetUrl(String magnetUrl) {
		MagnetUrl = magnetUrl;
	}
	public String getMagnetSize() {
		return MagnetSize;
	}
	public void setMagnetSize(String magnetSize) {
		MagnetSize = magnetSize;
	}
	public String getMagnetData() {
		return MagnetData;
	}
	public void setMagnetData(String magnetData) {
		MagnetData = magnetData;
	}
	public String getMagnetTitle() {
		return MagnetTitle;
	}
	public void setMagnetTitle(String magnetTitle) {
		MagnetTitle = magnetTitle;
	}
	public String getMagnetNum() {
		return MagnetNum;
	}
	public void setMagnetNum(String magnetNum) {
		MagnetNum = magnetNum;
	}
	@Override
	public String toString() {
		return "Magnet [MagnetUrl=" + MagnetUrl + ", MagnetSize=" + MagnetSize + ", MagnetData=" + MagnetData
				+ ", MagnetTitle=" + MagnetTitle + ", MagnetNum=" + MagnetNum + "]";
	}
	
}
