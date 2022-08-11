package com.example.demo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("V")

public class Versement extends Operation {

	public Versement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Versement(Date dateOperation, double montant, boolean deleted, Compte compte) {
		super(dateOperation, montant, deleted, compte);
	}

	public Versement(Long numero, Date dateOperation, double montant, Compte compte, boolean deleted) {
		super(numero, dateOperation, montant, compte, deleted);
	}
}
