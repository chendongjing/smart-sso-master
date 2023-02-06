package com.smart.sso.server.common;

/**
 * 含时效
 * 
 * @author dongjing.chen
 */
public interface Expiration {
	
	/**
	 * 时效（秒）
	 * @return
	 */
	int getExpiresIn();
}
