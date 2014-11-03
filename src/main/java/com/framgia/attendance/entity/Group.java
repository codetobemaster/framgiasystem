package com.framgia.attendance.entity;

import java.io.Serializable;

import org.seasar.dao.annotation.tiger.Bean;

@Bean(table = "group")
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer groupId;
	private String groupName;
	private int approverId;
	private String approverName;
	private Boolean checked = false;
	
	public int getApproverId() {
		return approverId;
	}

	public void setApproverId(int approverId) {
		this.approverId = approverId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getApproverName() {
		return approverName;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

}
