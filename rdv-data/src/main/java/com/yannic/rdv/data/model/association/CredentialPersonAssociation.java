package com.yannic.rdv.data.model.association;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.yannic.rdv.data.model.Credential;
import com.yannic.rdv.data.model.Person;
import com.yannic.rdv.data.model.type.CredentialPersonRelation;

@Entity
@Table(name = "credential_person")
public class CredentialPersonAssociation {
	
	@Id
	@Column(name = "credential_person_id")
	private long credentialPersonAssociationId;

	@OneToOne
	@JoinColumn(name = "credential_id")
	private Credential credential;
	
	@OneToOne
	@JoinColumn(name = "person_id")
	private Person person;
	
	@Enumerated(EnumType.STRING)
	private CredentialPersonRelation relation;
	

	public long getCredentialPersonAssociationId() {
		return credentialPersonAssociationId;
	}

	public void setCredentialPersonAssociationId(long credentialPersonAssociationId) {
		this.credentialPersonAssociationId = credentialPersonAssociationId;
	}

	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public CredentialPersonRelation getRelation() {
		return relation;
	}

	public void setRelation(CredentialPersonRelation relation) {
		this.relation = relation;
	}
	
	
}
