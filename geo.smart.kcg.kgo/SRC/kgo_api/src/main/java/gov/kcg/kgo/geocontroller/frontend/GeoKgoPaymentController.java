package gov.kcg.kgo.geocontroller.frontend;

import gov.kcg.kgo.geoservice.GeoKcgPaymentService;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoFrontendPaymentRs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(tags = {"前台繳費"})
@RequestMapping(value = "/frontend/payment")
public class GeoKgoPaymentController {

    @Autowired
    private GeoKcgPaymentService geoKcgPaymentService;

    /**
     * 生成繳費物件
     *
     * @param caseId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "前台繳費")
    @RequestMapping(value = "/{caseId}", method = {RequestMethod.GET})
    public GeoFrontendPaymentRs payByCaseId(@PathVariable("caseId") String caseId) throws Exception {
        final String certification = UUID.randomUUID().toString();
        return geoKcgPaymentService.genPaymentModel(caseId, certification);
    }
}
