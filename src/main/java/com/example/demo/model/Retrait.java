package com.example.demo.model;



import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("R")
public class Retrait extends  Operation{
	public Retrait() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Retrait(Date dateOperation, double montant, boolean deleted, Compte compte) {
		super(dateOperation, montant, deleted, compte);
	}

	public Retrait(Long numero, Date dateOperation, double montant, Compte compte, boolean deleted) {
		super(numero, dateOperation, montant, compte, deleted);
	}
}
