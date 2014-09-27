package com.smartstamp.tab.list;

import android.graphics.drawable.Drawable;

public class ItemRow {

	String brand;
	String branch;
	Integer mCafeBackgrounds;
	Integer coupon;
	Integer couponCountImage;
	
	
	
	
	public ItemRow(String brand, String branch, Integer mCafeBackgrounds, Integer coupon , Integer couponCountImage) {
		super();
		this.brand = brand;
		this.branch = branch;
		this.mCafeBackgrounds = mCafeBackgrounds;
		this.coupon = coupon;
		this.couponCountImage = couponCountImage;
	}

	public String getCafeTitle() {
		return brand;
	}
	public void setCafeTitle(String brand) {
		this.brand = brand;
	}
	public String getCafeMakerNames() {
		return branch;
	}
	public void setCafeMakerNames(String branch) {
		this.branch = branch;
	}
	
	
	public Integer getCafeBackgrounds() {
		return mCafeBackgrounds;
	}
	public void setCafeBackgrounds(Integer mCafeBackgrounds) {
		this.mCafeBackgrounds = mCafeBackgrounds;
	}
	
	
	
	public Integer getCoupon() {
		return coupon;
	}
	public void setCoupon(Integer coupon) {
		this.coupon = coupon;
	}
	
	public Integer getCouponCountImage() {
		return couponCountImage;
	}
	public void setCouponCountImage(Integer couponCountImage) {
		this.couponCountImage = couponCountImage;
	}
	
	
	
}
