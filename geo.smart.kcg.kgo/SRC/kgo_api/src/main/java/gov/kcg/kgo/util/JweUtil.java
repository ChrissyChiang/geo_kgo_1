package gov.kcg.kgo.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.AESDecrypter;
import com.nimbusds.jose.crypto.AESEncrypter;
import com.nimbusds.jose.util.Base64URL;

public class JweUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JweUtil.class);

	public static Payload decode(String secretKey, String jweToken) {
		try {
			JWEObject jweObject = JWEObject.parse(jweToken);
			jweObject.decrypt(new AESDecrypter(secretKey.getBytes()));
			return jweObject.getPayload();
		} catch (JOSEException | ParseException ex) {
			LOGGER.error("Decode JWE Error", ex);
			return new Payload("");
		}
	}

	public static String encrypt(String data, String secretKey) {
		JWEHeader jweHeader = new JWEHeader(JWEAlgorithm.A256KW, EncryptionMethod.A256CBC_HS512);
		Payload payload = new Payload(data);
		try {
			JWEObject jweObject = new JWEObject(jweHeader, payload);
			jweObject.encrypt(new AESEncrypter(secretKey.getBytes()));
			return jweObject.serialize();
		} catch (JOSEException ex) {
			LOGGER.error("Encrypt JWE Error", ex);
			return "";
		}
	}

	/**
	 * 檢核JWE Auth Tag 是否相等
	 * 
	 * @param jweToken
	 * @param secretKey
	 * @return
	 */
	public static boolean checkJweAuthTag(String secretKey, String jweToken) {
		//看來只有用在和 mydata 溝通
		LOGGER.info("JweUtil checkJweAuthTag...");
		try {
			JWEObject jweObject = JWEObject.parse(jweToken);
			jweObject.decrypt(new AESDecrypter(secretKey.getBytes()));
			String header = jweObject.getHeader().toBase64URL().toString();
			byte[] authTag = jweObject.getAuthTag().decode();
			byte[] cipherText = jweObject.getCipherText().decode();
			byte[] iv = jweObject.getIV().decode();
			Base64URL eKey = jweObject.getEncryptedKey();

			SecretKeySpec kekSpec = new SecretKeySpec(secretKey.getBytes(), "AES");
			SecretKey cek = (eKey.toString().length() > 0) ? unwrap(kekSpec, eKey.decode()) :
			// 使用 DIRECT 演算法時，不會產生 encryptedCEK 所以使用原訂的 AES key 做解密。
					kekSpec;
			SecretKey[] compositeKeys = compositeKey(cek);
			SecretKey macKey = compositeKeys[0];

			// compute AAD
			byte[] aad = header.getBytes(Charset.forName("ASCII"));
			// AAD length to 8 byte array
			byte[] al = ByteBuffer.allocate(8).putLong((aad.length * 8)).array();

			// Check MAC
			int hmacInputLength = aad.length + iv.length + cipherText.length + al.length;
			byte[] hmacInput = ByteBuffer.allocate(hmacInputLength).put(aad).put(iv).put(cipherText).put(al).array();
			Mac mac = Mac.getInstance(macKey.getAlgorithm());
			mac.init(macKey);
			mac.update(hmacInput);
			byte[] hmac = mac.doFinal();
			byte[] expectedAuthTag = Arrays.copyOf(hmac, macKey.getEncoded().length);
			return Arrays.equals(expectedAuthTag, authTag);
		} catch (Exception e) {
			LOGGER.error("checkJwe AuthTag Error", e);
			return false;
		}
	} //checkJweAuthTag

	private static SecretKey[] compositeKey(SecretKey cek) {
		SecretKey[] compositeKey = new SecretKey[2];

		// HMAC key
		SecretKey macKey = null;
		// AES key
		SecretKey encKey = null;

		if (cek.getEncoded().length == 32) {
			// 128bit HMAC key
			macKey = new SecretKeySpec(cek.getEncoded(), 0, 16, "HMACSHA256");
			// 128bit AES key
			encKey = new SecretKeySpec(cek.getEncoded(), 16, 16, "AES");
		} else if (cek.getEncoded().length == 64) {
			// 256bit MAC key
			macKey = new SecretKeySpec(cek.getEncoded(), 0, 32, "HMACSHA512");
			// 256bit AES key
			encKey = new SecretKeySpec(cek.getEncoded(), 32, 32, "AES");
		} else {
			LOGGER.error("CEK 長度不正確。 使用 AES_128_CBC_HMAC_SHA_256 長度應為 32。 使用 AES_256_CBC_HMAC_SHA_512 長度應為 64");
			return null;
		}

		compositeKey[0] = macKey;
		compositeKey[1] = encKey;

		return compositeKey;
	}

	private static SecretKey unwrap(SecretKey kek, byte[] base64UrlDecodedEncryptedCEK) {
		try {
			Cipher cipher = Cipher.getInstance("AESWrap");
			cipher.init(Cipher.UNWRAP_MODE, kek);
			SecretKey cek = (SecretKey) cipher.unwrap(base64UrlDecodedEncryptedCEK, "AES", Cipher.SECRET_KEY);
			return cek;
		} catch (Exception ex) {
			LOGGER.error("Unwrap cek Error", ex);
		}
		return null;
	}

}
