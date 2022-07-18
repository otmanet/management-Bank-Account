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

	public Retrait( Date dateOperation, double montant, Compte compte,boolean deleted) {
		super( dateOperation, montant, compte, deleted);
		// TODO Auto-generated constructor stub
	}

}
