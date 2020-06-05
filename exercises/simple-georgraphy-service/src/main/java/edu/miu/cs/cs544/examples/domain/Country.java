package edu.miu.cs.cs544.examples.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.BatchSize;

@Entity
@BatchSize(size = 20)
public class Country {
	
	@Id
	@Column(name = "COUNTRY_ID")
	private Integer id;
	
	@Column(name = "COUNTRY")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
