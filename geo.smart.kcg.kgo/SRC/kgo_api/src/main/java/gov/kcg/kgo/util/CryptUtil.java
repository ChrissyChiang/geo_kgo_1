package gov.kcg.kgo.util;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 加解密工具.
 */
public class CryptUtil {
	private static final String SECRET_KEY = "cathay360";

	private static final String ENCRYPT_NAME_SHA256 = "SHA-256";

	/**
	 * Base64 轉成圖檔object.
	 *
	 * @param imgBase64Str the img base 64 str
	 * @return the image
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Image base64ImgToImgFile(String imgBase64Str) throws IOException {
		Image img = null;
		if (StringUtils.isNotBlank(imgBase64Str)) {
			String base64Image = imgBase64Str.split(",")[1];
			byte[] bytes = Base64.getDecoder().decode(base64Image);
			img = ImageIO.read(new ByteArrayInputStream(bytes));
		}
		return img;
	}

	/**
	 * 檔案轉成 Base64 String
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String encodeFileToBase64Binary(MultipartFile file) throws IOException {
		String encodedBase64 = null;
		byte[] bytes = file.getBytes();
		encodedBase64 = Base64.getEncoder().encodeToString(bytes);
		return encodedBase64;
	}

	/**
	 * AES 256 加密.
	 *
	 * @param strToEncrypt the str to encrypt
	 * @return the string
	 */
	public static String AES256Encrypt(String strToEncrypt) {
		try {
			byte[] salt = initSalt();
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), salt, 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	/**
	 * AES 256 解密.
	 *
	 * @param strToDecrypt the str to decrypt
	 * @return the string
	 */
	public static String AES256Decrypt(String strToDecrypt) {
		try {
			byte[] salt = initSalt();
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), salt, 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	/**
	 * SHA-256 加密
	 * 
	 * @param strText
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String SHA256Encrypt(String strText) {
		String strResult = StringUtils.EMPTY;
		try {
			if (StringUtils.isNotBlank(strText)) {
				MessageDigest messageDigest;
				messageDigest = MessageDigest.getInstance(ENCRYPT_NAME_SHA256);// SHA-256 加密
				messageDigest.update(strText.getBytes()); // 帶入欲加密的字串
				byte byteBuffer[] = messageDigest.digest(); // 取得byte 類型结果

				/**
				 * 將byte轉為String
				 */
				StringBuffer strHexString = new StringBuffer();
				for (int i = 0; i < byteBuffer.length; i++) {
					String hex = Integer.toHexString(0xff & byteBuffer[i]);
					if (hex.length() == 1) {
						strHexString.append('0');
					}
					strHexString.append(hex);
				}
				strResult = strHexString.toString();
			}
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error while SHA-256 encrypting: " + e.toString());
		}
		return strResult;
	}

	public static String SHA256Encrypt2(String strText) {
		String strResult = StringUtils.EMPTY;
		try {
			if (StringUtils.isNotBlank(strText)) {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				digest.reset();
				digest.update(strText.getBytes("utf8"));
				strResult = String.format("%064x", new BigInteger(1, digest.digest()));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error while SHA-256 encrypting: " + e.toString());
			e.printStackTrace();
		}

		return strResult;

	}

	private static byte[] initSalt() throws Exception {
		// byte[] salt = new byte[8];
		byte[] salt = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		// Random random = new Random();
		// random.nextBytes(salt);
		return salt;
	}
}
