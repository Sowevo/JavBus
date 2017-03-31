package com.javbus.entity;

import java.util.List;

public class MovieInfo {
	/**
	 * ��û����
	 */
	String censored;
	/**
	 * ����
	 */
	String num;
	/**
	 * ����
	 */
	String title;
	/**
	 * ��������
	 */
	String release;
	/**
	 * ӰƬ����
	 */
	String runTime;
	/**
	 * ��Ա
	 */
	List<String> stars;
	/**
	 * ����
	 */
	String director;
	/**
	 * ������
	 */
	String studio;
	/**
	 * ������
	 */
	String label;
	/**
	 * ���
	 */
	List<String> genres;
	/**
	 * ����
	 */
	String cover;
	/**
	 * Ԥ��ͼ
	 */
	List<String> Previews;
	/**
	 * ϵ��
	 */
	String series;
	

	/**
	 * ��������
	 */
	List<Magnet> magnet;
	/**
	 * ��ȡ����
	 * @return
	 */
	public String getNum() {
		return num;
	}
	/**
	 * ���÷���
	 * @param num
	 */
	public void setNum(String num) {
		this.num = num;
	}
	/**
	 * ��ȡ����
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * ���ñ���
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * ��ȡ��������
	 * @return
	 */
	public String getRelease() {
		return release;
	}
	/**
	 * ���÷�������
	 * @param release
	 */
	public void setRelease(String release) {
		this.release = release;
	}
	/**
	 * ��ȡʱ��
	 * @return
	 */
	public String getRunTime() {
		return runTime;
	}
	/**
	 * ����ʱ��
	 * @param runTime
	 */
	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	/**
	 * ��ȡ��Ա
	 * @return
	 */
	public List<String> getStars() {
		return stars;
	}
	/**
	 * ������Ա
	 * @param stars
	 */
	public void setStars(List<String> stars) {
		this.stars = stars;
	}
	/**
	 * ��ȡ����
	 * @return
	 */
	public String getDirector() {
		return director;
	}
	/**
	 * ���õ���
	 * @param director
	 */
	public void setDirector(String director) {
		this.director = director;
	}
	/**
	 * ��ȡ������
	 * @return
	 */
	public String getStudio() {
		return studio;
	}
	/**
	 * ������
	 * @param studio
	 */
	public void setStudio(String studio) {
		this.studio = studio;
	}
	/**
	 * ��ȡ������
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * ������
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * ��ȡ���
	 * @return
	 */
	public List<String> getGenres() {
		return genres;
	}
	/**
	 * �������
	 * @param genres
	 */
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
	/**
	 * ��ȡ����
	 * @return
	 */
	public String getCover() {
		return cover;
	}
	/**
	 * ���÷���
	 * @param cover
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}
	/**
	 * ��ȡԤ��ͼ
	 * @return
	 */
	public List<String> getPreviews() {
		return Previews;
	}
	/**
	 * ����Ԥ��ͼ
	 * @param previewss
	 */
	public void setPreviews(List<String> previews) {
		Previews = previews;
	}
	/**
	 * ��ȡ��������
	 * @return
	 */
	public List<Magnet> getMagnet() {
		return magnet;
	}
	/**
	 * ���ô�������
	 * @param magnet
	 */
	public void setMagnet(List<Magnet> magnet) {
		this.magnet = magnet;
	}
	/**
	 * ��û����
	 * @return
	 */
	public String getCensored() {
		return censored;
	}
	/**
	 * ��û����
	 * @param censored
	 */
	public void setCensored(String censored) {
		this.censored = censored;
	}
	
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	@Override
	public String toString() {
		return "MovieInfo [censored=" + censored + ", num=" + num + ", title=" + title + ", release=" + release
				+ ", runTime=" + runTime + ", stars=" + stars + ", director=" + director + ", studio=" + studio
				+ ", label=" + label + ", genres=" + genres + ", cover=" + cover + ", Previews=" + Previews
				+ ", series=" + series + ", magnet=" + magnet + "]";
	}

}
