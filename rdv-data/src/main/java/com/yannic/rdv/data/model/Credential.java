package com.yannic.rdv.data.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.yannic.rdv.data.model.association.CredentialPersonAssociation;
import com.yannic.rdv.data.model.type.CredentialMethod;

@Entity
@Table(name = "credentials")
public class Credential {
	
	@Id
	@Column(name = "credential_id")
	private long credentialId;
	
	private String username;
	
	private String password;
	
	private String salt;
	
	@Column(name = "last_login_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDate;
	
	@Column(name = "session_key")
	private String sessionKey;
	
	@Enumerated(EnumType.STRING)
	private CredentialMethod method;
	
	@OneToMany
	@JoinTable(
		name = "credential_person",
		joinColumns={ @JoinColumn(name="credential_id", referencedColumnName="credential_id") },
		inverseJoinColumns={ @JoinColumn(name="credential_person_id", referencedColumnName="credential_person_id", unique=true) }
	)
	private List<CredentialPersonAssociation> credentialPersonAssociations;

	public long getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(long credentialId) {
		this.credentialId = credentialId;
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

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public CredentialMethod getMethod() {
		return method;
	}

	public void setMethod(CredentialMethod method) {
		this.method = method;
	}

	public List<CredentialPersonAssociation> getCredentialPersonAssociations() {
		return credentialPersonAssociations;
	}

	public void setCredentialPersonAssociations(
			List<CredentialPersonAssociation> credentialPersonAssociations) {
		this.credentialPersonAssociations = credentialPersonAssociations;
	}

}
