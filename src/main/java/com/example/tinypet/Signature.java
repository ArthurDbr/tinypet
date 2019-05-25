package com.example.tinypet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
public class Signature {
	@Id String id;
	String idUser;
	String idPetition;
	
	public Signature() {}	
	
	public Signature(String id, String idUser, String idPetition) {
		this.id = id;
		this.idUser = idUser;
		this.idPetition = idPetition;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getIdPetition() {
		return idPetition;
	}

	public void setIdPetition(String idPetition) {
		this.idPetition = idPetition;
	}

}
