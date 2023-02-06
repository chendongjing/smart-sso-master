package com.smart.sso.server.encryption.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;

/**
 * Aes加解密
 * @Company cn.ctg
 * @author maiyufeng
 * @date 2022-4-07
 */
public class AesUtil {
    private static final Logger logger = LogManager.getLogger("AESUtil");

    private static final String CHARSET = "UTF-8";

    public static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static byte[] base64Decode(String base64Code) throws Exception {
        return Base64.decodeBase64(base64Code.getBytes(CHARSET));
    }

    /**
     * 加密
     *
     * @param content
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        initEncryptCipher(encryptKey);
        return encryptCipher.doFinal(content.getBytes(CHARSET));
    }
    /**
     * 加密
     *
     * @param content
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(byte[] content, String encryptKey) throws Exception {
        initEncryptCipher(encryptKey);
        return encryptCipher.doFinal(content);
    }

    /**
     * 加密转字符
     *
     * @param content
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * 解密
     *
     * @param content
     * @param decryptKey
     * @return
     * @throws Exception
     */
    public static byte[] aesDecryptByBytes(String content, String decryptKey) throws Exception {
        initDecryptCipher(decryptKey);
        return decryptCipher.doFinal(content.getBytes(CHARSET));
    }

    /**
     * 解密
     *
     * @param content
     * @param decryptKey
     * @return
     * @throws Exception
     */
    public static byte[] aesDecryptByBytes(byte[] content, String decryptKey) throws Exception {
        initDecryptCipher(decryptKey);
        return decryptCipher.doFinal(content);
    }

    public static String aesDecrypt(String content, String decryptKey) throws Exception {
        return new String(aesDecryptByBytes(base64Decode(content), decryptKey));
    }

    private static Cipher encryptCipher = null;

    private static Cipher decryptCipher = null;

    public static void initEncryptCipher(String aesKey) throws Exception {
        if (encryptCipher == null) {
            //5.根据字节数组生成AES密钥
            SecretKeySpec skeySpec = new SecretKeySpec(Hex.decodeHex(aesKey.toCharArray()), "AES");
            //6.根据指定算法AES自成密码器
            encryptCipher = Cipher.getInstance("AES");
            //7.初始化密码器
            encryptCipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        }
    }

    public static void initDecryptCipher(String aesKey) throws Exception {
        if (decryptCipher == null) {
            //5.根据字节数组生成AES密钥
            SecretKeySpec skeySpec = new SecretKeySpec(Hex.decodeHex(aesKey.toCharArray()), "AES");
            //6.根据指定算法AES自成密码器
            decryptCipher = Cipher.getInstance("AES");
            //7.初始化密码器
            decryptCipher.init(Cipher.DECRYPT_MODE, skeySpec);
        }
    }



    public static String hutoolEncrpt(String content,String key){
        AES aes=new AES(Mode.ECB, Padding.ISO10126Padding, key.getBytes());
        return aes.encryptBase64(content);
    }



    public static String hutoolDecrpt(String content,String key){
        AES aes=new AES(Mode.ECB, Padding.ISO10126Padding, key.getBytes());
        return aes.decryptStr(content);
    }



    public static void main(String[] args) throws Exception {
        final String KEY = "6f59159ec0f038f985bc0cc94f3be097";

        String privateKeyStr = "管理员";
        String encrypt = aesEncrypt(privateKeyStr, KEY);
        logger.info("加密后：" + encrypt);


        String decrypt = aesDecrypt(encrypt, KEY);
        logger.info("解密后：" + decrypt);
/*

        byte[] er = CtgAesUtil.aesDecryptByBytes(CtgAesUtil.base64Decode(encrypt), KEY);
        logger.info(new String(er));

        AES aes=new AES(Mode.ECB, Padding.ISO10126Padding, KEY.getBytes());

        List<SysUserDTO> userDTOS=new ArrayList<>();
        for (int i=0;i<=3;i++){
            SysUserDTO userDTO=new SysUserDTO();
            userDTO.setId(i+"asda");
            userDTO.setMobile("1834474111"+i);
            userDTO.setName("user"+i);
            userDTOS.add(userDTO);
        }
        String datess = hutoolEncrpt(JSONUtil.toJsonStr(userDTOS.get(0)), KEY);
        String s = hutoolDecrpt("5Vn+RR8ZvHgeqdmQctUNkvyZ3bwKJPkA5RGPgADmF6Y=",KEY);

*/
    }
    
}
