package com.example.demo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("V")

public class Versement extends Operation {

	public Versement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Versement(java.util.Date dateOperation, double montant, Compte compte, boolean deleted) {
		super(dateOperation, montant, compte, deleted);
		// TODO Auto-generated constructor stub
	}

}
