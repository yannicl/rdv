package com.yannic.rdv.data.model.association;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.data.model.Person;
import com.yannic.rdv.data.model.type.AccountPersonRelation;

@Entity
@Table(name = "account_person")
public class AccountPersonAssociation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_person_id")
	private long accountPersonAssociationId;

	@OneToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	@OneToOne
	@JoinColumn(name = "person_id")
	private Person person;
	
	@Enumerated(EnumType.STRING)
	private AccountPersonRelation relation;
	

	public long getAccountPersonAssociationId() {
		return accountPersonAssociationId;
	}

	public void setAccountPersonAssociationId(long accountPersonAssociationId) {
		this.accountPersonAssociationId = accountPersonAssociationId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public AccountPersonRelation getRelation() {
		return relation;
	}

	public void setRelation(AccountPersonRelation relation) {
		this.relation = relation;
	}
	
	
}
