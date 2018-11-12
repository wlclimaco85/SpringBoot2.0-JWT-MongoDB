package com.freebies.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "RoleInfo")
public class RoleInfo extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Id
	private String roleId;
	private String roleName;
	private String roleCode;

	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRole() {
		StringBuilder builder = new StringBuilder();
		builder.append(roleId);
		builder.append("-");
		builder.append(roleName);
		builder.append("-");
		return builder.toString();
	}

}