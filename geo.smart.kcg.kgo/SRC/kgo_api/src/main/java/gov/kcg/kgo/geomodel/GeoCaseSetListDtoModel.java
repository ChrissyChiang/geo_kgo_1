package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.model.KgoCasesetCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20220711 add for 智能客服
 * 智能客服B流程案件服務清單 model
 */
@ApiModel(description = "智能客服案件服務清單 model")
public class GeoCaseSetListDtoModel {

    /** 案件編號 */
    @ApiModelProperty(notes = "案件編號")
    private String caseSetId;

    /** 案件名稱 */
    @ApiModelProperty(notes = "案件名稱")
    private String caseSetName;

    /** 標題 */
    @ApiModelProperty(notes = "標題")
    private String title;

    /** 內容 */
    @ApiModelProperty(notes = "內容")
    private String contentHtml;

    @ApiModelProperty(notes = "機關分類")
    private String organId;

    @ApiModelProperty(notes = "機關分類名稱")
    private String organName;

    @ApiModelProperty(notes = "是否啟用[申辦類型-線上(E)]")
    private Boolean isApplyTypeEActive;

    @ApiModelProperty(notes = "是否啟用[申辦類型-臨櫃(C)]")
    private Boolean isApplyTypeCActive;

    @ApiModelProperty(notes = "是否啟用[申辦類型-書表(L)]")
    private Boolean isApplyTypeLActive;

    @ApiModelProperty(notes = "該案件是否有向國發會申請MyData")
    private Boolean isMyDataTypeActive;

    @ApiModelProperty(notes = "驗證類別")
    private List<String> checkApplyUserTypes;

    @ApiModelProperty(notes = "同意區塊說明頁")
    private String serviceHtml;

    @ApiModelProperty(notes = "案件服務站外連結")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getMyDataTypeActive() {
        return isMyDataTypeActive;
    }

    public void setMyDataTypeActive(Boolean myDataTypeActive) {
        isMyDataTypeActive = myDataTypeActive;
    }

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getCaseSetName() {
        return caseSetName;
    }

    public void setCaseSetName(String caseSetName) {
        this.caseSetName = caseSetName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public Boolean getApplyTypeEActive() {
        return isApplyTypeEActive;
    }

    public void setApplyTypeEActive(Boolean applyTypeEActive) {
        isApplyTypeEActive = applyTypeEActive;
    }

    public Boolean getApplyTypeCActive() {
        return isApplyTypeCActive;
    }

    public void setApplyTypeCActive(Boolean applyTypeCActive) {
        isApplyTypeCActive = applyTypeCActive;
    }

    public Boolean getApplyTypeLActive() {
        return isApplyTypeLActive;
    }

    public void setApplyTypeLActive(Boolean applyTypeLActive) {
        isApplyTypeLActive = applyTypeLActive;
    }

    public List<String> getCheckApplyUserTypes() {
        return checkApplyUserTypes;
    }

    public void setCheckApplyUserTypes(List<String> checkApplyUserTypes) {
        this.checkApplyUserTypes = checkApplyUserTypes;
    }

    public String getServiceHtml() {
        return serviceHtml;
    }

    public void setServiceHtml(String serviceHtml) {
        this.serviceHtml = serviceHtml;
    }
}
