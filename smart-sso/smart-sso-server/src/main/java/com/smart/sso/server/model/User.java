package com.smart.sso.server.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户
 */
@Data
@TableName(value = "sso_user")
public class User implements Serializable {

	private static final long serialVersionUID = 10125567610925057L;

	/** ID */
	private Integer id;
	/** 用户姓名 */
	private String userName;
	/** 登录名 */
	private String loginName;
	/** 密码 */
	private String password;
	
	public User() {
		super();
	}

	public User(Integer id, String userName, String loginName, String password) {
		super();
		this.id = id;
		this.userName = userName;
		this.loginName = loginName;
		this.password = password;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
