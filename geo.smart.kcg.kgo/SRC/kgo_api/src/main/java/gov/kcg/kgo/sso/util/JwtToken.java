package gov.kcg.kgo.sso.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.config.Constants;
import gov.kcg.kgo.enums.common.JwtParseType;

public class JwtToken {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtToken.class);

	private static JwtToken instance;
	
	/** JWT 分割符號 **/
	public final static String JWT_SPILIT_SYMBOL = "\\.";

	private JwtToken() {
		// 這裡面跑很了多code，建立物件需要花費很多資源
	}

	/**
	 * 多執行緒時，當物件需要被建立時才使用synchronized保證Singleton一定是單一的 ，增加程式校能
	 * 
	 * @return
	 */
	public static JwtToken getInstance() {
		if (instance == null) {
				if (instance == null) {
					instance = new JwtToken();
				}
		}
		return instance;
	}

	/**
	 * parse jwt
	 * 
	 * @param parseType
	 * @param jwtToken
	 * @return
	 */
	public String parseJwt(String jwtToken, JwtParseType parseType) {
		String[] splitString = jwtToken.split(JWT_SPILIT_SYMBOL);
		String result = "";
		Base64 base64Url = new Base64(true);

		switch (parseType) {
		case HEADER:
			result = new String(base64Url.decode(splitString[0]));
			LOGGER.info("JWT Header : ");
			break;
		case BODY:
			result = new String(base64Url.decode(splitString[1]));
			LOGGER.info("JWT Body : ");
			break;
		case ALL:
			int size = splitString.length;
			for (int i = 0; i < size; i++) {
				LOGGER.info("JWT [{}]\n", i);
				String jwtDecode = new String(base64Url.decode(splitString[i]));
				LOGGER.info("JWT Decode : " + jwtDecode);
			}
		default:
		}
		LOGGER.info(result);
		return result;
	}
	
	
	
	public static void main(String[] args) {
		JwtToken.getInstance().parseJwt("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhY2NvdW50cy5rY2cuZ292LnR3IiwiaWF0IjoxNjAxMDg5ODg5LCJleHAiOjE2MDEwOTE2ODksIktDR1aaT09HTEVfVVNFUl9TU09fVE9LRU4iOiIzM2Q5OGIzNy1hYTk5LTRkZTItYTIwYS0wYWJjZjZjYzg3NjQifQ.LYHwiksH45lsh7aeH0hBEEfkFf46Eg1l_qObI752op0#", JwtParseType.BODY);
	}
}
