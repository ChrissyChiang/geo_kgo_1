package gov.kcg.kgo.controller.backend;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.viewModel.testApi.homeAction.rq.TestHomeActionRq;
import gov.kcg.kgo.viewModel.testApi.homeAction.rs.TestHomeActionRs;
import gov.kcg.kgo.viewModel.testApi.homeAction.rs.bean.TestHomeActionRsViewForm;
import gov.kcg.kgo.viewModel.testApi.homeAction.rs.bean.UserBean;

/**
 * controller 範例.
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    /**
     * XX功能首頁.
     *
     * @param rq the rq
     * @param request the request
     * @return the string
     */
    @RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, 
    		consumes = {MediaType.APPLICATION_JSON_VALUE },
    		produces = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
	public TestHomeActionRs homeAction(@RequestBody TestHomeActionRq rq, HttpServletRequest request) {
    	// 取得登入者資訊.
    	TestHomeActionRs rs = new TestHomeActionRs();
    	List<UserBean> userList = new ArrayList<>();
    	userList.add(new UserBean());
    	TestHomeActionRsViewForm viewForm = new TestHomeActionRsViewForm();
    	viewForm.setUserList(userList);
    	rs.setData(viewForm);
    	return rs;
	}

	private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private TaskService taskService;

	@RequestMapping("/test2/{userId}")
	public void test2(@PathVariable("userId") String userId) {
		List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(userId).list();
		taskList.forEach(task -> {
			LOGGER.info(">>>>> task info={} {} {}",task.toString(), task.getName(), task.getProcessInstanceId());
			LOGGER.info(">>>>> task info={} {} {}",task.getId(), task.getName(), task.getProcessInstanceId());
		});
	}
}
