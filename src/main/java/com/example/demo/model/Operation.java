package com.example.demo.model;


import java.util.Date;


import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 2)
@SQLDelete(sql = "UPDATE operation"+" SET deleted=true "+" WHERE numero=?")
@Where(clause = "deleted=false")
@Table(name="operation")
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,include=JsonTypeInfo.As.PROPERTY,property ="typeOperation")
@JsonSubTypes({
	@Type(name="R",value=Retrait.class),
	@Type(name="V",value=Versement.class)	
})
public abstract class Operation {
	@Id
	@GeneratedValue
	private Long numero;
	private Date dateOperation;
	private double montant;
	private boolean deleted;

	@ManyToOne
	@JoinColumn(name = "CODE_CPTE")
	private Compte compte;
	public Operation() {super();}

	public Operation(Date dateOperation, double montant, boolean deleted, Compte compte) {
		this.dateOperation = dateOperation;
		this.montant = montant;
		this.deleted = deleted;
		this.compte = compte;
	}

	public Operation(Long numero, Date dateOperation, double montant, Compte compte, boolean deleted) {
		super();
		this.numero=numero;
		this.dateOperation = dateOperation;
		this.montant = montant;
		this.compte = compte;
		this.deleted=deleted;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public java.util.Date getDateOperation() {
		return dateOperation;
	}

	public void setDateOperation(java.util.Date dateOperation) {
		this.dateOperation = dateOperation;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
