package gov.kcg.kgo.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import gov.kcg.kgo.util.MessageUtil;

/**
 * kgo 前台台共通service.
 */
public abstract class KgoFrontEndServiceImpl extends KgoBaseServiceImpl {
	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoFrontEndServiceImpl.class);

	@Autowired
	protected HttpServletRequest request;
	
	@Autowired
	protected MessageUtil messageUtil;
    
}
