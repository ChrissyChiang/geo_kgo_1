package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.mydata.vo.ec.rq.GetServiceDataRq;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rq.GetSpSignatureParameterRq;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rq.VerifySpIdRq;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rs.GetServiceDataRs;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rs.GetSpSignatureParameterRs;
import gov.kcg.kgo.viewModel.mydata.vo.ec.rs.VerifySpIdRs;

public interface CallMyDataService {

    /**
     * 取得導向MyData的Url
     *
     * @param getSpSignatureParameterRq
     * @return
     */
    GetSpSignatureParameterRs getSignatureParameterByTxId(GetSpSignatureParameterRq getSpSignatureParameterRq);

    /**
     * 呼叫MyData做身份驗證
     *
     * @param verifySpIdRq
     * @return
     */
    VerifySpIdRs verifyByTxIdAndDataAndPkcs7File(VerifySpIdRq verifySpIdRq);

    /**
     * 呼叫MyData取得處理完成的資料
     *
     * @param getServiceData
     * @return
     */
    GetServiceDataRs getServiceDataByTxIdAndPermissionTicket(GetServiceDataRq getServiceData);

}
