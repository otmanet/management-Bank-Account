package com.example.demo.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;





@Entity
@SQLDelete(sql = "UPDATE client "+" SET deleted=true "+" WHERE id=? ")
@Where(clause = "deleted=false")
@Table(name="client")
//use interface Serializable when do want store or send object
public class Client implements Serializable {
	@Id
	@GeneratedValue
	private Long id;
	@NotEmpty
	@Size(max=8)
	private String nom; 

	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
	private Collection<Compte> compte;
	
	private Boolean deleted;
	
	public Client() { super();}
	public Client(String nom,Boolean deleted) {
		this.nom=nom;
		this.deleted=deleted;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	@JsonIgnore
	public Collection<Compte> getCompte() {
		return compte;
	}
	public void setCompte(Collection<Compte> compte) {
		this.compte = compte;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
