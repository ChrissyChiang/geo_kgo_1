package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoCasesetGroupOrgan; 

/** 
 * GEO 20211109 add
 * Model for 機關審核表單 案件設定群組
 */
@Component
@ApiModel(description = "機關審核表單 案件設定群組")
public class GeoKgoCasesetGroupOrganModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "案件種類ID")
    private String CaseSetId; 

    @ApiModelProperty(notes = "群組序號")
    private Integer GroupSeq; 

    @ApiModelProperty(notes = "版本號")
    private Integer Version; 

    @ApiModelProperty(notes = "備註")
    private String Memo; 

    @ApiModelProperty(notes = "顯示順序")
    private Integer OrderNum; 

    @ApiModelProperty(notes = "是否顯示")
    private Integer IsShow; 

    @ApiModelProperty(notes = "重複檢核時間")
    private String CheckFrequencyPeriod; 


    public String getCaseSetId() { 
        return CaseSetId; 
    } 

    public void setCaseSetId(String CaseSetId) { 
        this.CaseSetId = CaseSetId; 
    } 

    public Integer getGroupSeq() { 
        return GroupSeq; 
    } 

    public void setGroupSeq(Integer GroupSeq) { 
        this.GroupSeq = GroupSeq; 
    } 

    public Integer getVersion() { 
        return Version; 
    } 

    public void setVersion(Integer Version) { 
        this.Version = Version; 
    } 

    public String getMemo() { 
        return Memo; 
    } 

    public void setMemo(String Memo) { 
        this.Memo = Memo; 
    } 

    public Integer getOrderNum() { 
        return OrderNum; 
    } 

    public void setOrderNum(Integer OrderNum) { 
        this.OrderNum = OrderNum; 
    } 

    public Integer getIsShow() { 
        return IsShow; 
    } 

    public void setIsShow(Integer IsShow) { 
        this.IsShow = IsShow; 
    } 

    public String getCheckFrequencyPeriod() { 
        return CheckFrequencyPeriod; 
    } 

    public void setCheckFrequencyPeriod(String CheckFrequencyPeriod) { 
        this.CheckFrequencyPeriod = CheckFrequencyPeriod; 
    } 



    public static GeoKgoCasesetGroupOrganModelBase changeToModel(GeoKgoCasesetGroupOrgan entity) { 
        GeoKgoCasesetGroupOrganModelBase model = new GeoKgoCasesetGroupOrganModelBase();
        model.setMemo(entity.getMemo()); 
        model.setOrderNum(entity.getOrderNum()); 
        model.setIsShow(entity.getIsShow()); 
        model.setCheckFrequencyPeriod(entity.getCheckFrequencyPeriod()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoCasesetGroupOrganModelBase> changeListToModel(List<GeoKgoCasesetGroupOrgan> entityList) { 
        List<GeoKgoCasesetGroupOrganModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoCasesetGroupOrganModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
