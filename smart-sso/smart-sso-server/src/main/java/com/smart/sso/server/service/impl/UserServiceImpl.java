package com.smart.sso.server.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.sso.server.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.sso.client.rpc.Result;
import com.smart.sso.client.rpc.SsoUser;
import com.smart.sso.server.model.User;
import com.smart.sso.server.service.UserService;

@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
	@Autowired
	private UserMapper userMapper;

	/**
	 * 短信验证码开关，0为开，1为关
	 */
	private Integer messageCode = 1;

	/**
	 * 图形验证码开关，0为开，1为关
	 */
	private Integer graphCode = 1;

	@Override
	public Result<SsoUser> login(String loginName, String password) {
		
		//TODO
		//这里调取数据库查询登录账号密码，进行判断用户登录账号和密码是否匹配
		User user = userMapper.getUserByNamePWD(loginName, password);
		if (ObjectUtil.isNotNull(user)) {
			//校验密码复杂度
			Boolean checkPWD = checkPWD(password);
			if (!checkPWD) {
				return Result.createError("密码复杂度不合格，至少包含大写字母、小写字母、数字或特殊符号中的任意三种");
			}
			//校验短信验证码
			if (messageCode == 0) {

			}
			//校验图形验证码
			if (graphCode == 0) {

			}
			if (user.getLoginName().equals(loginName)) {
				if(user.getPassword().equals(password)) {
					return Result.createSuccess(new SsoUser(user.getId(), user.getLoginName()));
				}
				else {
					return Result.createError("密码有误");
				}
			}
		}
		return Result.createError("用户不存在");
	}

	/**
	 * 校验复杂度
	 */
	public Boolean checkPWD(String PWD) {
		// 规定的正则表达式
		// (?![a-zA-Z]+$) 表示 字符串不能完全由大小写字母组成
		// (?![A-Z0-9]+$) 表示 字符串不能完全由大写字母和数字组成
		// (?![A-Z\W_]+$) 表示 字符串不能完全由大写字母和特殊字符组成
		// (?![a-z0-9]+$) 表示 字符串不能完全由小写字母和数字组成
		// (?![a-z\W_]+$) 表示 字符串不能完全由小写字母和特殊字符组成
		// (?![0-9\W_]+$) 表示 字符串不能完全由数字和特殊字符组成
		// [a-zA-Z0-9\W_]{8,} 表示 字符串应该匹配大小写字母、数字和特殊字符，至少匹配8次
		String regex = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![a-z0-9]+$)(?![A-Z\\W_]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,}$";
		return ReUtil.isMatch(regex, PWD);
	}

}
