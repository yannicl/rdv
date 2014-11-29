package com.yannic.rdv.data.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.yannic.rdv.data.model.association.AccountPersonAssociation;
import com.yannic.rdv.data.model.type.LoginMethod;

@Entity
@Table(indexes = {@Index(columnList = "api_key", name="api_key_index", unique=true)}, name = "accounts")
public class Account {
	
	@Id
	@Column(name = "account_id")
	private Long accountId;
	
	private String username;
	
	private String password;
	
	private String salt;
	
	@Column(name = "last_login_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDate;
	
	@Column(name = "api_key")
	private String apiKey;
	
	@Enumerated(EnumType.STRING)
	private LoginMethod method;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name = "account_person",
		joinColumns={ @JoinColumn(name="account_id", referencedColumnName="account_id") },
		inverseJoinColumns={ @JoinColumn(name="account_person_id", referencedColumnName="account_person_id", unique=true) }
	)
	private List<AccountPersonAssociation> accountPersonAssociations;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public LoginMethod getMethod() {
		return method;
	}

	public void setMethod(LoginMethod method) {
		this.method = method;
	}

	public List<AccountPersonAssociation> getAccountPersonAssociations() {
		return accountPersonAssociations;
	}

	public void setAccountPersonAssociations(
			List<AccountPersonAssociation> accountPersonAssociations) {
		this.accountPersonAssociations = accountPersonAssociations;
	}

	

}
