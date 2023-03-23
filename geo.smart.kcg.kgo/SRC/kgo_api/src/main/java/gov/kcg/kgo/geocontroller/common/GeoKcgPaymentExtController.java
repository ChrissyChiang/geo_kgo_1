package gov.kcg.kgo.geocontroller.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.geomodel.ktcpay.GeoKgoPaymentExtModifyRqModel;
import gov.kcg.kgo.geomodel.ktcpay.GeoKgoPaymentExtQueryRqModel;
import gov.kcg.kgo.geoservice.GeoKcgPaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common/kcgPay")
@Api(tags = {"市民科技外部繳退費api"}, value = "市民科技外部繳退費api")
public class GeoKcgPaymentExtController extends BaseController {
  @Autowired
  private GeoKcgPaymentService geoKcgPaymentService;

  @ApiOperation(tags = {"取得相關預約繳費資訊"}, value = "取得相關預約繳費資訊")
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.OPTIONS})
  public Object listPersonalPayment(@RequestBody GeoKgoPaymentExtQueryRqModel model) {
    return geoKcgPaymentService.getPaymentListByCaseId(model);
  }

  @ApiOperation(tags = {"變更繳退費狀態"}, value = "變更繳退費狀態")
  @RequestMapping(value = "/apprise", method = {RequestMethod.POST, RequestMethod.OPTIONS})
  public Object listPersonalPayment(@RequestBody GeoKgoPaymentExtModifyRqModel model) throws JsonProcessingException {
    return geoKcgPaymentService.savePaymentRecord(model);
  }
}
