package com.example.demo.model;




import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

@Entity
@DiscriminatorValue("CC")
@SQLDelete(sql="UPDATE compte "+" SET deleted=true "+" WHERE code=?")
@Where(clause = "deleted=false")
@Table(name="compte")
public class CompteCourant extends Compte {
	
	@NotNull
	@Min(100)
	private double decouvert;

	public CompteCourant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompteCourant(@NotEmpty String code,@Size(min=100) double sold,@NotEmpty Date dateCreation, Client client, double decouvert,Boolean deleted) {
		super(code, sold, dateCreation, client,deleted);
		this.decouvert = decouvert;
	}

	public double getDecouvert() {
		return decouvert;
	}

	public void setDecouvert(double decouvert) {
		this.decouvert = decouvert;
	}

	
}
