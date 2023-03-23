package gov.kcg.kgo.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import gov.kcg.kgo.util.MessageUtil;

/**
 * 高雄市政府 - 一次GO base controller.
 */
@Controller
public class BaseController {

	@Autowired
	private MessageUtil messageUtil;

}
