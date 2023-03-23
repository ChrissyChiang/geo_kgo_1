package gov.kcg.kgo.controller.mydata;

import gov.kcg.kgo.service.MyDataService;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rq.SpApplyRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyDataCallbackController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyDataCallbackController.class);

    @Autowired
    private MyDataService myDataService;

    /**
     * MyData callback api
     *
     * @param spApplyRq
     */
    @PostMapping(value = "/sp/apply",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void spApply(@RequestBody SpApplyRq spApplyRq) {
        LOGGER.info("MyDataCallbackController spApply...");
        myDataService.myDataNotifySp(spApplyRq);
    }

}
