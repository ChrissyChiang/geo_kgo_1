package gov.kcg.kgo.geoutil;

import gov.kcg.kgo.geomodel.GeoKeyValueModel;
import gov.kcg.kgo.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * GEO 20210829 add
 * <p>
 * DTO for 共用字串處理物件
 */
public class GeoStringUtil {

    public static final String QUESTION_MAIN_ID_PREFIX_CHAR = "QM";
    public static final String QUESTION_MAIN_TOPIC_ID_PREFIX_CHAR = "QMT";
    public static final String QUESTION_TOPIC_ID_PREFIX_CHAR = "QT";
    public static final String QUESTION_TOPIC_DETAIL_ID_PREFIX_CHAR = "QTD";
    public static final String QUESTION_CASESET_TOPIC_ID_PREFIX_CHAR = "QCT";
    public static final String QUESTION_CASESET_DETAIL_ID_PREFIX_CHAR = "QCD";
    public static final String QUESTION_ANSWER_ID_PREFIX_CHAR = "QA";
    public static final String QUESTION_ANSWER_DETAIL_ID_PREFIX_CHAR = "QAD";
    public static final String APPOINTMENT_MAIN_ID_PREFIX_CHAR = "AM";
    public static final String APPOINTMENT_DETAIL_PREFIX_CHAR = "AD";
    public static final String APPOINTMENT_DETAIL_TIME_PREFIX_CHAR = "ADT";
    public static final String APPOINTMENT_DETAIL_NUMBERS_PREFIX_CHAR = "ADN";
    public static final String APPOINTMENT_BLOCK_USER_ID_PREFIX_CHAR = "ABU";
    public static final String APPOINTMENT_PREFIX_CHAR = "AO";
    public static final String AGENT_PREFIX_CHAR = "AG";
    public static final String FRONTEND_USER_PREFIX_CHAR = "FU";
    public static final String BACKEND_USER_PREFIX_CHAR = "BEUSER";
    public static final String CASE_CROSS_REVIEW_PREFIX_CHAR = "CCS";

    private static final String DEFAULT_CIPHER_AES_ALGORITHM = "AES/CBC/PKCS7Padding";
    private static BouncyCastleProvider bouncyCastleProvider;

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoStringUtil.class);

    public static List<GeoKeyValueModel> parseKeyValue(String oldStr) {
        //將 key1-value1,key2-value2,key3-value3...格式字串組合成物件
        List<GeoKeyValueModel> dtoList = null;
        List<String> itemsList = Arrays.asList(oldStr.split(","));
        if (itemsList != null) {
            dtoList = new ArrayList<>();
            for (int i = 0; i < itemsList.size(); i++) {
                if (itemsList.get(i).indexOf("-") > 0) {
                    GeoKeyValueModel dto = new GeoKeyValueModel();
                    dto.setKeyData(itemsList.get(i).substring(0, itemsList.get(i).indexOf("-")));
                    dto.setValueData(itemsList.get(i).substring(itemsList.get(i).indexOf("-") + 1));
                    dtoList.add(dto);
                }
            } //for (int i=0; i<itemsList.size(); i++)
        } //if (itemsList!=null)
        return dtoList;
    } //parseKeyValue

    public static String getCurrentDateStr() {
        return DateUtil.timestampToString(DateUtil.getCurrentTimestamp(), DateUtil.PATTEN_YEAR_MONTH_DAY_NO_HYPHEN);
    }

    public static String getNextId(String suffixStr) {
        return StringUtils.isBlank(suffixStr) ? String.format("%05d", Long.parseLong("1")) : String.format("%05d", Long.parseLong(suffixStr));
    }

    public static String encodeAeskey() {
        //使用AES(256)加解密,有些jvm版本需更換jre/lib/security裏的local_policy.jar和US_export_policy.jar(PS.因舊美國政策禁止輸出256加密)
        String encodeStr = null;
        try {
            SecureRandom random = new SecureRandom();
            //random.setSeed("test1".getBytes()); //本案不需規範
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(256, random);
            SecretKey secretKey = generator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded(); //返回基本編碼格式的金鑰
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES"); //根據給定的位元組陣列構造一個金鑰
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_AES_ALGORITHM, getBouncyCastleProvider()); //不能用 new sun.security.provider.Sun()
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encodeStr = Base64.getEncoder().encodeToString(key.getEncoded()); //每次執行產生的Key不一樣
            //LogService.debug("GeoStringUtil encodeAeskey key: "+ encodeStr);
            enCodeFormat = null;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            LOGGER.error("GeoStringUtil encodeAeskey exception: " + e.toString());
        }
        return encodeStr;
    } //encodeAeskey

    public static String aesEncrypt(String originStr, String aesKeyStr, IvParameterSpec iv) {
        //同一個AES Key加密同一字串,加密後的字串也會相同
        String encryptStr = null;
        try {
            SecretKey originalKey = decodeAes(aesKeyStr);
            byte[] enCodeFormat = originalKey.getEncoded(); //返回基本編碼格式的金鑰
            SecretKeySpec skeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_AES_ALGORITHM, getBouncyCastleProvider());

            if (iv == null) {
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            } //if (iv==null)
            encryptStr = Base64.getEncoder().encodeToString(cipher.doFinal(originStr.getBytes("UTF-8")));
            enCodeFormat = null;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            LOGGER.error("GeoStringUtil aesEncrypt exception: " + e.toString());
        }
        return encryptStr;
    } //aesEncrypt

    public static String aesDecript(String encriptStr, String aesKeyStr, IvParameterSpec iv) {
        String decryptStr = null;
        try {
            SecretKey originalKey = decodeAes(aesKeyStr);
            byte[] enCodeFormat = originalKey.getEncoded(); //返回基本編碼格式的金鑰
            SecretKeySpec skeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_AES_ALGORITHM, getBouncyCastleProvider());

            if (iv == null) {
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            } //if (iv==null)
            decryptStr = new String(cipher.doFinal(Base64.getDecoder().decode(encriptStr)));
            enCodeFormat = null;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            LOGGER.error("GeoStringUtil aesDecript exception: " + e.toString());
        }
        return decryptStr;
    } //aesDecript

    public static String generateIvStr() {
        return (UUID.randomUUID().toString()).replaceAll("-", "").substring(0, 15);
    }

    public static String encodeIvStrToBase64(String ivStr) {
        //使用16位字串來產生向量值
        int length = ivStr.length();
        if (length > 16) {
            ivStr = ivStr.substring(0, 16);
        } else if (length < 16) {
            while (ivStr.length() < 16) ivStr = ivStr + "0";
        }
        byte[] encodeByteArray = Base64.getEncoder().encode(ivStr.getBytes());
        return new String(encodeByteArray);
    }

    public static IvParameterSpec generateIv(String ivStr) {
        //使用16位字串來產生向量值
        int length = ivStr.length();
        if (length > 16) {
            ivStr = ivStr.substring(0, 16);
        } else if (length < 16) {
            while (ivStr.length() < 16) ivStr = ivStr + "0";
        }
        LOGGER.debug("SecurityUtil generateIv iv: " + ivStr);
        return new IvParameterSpec(ivStr.getBytes());
    } //generateIv

    private static SecretKey decodeAes(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey.getBytes());
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        //LogService.debug("GeoStringUtil decodeAes key: "+Base64.getEncoder().encodeToString(originalKey.getEncoded()));
        decodedKey = null;
        return originalKey;
    } //decodeAes

    private static BouncyCastleProvider getBouncyCastleProvider() {
        if (bouncyCastleProvider == null) bouncyCastleProvider = new BouncyCastleProvider();
        return bouncyCastleProvider;
    } //getBouncyCastleProvider

    public static String urlEncode(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8");
    }

}
