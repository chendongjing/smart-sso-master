package com.smart.sso.server.encryption.util;


import cn.hutool.jwt.JWTUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加解密
 * @Company cn.ctg
 * @author maiyufeng
 * @date 2022-4-07
 */
public class RsaUtils {


    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static final String SIGN_ALGORITHMS = "SHA256withRSA";

    private static String ALGORITHM_RSA = "RSA";

    /**
     * 私钥加签名
     * @param encryptData
     * @param privateKey
     * @return
     */
    public static String rsaSign(String encryptData, String privateKey) {
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(loadPrivateKey(privateKey));
            signature.update(encryptData.getBytes());
            byte[] signed = signature.sign();
            return Base64.encodeBase64URLSafeString(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥验签
     * @param encryptStr
     * @param sign
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean verifySign(String encryptStr, String sign, String publicKey)throws Exception {
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(loadPublicKey(publicKey));
            signature.update(encryptStr.getBytes());
            return signature.verify(Base64.decodeBase64(sign));
        }  catch (NoSuchAlgorithmException e) {
            throw new Exception(String.format("验证数字签名时没有[%s]此类算法", SIGN_ALGORITHMS));
        } catch (InvalidKeyException e) {
            throw new Exception("验证数字签名时公钥无效");
        } catch (SignatureException e) {
            throw new Exception("验证数字签名时出现异常");
        }
    }


    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        byte[] buffer = Base64.decodeBase64(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        byte[] buffer = Base64.decodeBase64(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * @Description RSA解密
     * @author maiyufeng
     * @date 2022/4/6 22:23
     */

    protected static String rsaEncrypt(String sourceData, String key,boolean isPrivate){
        try {
            Key key1 = isPrivate ? loadPrivateKey(key) : loadPublicKey(key);
            byte[] data = sourceData.getBytes();
            byte[] dataReturn = new byte[0];
            Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, key1);

            // 加密时超过117字节就报错。为此采用分段加密的办法来加密
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i += MAX_ENCRYPT_BLOCK) {
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i,i + MAX_ENCRYPT_BLOCK));
                sb.append(new String(doFinal));
                dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
            }

            return Base64.encodeBase64URLSafeString(dataReturn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description RSA解密
     * @author maiyufeng
     * @date 2022/4/6 22:22
     */
    protected static String rsaDecrypt(String encryptedData, String key,boolean isPrivate){
        try {
            Key key1 = isPrivate ? loadPrivateKey(key) : loadPublicKey(key);
            byte[] data = Base64.decodeBase64(encryptedData);

            Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
            cipher.init(Cipher.DECRYPT_MODE, key1);

            // 解密时超过128字节就报错。为此采用分段解密的办法来解密
            byte[] dataReturn = new byte[0];
            for (int i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + MAX_DECRYPT_BLOCK));
                dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
            }

            return new String(dataReturn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 使用公钥将数据加密
     * @param sourceData
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String sourceData, String publicKey){
        return rsaEncrypt(sourceData,publicKey,false);
    }

    /**
     * 使用私钥将数据加密
     * @param sourceData
     * @param privateKey
     * @return
     */
    public static String privateEncrypt(String sourceData, String privateKey){
        return rsaEncrypt(sourceData,privateKey,true);
    }


    /**
     * 使用公钥解密
     * @param encryptedData
     * @param privateKey
     * @return
     */
    public static String publicDecrypt(String encryptedData, String privateKey) {
        return rsaDecrypt(encryptedData,privateKey,false);
    }

    /**
     * 使用私钥解密
     * @param encryptedData
     * @param privateKey
     * @return
     */
    public static String privateDecrypt(String encryptedData, String privateKey) {
        return rsaDecrypt(encryptedData,privateKey,true);
    }



    public static void main(String[] args) {
        String password = "123";
        Map<String,Object> map=new HashMap<>();
        map.put("user_id","ouNfk4hjaQX1UOVUXaz8PhbXhik8");
        map.put("sessionKey","1233444");
        String message = JWTUtil.createToken(map, password.getBytes());

        try {

            String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6BSDlbRplhMMNZBKHX4xe8AwE" +
                    "SpzHVfAcHHsX9FFSMuF91W3cxgT/g5n+qlLLFzCE3hWG/yX5NMAxR4mS3MlhyXKw" +
                    "ko3tK9Ua691afod1lxORR3IaZ8nV7v5Bv8y4JDe4E3/f/bQIGzroWiJ0sXTcO41G" +
                    "qvOw3G9leClSvjVnSwIDAQAB";
            String privateKeyStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALoFIOVtGmWEww1k" +
                    "EodfjF7wDARKnMdV8Bwcexf0UVIy4X3VbdzGBP+Dmf6qUssXMITeFYb/Jfk0wDFH" +
                    "iZLcyWHJcrCSje0r1Rrr3Vp+h3WXE5FHchpnydXu/kG/zLgkN7gTf9/9tAgbOuha" +
                    "InSxdNw7jUaq87Dcb2V4KVK+NWdLAgMBAAECgYBqCihhgJtOiarjBEvnrZkIOZCw" +
                    "FZRfsWaJr9afph+BWw3dvH+/HYaV3YA4gwFlUlfPNgZRiTstX1u7+8q51HBa+08h" +
                    "jPE8Q4GhoUY+sQ9MB8NXA6SWHNPPfMOYIeKEtKmNBdgIbtuhnob3o18rJNFIY+qC" +
                    "i8djf4om93+AChmo6QJBAO31hd9qem7BnHXsxiMwS+iHlRjW9KxXva2zf+BNURSR" +
                    "Z19cePReHJGE4v1C731MZlygTB5zKChQ8uZ3JLKJeX8CQQDIH4k/xbuhMb8dMdzl" +
                    "AYN/CU+MgfWjlgbYjxOnTaLcbs5Mlz9v3/5I/FwqxPvzGuCjHkyh08oFfnQXvzdj" +
                    "YMA1AkEApjgyOnzzZviBZXJueVgcPiKvSHmm0dg8W+Cd+72mXHqxPdCngPNYe2Ha" +
                    "+VRPXDQI8LzcTwzbyUW6Vrh0/u2+2wJBAK1rZqx01VuimFLcWue4oBL+JolENXFF" +
                    "GTmhAw8AIBmVjACjML3qBZmJ1vTZLtxEdlXkc9PojDCmnEPX2E+uD+ECQF2eX4EY" +
                    "X95HDzQ4cm1kGQudjgfH1gZ+30DIindIHXNAOFpYeAUD7yUQP5tZO8nG38gybPJg" +
                    "FoadlsSMIQIpksM=";

            //加密
            String privateEncryptStr = privateEncrypt(message, privateKeyStr);
            String publicEncryptStr = publicEncrypt(message, publicKeyStr);
            String privateEncryptSign = rsaSign(privateEncryptStr,privateKeyStr);
            String publicEncryptSign = rsaSign(publicEncryptStr,privateKeyStr);

            System.out.println("source:" + message);
            System.out.println("private encryptStr: " + privateEncryptStr);
            System.out.println("public encryptStr: " + publicEncryptStr);
            System.out.println("private encrypt sign: " + privateEncryptSign);
            System.out.println("public encrypt sign: " + publicEncryptSign);
            System.out.println("public decrypt:" + publicDecrypt(privateEncryptStr, publicKeyStr));
            System.out.println("private decrypt:" + privateDecrypt(publicEncryptStr, privateKeyStr));
            System.out.println("verifySign1: " + verifySign(privateEncryptStr,privateEncryptSign,publicKeyStr));
            System.out.println("verifySign2: " + verifySign(publicEncryptStr,publicEncryptSign,publicKeyStr));




        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
