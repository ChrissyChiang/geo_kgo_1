package gov.kcg.kgo.controller.mydata;

import gov.kcg.kgo.service.MyDataService;
import gov.kcg.kgo.service.impl.CaseFormServiceImpl;
import gov.kcg.kgo.viewModel.mydata.bo.ClientInfoBO;
import gov.kcg.kgo.viewModel.mydata.bo.UserInfoBO;
import gov.kcg.kgo.viewModel.mydata.vo.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myDataService")
public class MyDataServiceController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MyDataServiceController.class);

    @Autowired
    private MyDataService myDataService;

    /**
     * MyData mode one
     *
     * @param
     */
    @PostMapping(value = "/doMyDataModeOne",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public MyDataServiceModeOneRs doMyDataModeOne(@RequestBody MyDataServiceModeOneRq req) {
        UserInfoBO userInfoBO = new UserInfoBO();
        userInfoBO.setPid(req.getPersonalId());

        ClientInfoBO clientInfoBO = new ClientInfoBO(
                req.getClientId(),
                req.getClientIv(),
                req.getClientSecretKey(),
                req.getResourceList());

        String redirectMyDataUrl = myDataService.getRedirectMyDataUrl(req.getTxId(), req.getSpReturnUrl(), userInfoBO, clientInfoBO);

        MyDataServiceModeOneRs res = new MyDataServiceModeOneRs(req);
        res.setRedirectMyDataUrl(redirectMyDataUrl);
        return res;
    }

    /**
     * Get permissionTicket
     *
     * @param
     */
    @PostMapping(value = "/getPermissionTicket",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public MyDataServicePermissionTicketRs getPermissionTicket(@RequestBody MyDataServicePermissionTicketRq req) {
        return myDataService.getPermissionTicketAndSecretKey(req);
    }

    /**
     * Download MyData data
     */
    @PostMapping(value = "/downloadMyData",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public MyDataServiceDownloadRs downloadMyData(@RequestBody MyDataServiceDownloadRq req) {
        //LOGGER.info("downloadMyData start");
        return myDataService.downloadMyData(req.getTxId(), req.getClientId(),req.getClientIv() , req.getClientSecretKey(), req.getPermissionTicket(), req.getSecretKey());
    }

}
