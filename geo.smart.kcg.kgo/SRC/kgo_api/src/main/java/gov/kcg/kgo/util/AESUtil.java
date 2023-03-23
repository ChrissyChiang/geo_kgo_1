package gov.kcg.kgo.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AESUtil {

    // 算法/加密模式/數據填充方式
    private static final String ALGORITHMSTR = "AES/CBC/PKCS5Padding";

    public static final String KEY_ALGORITHM="AES";
    public static final String CIPHER_ALGORITHM="AES/ECB/PKCS7Padding";
    
    /**
     * 演算法/模式/填充
     **/
    private static final String CipherMode = "AES/CBC/PKCS7Padding";

    public static byte[] encrypt(String data, String iv, byte[] encryptKey, String alg) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());

        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey, "AES"), ivParameterSpec);

        byte[] encryptBytes = cipher.doFinal(data.getBytes());

        return encryptBytes;
    }

    public static byte[] decrypt(String iv, String alg, byte[] data, byte[] decryptKey) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey, "AES"), ivParameterSpec);

        byte[] decryptBytes = cipher.doFinal(data);

        return decryptBytes;
    }

    public static byte[] encrypt(String data, String alg, byte[] encryptKey) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);

        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey, "AES"));

        byte[] encryptBytes = cipher.doFinal(data.getBytes());

        return encryptBytes;
    }

    public static byte[] decrypt(String alg, byte[] encryptStr, byte[] decryptKey) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);

        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey, "AES"));

        byte[] decryptBytes = cipher.doFinal(encryptStr);

        return decryptBytes;
    }
    
    public static void main(String[] args) throws Exception {
		String dataStr = "jQp0Ukjp4E2w%2fCGVf4uYirsS00%2f2zQVmyTOnyGqJ8nIQnTIH3scOEp61dKSvUXBc5jRcffm3e359NiqQ1rJGcp3aJBnZBnIUGnAnn0so1MnrwIZf51aPSJe8cP0GtfrfN34USa1nptaxP9yK4jvIjRATk%2fIgyaAfIBb23fLqgM8ec6u7RrY1KeDPrFfS4k3C%2fwdL%2b0sL65tmRSJtlFOqRiRNklmXEnVJVxkZMNjvdt%2fW4d0fkK%2bK2dOYZwXuNuxWtT%2bJ8mxFi4lOnjxMkApnQru4N3Gu056FnfSfUSfnW6e6uDs8P3pw37TrlICkdFUVTx0JGXWhACzK2cmblJEnKjxU9%2fH7lHkJFwWOTF%2fl4JjlAFmkjWDEg6%2fOnr2opPcJ6mQJv%2bjruCiEt9iVYKfXtO4SjC%2fh9bsBBgkmc9v2pBYNtFv7UDuSJcGlXSx3QQIAZu0rclauMSksNbHpl5EYL69oLxYWB0j%2fs0V2zHiwssffiqfCOgb7UWeUniasHZ6nf2zeq9mzrPkcIq75vrnm14x8astVWft%2bv94%2bO5Nj0TV1Uyc03IqCuwnGVybCLF0hYf2Sv7bdU2RgXBTwEmmtdu6R5iMYj57zAp7BtqsjTKamf2qPpuQmtDt45VzHytd6%2b%2bIv2%2fEyaoLVKIWBBQE29HWCgokaUO%2bqx0YMrU%2f0pZwJJyd0Ey5i5YWKKrUkMEgjd8ubu%2fruheM4Qc4Y%2bEeelf%2b%2bvcTTwzkGekLTk9xW6odFTNxa8WKW%2f8CsGZ3zxO6Xos6JXVm1WFiEdyt0Su7Eg7Kj6X031DmgGus29n6TCE939t2weXYRMRlG0UMsTYD3";
//		String urlDecode = URLDecoder.decode(dataStr);
		
//		byte[] data = decrypt(urlDecode);
//    	System.out.println("解密後：" + new String(data));
    	
//    	String data = decrypt(urlDecode, cityKey, cityIv);
    	
//		byte[] data = decrypt22(Base64.getDecoder().decode(urlDecode), cityKey, cityIv);
//    	System.out.println("解密後：" + new String(data));
    	
    	
		Security.addProvider(new BouncyCastleProvider());  
    	String encRsData = "{" + 
    			"	\"ERROR_CODE\": \"0\"," + 
    			"	\"PUBLISH_INFO_LAST_UPDATE_TIME\": \"20201119173729+0800\"," + 
    			"	\"PUBLISH_INFO_LAST_UPDATE_TAG\": \"fb13f97565867386228a5464e5d83714\"," + 
    			"	\"PUBLISH_INFO_CONTENT\": {" + 
    			"		\"KCG_USER_BASIC_INFO\": {" + 
    			"			\"APP_COMPANY_ID\": \"KCG\"," + 
    			"			\"APP_USER_LOGIN_ID\": \"puman615\"," + 
    			"			\"AUTH_FROM_ADDRESS\": \"61.216.190.109\"," + 
    			"			\"AUTH_DATE\": \"20201119173729\"," + 
    			"			\"APP_USER_TW_SSN\": \"N125696899\"," + 
    			"			\"AUTH_METHOD\": [" + 
    			"				\"pass_idpass_auth\"" + 
    			"			]" + 
    			"		}" + 
    			"	}" + 
    			"}";
    	/** 程式資料平台 單登AES加密 key&iv */
        String  CITY_IV = "B6SO4II4q9uJfdkmrWWgZw==";
    	String CITY_KEY = "Ijh7MKntiwK7Ee0LAeIQsX3/jjj9hxE8DLYfpkahyYs=";
    	
//    	String encStr = cbcPkcs7Encrypt(encRsData, CITY_KEY, CITY_IV);
//    	
//    	String encAndUrlEnc = URLEncoder.encode(new String(encStr), "UTF-8");
//    	System.out.println("加密後：" + encAndUrlEnc);
    	
//    	String urlDecode = URLDecoder.decode(encAndUrlEnc, "UTF-8");
    	
    	String urlDecode = URLDecoder.decode("Cbm520%252FYePCj8m1zGUZZvbIbCZY4Nn5GZbofhEkx0oSOq045GXxVsd2CNz7b8fpoQ1Jt6W5X6fOdQ5%252BFcdAsKjbtq%252FU%252BNgzXCbs5I7WMuU3b3It4BoharnB5a7SiU8PGSCeu7HiLVU6lkmy1nxB8tJqJe7WKa1bRaLn55N4wmF06hqMuOC3HAVXelOK1GS%252F5Hub7QEQqHQIeiH%252FdY1DqMRj%252BE6r0HhIL0mTAhgNTgRVs%252BH8W3z1PuT772is1h2%252BFZx%252F3tOWujB5dxqNJQrWGaRYoHyGmAttIi6kQhUDdguin9u93WZJ50quyNp1PDT1TKLt4vo8D2IQ2n8Wg2cNBg8aiaEI9ZQyiEqwjSc81djLAf9sxN8r4IMdtKFfPbKMBFRUvZKb9OoW4s%252FImq86nk4Vk2ETFrJe%252BAqlIYx3EUQGGMZv1FuiyuUq0HbAbwQyxaKlS%252B5L%252FIS%252FILfx6yJ%252Fv7g%253D%253D", "UTF-8");
    	
		String data = cbcPkcs7Decrypt(urlDecode, CITY_KEY, CITY_IV);
    	System.out.println("解密後：" + new String(data));
    	
	}
    
    /**
     * AES/CBC/PKCS7Padding 加密
     * 演算法/模式/填充
     * @param data the data
     * @param key the key
     * @param iv the iv
     * @return the string
     * @throws Exception the exception
     */
    public static String cbcPkcs7Encrypt(String data, String key, String iv) throws Exception {
    	Security.addProvider(new BouncyCastleProvider()); 
        //加密之前，先從Base64格式還原到原始格式
        Decoder decoder = Base64.getDecoder();
    	byte[] dataByte = decoder.decode(Base64.getEncoder().encodeToString(data.replaceAll("[\\n\\t ]", "").getBytes()));
    	byte[] keyByte = decoder.decode(key);
    	byte[] ivByte = decoder.decode(iv);
    	
        String encryptedData = null;

        //指定算法，模式，填充方式，创建一个Cipher
        Cipher cipher = Cipher.getInstance(CipherMode);

        //生成Key对象
        Key sKeySpec = new SecretKeySpec(keyByte, "AES");
        
        //把向量初始化到算法参数
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");  
        params.init(new IvParameterSpec(ivByte));  

        //指定用途，密鑰，參數 初始化Cipher對象
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, params);

        //指定加密
        byte[] result = cipher.doFinal(dataByte);
            
        // 對結果進行Base64編碼，否則會得到一串亂碼，不便於後續操作
        Base64.Encoder encoder = Base64.getEncoder();
        encryptedData = encoder.encodeToString(result);	
    	return encryptedData;
    }
    
    /**
     * AES/CBC/PKCS7Padding 解密
     * 
     * 演算法/模式/填充
     *
     * @param content the content
     * @param key the key
     * @param iv the iv
     * @return the byte[]
     * @throws Exception 
     */
    public static String cbcPkcs7Decrypt(String data, String key, String iv) throws Exception {
        try {
        	Security.addProvider(new BouncyCastleProvider());
        	byte[] content = Base64.getDecoder().decode(data);
            SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));

            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] result = cipher.doFinal(content);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    
//    /**
//     * 加密
//     * @param content 加密的字串
//     * @param encryptKey key值
//     * @return
//     * @throws Exception
//     */
//    public static String encrypt(String content, String encryptKey) throws Exception {
//        KeyGenerator kgen = KeyGenerator.getInstance("AES");
//        kgen.init(128);
//        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
//        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
//        byte[] b = cipher.doFinal(content.getBytes("utf-8"));
//        // 採用base64演算法進行轉碼,避免出現中文亂碼
////        return Base64.encodeBase64String(b);
//        String strBase64 =  Base64.getEncoder().encodeToString(content.getBytes());
//        return strBase64;
//
//    }

}
