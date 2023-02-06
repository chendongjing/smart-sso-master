package com.smart.sso.server.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.events.AbstractSessionEvent;
import org.springframework.session.web.http.SessionEventHttpSessionListenerAdapter;

import com.smart.sso.client.SmartContainer;
import com.smart.sso.client.filter.LoginFilter;
import com.smart.sso.client.filter.LogoutFilter;
import com.smart.sso.client.listener.LogoutListener;
import com.smart.sso.client.session.SessionMappingStorage;

@Configuration
public class SmartSsoConfig {

    @Value("${sso.server.url}")
    private String serverUrl;
    @Value("${sso.app.id}")
    private String appId;
    @Value("${sso.app.secret}")
    private String appSecret;
    
	/**
	 * 单实例方式注册单点登出Listener
	 * 
	 * @return
	 */
//	@Bean
//	public ServletListenerRegistrationBean<HttpSessionListener> LogoutListener() {
//		ServletListenerRegistrationBean<HttpSessionListener> listenerRegBean = new ServletListenerRegistrationBean<>();
//		LogoutListener logoutListener = new LogoutListener();
//		listenerRegBean.setListener(logoutListener);
//		return listenerRegBean;
//	}

	/**
	 * 分布式redis方式注册单点登出Listener
	 * 
	 * 注：
	 * 1.需注入RedisSessionMappingStorage
	 * 2.需要使用Spring方式注入LogoutListener，使用ServletListenerRegistrationBean方式不生效
	 */
    @Autowired
	private SessionMappingStorage sessionMappingStorage;
//
//	@Bean
//	public SessionMappingStorage sessionMappingStorage() {
//		return this.sessionMappingStorage = new RedisSessionMappingStorage();
//	}

	@Bean
	public ApplicationListener<AbstractSessionEvent> LogoutListener() {
		List<HttpSessionListener> httpSessionListeners = new ArrayList<>();
		LogoutListener logoutListener = new LogoutListener();
		logoutListener.setSessionMappingStorage(sessionMappingStorage);
		httpSessionListeners.add(logoutListener);
		return new SessionEventHttpSessionListenerAdapter(httpSessionListeners);
	}

	/**
	 * 单点登录Filter容器
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<SmartContainer> smartContainer() {
		SmartContainer smartContainer = new SmartContainer();
		smartContainer.setServerUrl(serverUrl);
		smartContainer.setAppId(appId);
		smartContainer.setAppSecret(appSecret);
		
		// 忽略拦截URL,多个逗号分隔
        smartContainer.setExcludeUrls("/login,/logout,/oauth2/*,/custom/*,/assets/*");

		smartContainer.setFilters(new LogoutFilter(), new LoginFilter());

		FilterRegistrationBean<SmartContainer> registration = new FilterRegistrationBean<>();
		registration.setFilter(smartContainer);
		registration.addUrlPatterns("/*");
		registration.setOrder(1);
		registration.setName("smartContainer");
		return registration;
	}
}
