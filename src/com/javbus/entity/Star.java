package com.javbus.entity;

public class Star {
	/**
	 * ����
	 */
	String name ;
	/**
	 * ����
	 */
	String birthday;
	/**
	 * ����
	 */
	String age;
	/**
	 * ���
	 */
	String height;
	/**
	 * �ֱ�
	 */
	String cup;
	/**
	 * ��Χ
	 */
	String bust;
	/**
	 * ��Χ
	 */
	String waist;
	/**
	 * ��Χ
	 */
	String hips;
	/**
	 * ������
	 */
	String hometown;
	/**
	 * ͷ��
	 */
	String image;
	/**
	 * ����
	 */
	String hobby;
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getCup() {
		return cup;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	/**
	 * ��Χ
	 * @return
	 */
	public String getBust() {
		return bust;
	}
	/**
	 * ��Χ
	 * @param bust
	 */
	public void setBust(String bust) {
		this.bust = bust;
	}
	/**
	 * ��Χ
	 * @return
	 */
	public String getWaist() {
		return waist;
	}
	/**
	 * ��Χ
	 * @param waist
	 */
	public void setWaist(String waist) {
		this.waist = waist;
	}
	/**
	 * ��Χ
	 * @return
	 */
	public String getHips() {
		return hips;
	}
	/**
	 * ��Χ
	 * @param hips
	 */
	public void setHips(String hips) {
		this.hips = hips;
	}
	public String getHometown() {
		return hometown;
	}
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Star [name=" + name + ", birthday=" + birthday + ", age=" + age + ", height=" + height + ", cup=" + cup
				+ ", bust=" + bust + ", waist=" + waist + ", hips=" + hips + ", hometown=" + hometown + ", image="
				+ image + ", hobby=" + hobby + "]";
	}

}
