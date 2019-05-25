package com.example.tinypet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
public class Aggregate {
	@Id String id;
	Long value;
	
	public Aggregate() {}	
	
	public Aggregate(String id, Long value) {
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}	
}
