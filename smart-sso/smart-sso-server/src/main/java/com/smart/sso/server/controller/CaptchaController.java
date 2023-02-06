package com.smart.sso.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wf.captcha.utils.CaptchaUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 普通验证码生成类
 * @author:dongjing.chen
 * @Description:
 * @Company:ctg.cn
 * @date:2022年8月17日
 */
@RestController
@RequestMapping("/tCaptcha")
@Slf4j
public class CaptchaController {
	 
	@GetMapping("captcha")
	public  void  captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 
		// 设置位数
	//	CaptchaUtil.out(4, request, response);
		// 设置宽、高、位数
		CaptchaUtil.out(130, 48, 4, request, response);
		// 使用gif验证码
		//GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 4);
		response.setContentType("image/gif"); //设置请求以及响应的内容类型以及编码方式
	    response.setCharacterEncoding("UTF-8");
		//CaptchaUtil.out(gifCaptcha, request, response);
		String captcha = (String) request.getSession().getAttribute("captcha");
		log.info("本次生成验证码成功:"+captcha);
		
	}
	
   
}
