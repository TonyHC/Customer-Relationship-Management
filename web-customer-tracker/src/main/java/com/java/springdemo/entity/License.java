package com.java.springdemo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "license")
public class License {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "license_name")
	private String licenseName;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "expiration_date")
	private Date expirationDate;
	
	public License() {
		
	}
	
	public License(String licenseName, Date startDate, Date expirationDate) {
		this.licenseName = licenseName;
		this.startDate = startDate;
		this.expirationDate = expirationDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public String toString() {
		return "License [id=" + id + ", licenseName=" + licenseName + ", startDate=" + startDate + ", expirationDate="
				+ expirationDate + "]";
	}
}