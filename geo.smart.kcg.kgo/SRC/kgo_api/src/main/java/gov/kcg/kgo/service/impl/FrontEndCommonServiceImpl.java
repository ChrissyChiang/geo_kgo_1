package gov.kcg.kgo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.service.FrontEndCommonService;

/**
 * 
 * Front End Common Service
 * 
 * @author TPIuser
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service("FrontEndCommonService")
public class FrontEndCommonServiceImpl extends KgoFrontEndServiceImpl implements FrontEndCommonService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FrontEndCommonServiceImpl.class);

}
