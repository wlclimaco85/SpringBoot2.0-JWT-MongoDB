package com.freebies.model;

import java.time.LocalDateTime;

public class LoginInfo extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String password;
	private Integer loginAttempts;
	private String loginAccountStatus;
	private String statusReason;
	private String pwdChangeReq; // Yes when user created and if requested for Reset password
	private String lastPwdDate;
	private String loginType;  // It should be user or system.
	private LocalDateTime lastLoginDate;
	private LocalDateTime lastLogoutDate;
	private boolean isFirstLogin;
	private boolean consentAccept;
	private LocalDateTime consentAcceptedDate;
	private LocalDateTime consentDeclineDate;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(LocalDateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Integer getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(Integer loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public String getLoginAccountStatus() {
		return loginAccountStatus;
	}

	public void setLoginAccountStatus(String loginAccountStatus) {
		this.loginAccountStatus = loginAccountStatus;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	public String getPwdChangeReq() {
		return pwdChangeReq;
	}

	public void setPwdChangeReq(String pwdChangeReq) {
		this.pwdChangeReq = pwdChangeReq;
	}

	public String getLastPwdDate() {
		return lastPwdDate;
	}

	public void setLastPwdDate(String lastPwdDate) {
		this.lastPwdDate = lastPwdDate;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public LocalDateTime getLastLogoutDate() {
		return lastLogoutDate;
	}

	public void setLastLogoutDate(LocalDateTime lastLogoutDate) {
		this.lastLogoutDate = lastLogoutDate;
	}

	/**
	 * @return the isFirstLogin
	 */
	public boolean isFirstLogin() {
		return isFirstLogin;
	}

	/**
	 * @param isFirstLogin the isFirstLogin to set
	 */
	public void setFirstLogin(boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	/**
	 * @return the consentAccept
	 */
	public boolean isConsentAccept() {
		return consentAccept;
	}

	/**
	 * @param consentAccept the consentAccept to set
	 */
	public void setConsentAccept(boolean consentAccept) {
		this.consentAccept = consentAccept;
	}

	/**
	 * @return the consentAcceptedDate
	 */
	public LocalDateTime getConsentAcceptedDate() {
		return consentAcceptedDate;
	}

	/**
	 * @param consentAcceptedDate the consentAcceptedDate to set
	 */
	public void setConsentAcceptedDate(LocalDateTime consentAcceptedDate) {
		this.consentAcceptedDate = consentAcceptedDate;
	}

	/**
	 * @return the consentDeclineDate
	 */
	public LocalDateTime getConsentDeclineDate() {
		return consentDeclineDate;
	}

	/**
	 * @param consentDeclineDate the consentDeclineDate to set
	 */
	public void setConsentDeclineDate(LocalDateTime consentDeclineDate) {
		this.consentDeclineDate = consentDeclineDate;
	}

}