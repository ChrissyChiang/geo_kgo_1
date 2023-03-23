package gov.kcg.kgo.service.bean.excel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gov.kcg.kgo.geomodel.dto.SiteCaseMainQueryViewListDto;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseQueryDataGrid;

import javax.persistence.Column;
import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCaseHandleSiteExcelVo extends GeoCaseHandleExcelVo{

    private String siteName;
    private String startTime;
    private Integer amount;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public static GeoCaseHandleSiteExcelVo chanceDtoDataToVo(CaseHandleCaseQueryDataGrid grid,int order){
        GeoCaseHandleSiteExcelVo vo = new GeoCaseHandleSiteExcelVo();
        vo.setOrder(order);
        vo.setAmount(grid.getAmount());
        vo.setSiteName(grid.getSiteName());
        vo.setStartTime(grid.getStartTime());
        vo.setCaseId(grid.getCaseId());
        vo.setCaseName(grid.getCaseName());
        vo.setCaseOfficer(grid.getOfficer());
        vo.setDeadlineDate(grid.getLimitDate());
        vo.setApplyUser(grid.getApplicant());
        vo.setApplyDate(grid.getApplyDate());
        vo.setStatus(grid.getStatusName());
        vo.setLogin(grid.getApplyUserLoginType());
        return vo;
    }
}
