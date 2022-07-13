package com.example.demo.model;

import java.io.Serializable;

import java.util.Collection;
import java.util.Date;


import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;




@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Type_CPTE",discriminatorType = DiscriminatorType.STRING,length = 2)
@SQLDelete(sql="UPDATE compte "+" SET deleted=true "+" WHERE code=?")
@Where(clause = "deleted=false")
@Table(name="compte")
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,include=JsonTypeInfo.As.PROPERTY,property ="type")
@JsonSubTypes({
	@Type(name="CC",value=CompteCourant.class),
	@Type(name="CE",value=CompteEpargne.class)	
})
public abstract class Compte implements  Serializable {
	
	@Id
	@NotEmpty
	private String code;
	
	@Min(100)
	private double sold;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	@Temporal(TemporalType.DATE)
	private Date dateCreation;
	
	@ManyToOne
	@JoinColumn(name="CODE_CLI")
	private Client client;
	
	@OneToMany(mappedBy = "compte")
	private Collection<Operation> operations;
	private Boolean deleted;
	public Compte() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Compte(String code, double sold, Date dateCreation, Client client,Boolean deleted) {
		super();
		this.code = code;
		this.sold = sold;
		this.dateCreation = dateCreation;
		this.client = client;
		this.deleted=deleted;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getSold() {
		return sold;
	}

	public void setSold(double sold) {
		this.sold = sold;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Collection<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Collection<Operation> operations) {
		this.operations = operations;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	
}
