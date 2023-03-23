package gov.kcg.kgo.util;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.uuid.Generators;

public class RandomUtil {

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public static String getUUID() {
		return Generators.randomBasedGenerator().generate().toString();
	}

	/**
	 * Gets the random number by digits
	 * 
	 * ex: digits = 4 → 1000~9999 , digits = 6 → 100000~999999
	 * 
	 * @param digits
	 * @return
	 */
	public static int getRandomNumberWithDigits(int digits) {
		int baseNumber = 1;
		for (int i = 0; i < digits; i++) {
			baseNumber *= 10;
		}

		Random rnd = new Random();
		return baseNumber + rnd.nextInt(baseNumber * 9);
	}
	
	/**
	 * 隨機產生四位數字.
	 *
	 * @return the four random
	 */
	public static String getFourRandom() {
		final int radomLength = 4;
		final String digits = "0123456789";
		return RandomStringUtils.random(radomLength, digits);
	}
}
