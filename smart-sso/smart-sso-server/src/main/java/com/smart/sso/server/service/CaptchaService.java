package com.smart.sso.server.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码生成接口
* @author:dongjing.chen
* @Description:
* @Company:ctg.cn
* @date:2023年2月3日
*/

public interface CaptchaService {
	
	void captcha(HttpServletRequest request, HttpServletResponse response)  throws Exception;

}
