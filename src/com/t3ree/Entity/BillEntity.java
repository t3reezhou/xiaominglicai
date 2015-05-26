package com.t3ree.Entity;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class BillEntity implements Serializable {
	private int id;
	private String bid;
	private int type;
	private Float used;
	private String other;
	private String date;
	private int synchronization = 0;

	private int position;

	public BillEntity() {
	}

	public BillEntity(int type, float used, String other, String date) {
		this.type = type;
		this.used = used;
		this.other = other;
		this.date = date;
	}

	public BillEntity(int type, float used, String date) {
		this.type = type;
		this.used = used;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getUsed() {
		return used;
	}

	public void setUsed(float used) {
		this.used = used;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getSynchronization() {
		return synchronization;
	}

	public void setSynchronization(int synchronization) {
		this.synchronization = synchronization;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
