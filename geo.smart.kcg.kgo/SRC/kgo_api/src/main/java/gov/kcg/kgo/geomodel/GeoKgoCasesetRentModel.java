package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
@ApiModel(description = "前台-場地活動服務案件 model")
public class GeoKgoCasesetRentModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="案件ID")
    private String casesetId;
    @ApiModelProperty(value="案件名稱")
    private String caseName;
    @ApiModelProperty(value="機關名稱")
    private String organName;
    @ApiModelProperty(value="機關id")
    private String organId;
    @ApiModelProperty(value="服務類型")
    private String casesetCategory;


    public String getCasesetId() {
        return casesetId;
    }

    public void setCasesetId(String casesetId) {
        this.casesetId = casesetId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getCasesetCategory() {
        return casesetCategory;
    }

    public void setCasesetCategory(String casesetCategory) {
        this.casesetCategory = casesetCategory;
    }
}
