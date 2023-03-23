package gov.kcg.kgo.util;

import org.springframework.util.Base64Utils;

import java.security.MessageDigest;

public class SHAUtil {

    public static String encrypt(String plainText, String alg) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance(alg);
        messageDigest.update(plainText.getBytes("UTF-8"));

        return Base64Utils.encodeToString(messageDigest.digest());
    }

}
