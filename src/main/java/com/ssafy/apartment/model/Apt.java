package com.ssafy.apartment.model;

public class Apt {
	private String dong;
	private String apt_name;
	private String floor;
	private String size;
	private String price;
	public Apt() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Apt(String dong, String apt_name, String floor, String size, String price) {
		super();
		this.dong = dong;
		this.apt_name = apt_name;
		this.floor = floor;
		this.size = size;
		this.price = price;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getApt_name() {
		return apt_name;
	}
	public void setApt_name(String apt_name) {
		this.apt_name = apt_name;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Apt [dong=" + dong + ", apt_name=" + apt_name + ", floor=" + floor + ", size=" + size + ", price="
				+ price + "]";
	}
		
}
