package gov.kcg.kgo.util;

import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * i18n 對應訊息處裡util.
 */
@Component
public class MessageUtil {

	private final static Logger LOGGER = LoggerFactory.getLogger(MessageUtil.class);

	@Resource
    private MessageSource messageSource;

	private String getMessage(String keyName, Object [] args, Locale locale){

		if(locale == null){
			LOGGER.info("locale is null set default Locale TAIWAN");
			locale = Locale.TAIWAN;
		}
	    String msg = "";
	    try{
	    	msg = messageSource.getMessage(keyName, args, locale);
	    }catch(NoSuchMessageException e){
	    	LOGGER.error("No message found under code '" + keyName + "'");
	    }
	    
	    if(msg == null || msg.trim().equals("")){
	    	LOGGER.error(keyName + " does not exist in properties");
	    }
		return msg;
	}
	
	/**
	 * 取得語系文字(預設為 Locale.TAIWAN).
	 *
	 * @param keyName the key name
	 * @return the message
	 */
	public String getMessage(String keyName){
		Locale locale = LocaleContextHolder.getLocale();
		return getMessage(keyName, null, locale);
	}
	
	/**
	 * 取得語系文字(預設為 Locale.TAIWAN) 傳參數.
	 *
	 * @param keyName the key name
	 * @return the message
	 */
	public String getMessage(String keyName, Object [] args){
		Locale locale = LocaleContextHolder.getLocale();
		return getMessage(keyName, args, locale);
	}
	
	/**
	 * 取得當前設定的語系.
	 *
	 * @param keyName the key name
	 * @return the current locale message
	 */
	public String getCurrentLocaleMessage(String keyName){
		// Get current locale
		Locale locale = LocaleContextHolder.getLocale();
		return getMessage(keyName, null, locale);
	}
}
