package com.smart.sso.server.encryption.util;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * SHA非对称加密算法（不可解密）
 * 
 * @author:dongjing.chen
 * @Description:
 * @Company:ctg.cn
 * @date:2022年3月23日
 */
public class SHAUtils {

	// ASC 加密秘钥

	/**
	 * SHA非对称加密算法
	 * 
	 * @param spara
	 * @return
	 */
	public static String getSHA(String spara) {
		String sRtn = null;
		try {
			byte[] plainText = spara.getBytes("UTF8");

			// 使用getInstance("算法")来获得消息摘要,这里使用SHA-1的160位算法
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			// 开始使用算法
			messageDigest.update(plainText);
			// 输出算法运算结果
			Base64.Encoder encoder = Base64.getMimeEncoder();
			sRtn = encoder.encodeToString(messageDigest.digest());
//			sRtn = new BASE64Encoder().encode(messageDigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sRtn;
	}

	// 测试
	public static void main(String[] args) throws Exception {

		// String sourceStr1 = "86-15013658623";
		// String sourceStr1 = "86-15177009152";
		String sourceStr = "86-15013658623";
		// String sourceStr1 = "86-15527266840";
		String s = getSHA(sourceStr);
		System.out.println(s);

	}

}
