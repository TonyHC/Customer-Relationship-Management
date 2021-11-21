package com.crm.customertracker.entity.customer;

import lombok.*;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Override
	public String toString() {
		return "License [id=" + id + ", licenseName=" + licenseName + ", startDate=" + startDate + ", expirationDate="
				+ expirationDate + "]";
	}
}