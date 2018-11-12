package com.freebies.model;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.freebies.common.EncryptionUtility;


@Document(collection = "UserInfo")
public class UserInfo extends BaseModel{

	
	@Id
	private String userId;
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String email;
	private String contact;
	private String associateType;
	private String employeeId;
	private String userStatus;

	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	private String updatedBy;
   
    private RoleInfo roleInfo;
	
	private String locale;
	
	LoginInfo loginInfo;
	
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
		
	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return EncryptionUtility.decrypt(email);
	}

	public void setEmail(String email) {
		this.email = EncryptionUtility.encrypt(email);
	}

	public String getContact() {
		return EncryptionUtility.decrypt(contact);
	}

	public void setContact(String contact) {
		this.contact = EncryptionUtility.encrypt(contact);
	}

	public String getAssociateType() {
		return associateType;
	}

	public void setAssociateType(String associateType) {
		this.associateType = associateType;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public RoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}


	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
    
}
