package com.example.demo.model;



import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

@Entity
@DiscriminatorValue("CE")
@SQLDelete(sql="UPDATE compte "+" SET deleted=true "+" WHERE code=?")
@Where(clause = "deleted=false")
@Table(name="compte")
public class CompteEpargne extends Compte {
	@NotNull
	@Min(100)
	private double taux;

	public CompteEpargne() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompteEpargne(@NotEmpty String code, double sold, Date dateCreation, Client client, @NotNull double taux,Boolean deleted) {
		super(code, sold, dateCreation, client,deleted);
		this.taux = taux;
	}

	public double getTaux() {
		return taux;
	}

	public void setTaux(double taux) {
		this.taux = taux;
	}
	
}
