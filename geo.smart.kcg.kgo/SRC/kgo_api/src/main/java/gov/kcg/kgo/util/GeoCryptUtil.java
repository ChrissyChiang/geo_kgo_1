package gov.kcg.kgo.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * Geo 20210816 add
 *
 * 加解密工具.
 */
public class GeoCryptUtil {

	/**
	 * 檔案轉成 Base64 String
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String encodeFileToBase64Binary(File file) throws IOException {
		String encodedBase64 = null;
		byte[] bytes = FileUtils.readFileToByteArray(file);
		encodedBase64 = Base64.getEncoder().encodeToString(bytes);
		return encodedBase64;
	} //encodeFileToBase64Binary
}
