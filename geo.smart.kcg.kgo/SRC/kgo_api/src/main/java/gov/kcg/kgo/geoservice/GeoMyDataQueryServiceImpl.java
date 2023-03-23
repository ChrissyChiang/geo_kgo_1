package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.enums.front.FrontendFunctionCodeEnum;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geomodel.GeoKgoMyDataCaseModel;
import gov.kcg.kgo.georepository.custom.GeoMyDataCaseQueryReposCustom;
import gov.kcg.kgo.geoviewmodel.frontend.rq.MyDataQueryRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoMyDataQueryRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoMyDataQueryViewForm;
import gov.kcg.kgo.service.impl.KgoBaseServiceImpl;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.KgoUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeoMyDataQueryServiceImpl extends KgoBaseServiceImpl implements GeoMyDataQueryService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoMyDataQueryServiceImpl.class);
    @Autowired
    GeoMyDataCaseQueryReposCustom geoMyDataCaseQueryReposCustom;

    /**
     * GEO 20221009 add_Jim
     *
     * @param rq
     * @return
     */
    @Override
    public GeoMyDataQueryRs queryMyDataCase(MyDataQueryRq rq) {
        LOGGER.info("queryMyDataCase start");
        GeoMyDataQueryRs rs = new GeoMyDataQueryRs();
        GeoMyDataQueryViewForm viewForm = new GeoMyDataQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        OperationApiMemo memo = null;
        try {
            memo = super.genOperationMemo(SystemTypeEnum.F, null, FrontendFunctionCodeEnum.MyDataCaseSearch);
            String myDataKeyWord = rq.getMyDataKeyWord();
            String myDataCaseNoKeyWord = rq.getMyDataCaseNoKeyWord();
            Integer pageNumber = rq.getPageNumber();
            Integer pageSize = rq.getPageSize();
            FrontendLoginUserInfo loginUserInfo = KgoUtil.getFrontendLoginUser(false);
            List<GeoKgoMyDataCaseModel> caseList = new ArrayList<>();
            List<GeoKgoMyDataCaseModel> caseSubList = new ArrayList<>();
            int totalPage = 0;
                //獲取身分證
                loginUserInfo.setInfoByLoginType();
                String twSSn = loginUserInfo.getTwSSn();
                LOGGER.info("queryMyDataCase twSSn="+twSSn);
                if (twSSn!=null && !twSSn.equals("")){
                    caseList = geoMyDataCaseQueryReposCustom.getMyDataCaseList(twSSn);
                    if (Strings.isNotBlank(myDataKeyWord)) {
                        caseList = caseList.stream()
                                .filter(e -> e.getCaseSet().contains(myDataKeyWord)).collect(Collectors.toList());
                    }
                    if (Strings.isNotEmpty(myDataCaseNoKeyWord)) {
                        caseList = caseList.stream()
                                .filter(e -> e.getCaseId().contains(myDataCaseNoKeyWord)).collect(Collectors.toList());
                    }
                    if (caseList.size() > 0 && pageNumber != null && pageNumber > 0 && pageSize != null && pageSize > 0) {
                        totalPage = (int) Math.ceil((double) caseList.size() / pageSize);
                        pageNumber = pageNumber > totalPage ? totalPage : pageNumber;
                        int offset = (pageNumber - 1) * pageSize;
                        int end = pageSize + offset;
                        end = Math.min(end, caseList.size());
                        offset = offset > end ? Math.max(end - pageSize, 0) : offset;
                        caseSubList = caseList.subList(offset, end);
                    } else {
                        caseSubList = caseList;
                        totalPage = caseSubList.size() > 0 ? 1 : totalPage;
                    }
                } //if (twSSn!=null && !twSSn.equals(""))
            viewForm.setCaseList(caseSubList);
            viewForm.setTotalPage(totalPage);
            viewForm.setTotalSize(caseList.size());
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoFrontEndApiError.FAIL_TO_SEARCH), e);
        } finally {
            /** === 儲存操作紀錄 === */
            String keyWord ="";
            if (!rq.getMyDataCaseNoKeyWord().equals(""))keyWord=rq.getMyDataKeyWord();
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn("MyDataCase搜尋",keyWord));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return rs;
    } //queryMyDataCase
}