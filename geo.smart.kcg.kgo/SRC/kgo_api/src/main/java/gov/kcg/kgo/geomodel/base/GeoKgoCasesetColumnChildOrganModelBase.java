package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoCasesetColumnChildOrgan; 

/** 
 * GEO 20211109 add
 * Model for 機關審核表單 服務設定子欄位
 */
@Component
@ApiModel(description = "機關審核表單 服務設定子欄位")
public class GeoKgoCasesetColumnChildOrganModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "複合欄位ID")
    private String CColumnId; 

    @ApiModelProperty(notes = "案件種類ID")
    private String CaseSetId; 

    @ApiModelProperty(notes = "版本號")
    private Integer Version; 

    @ApiModelProperty(notes = "欄位ID")
    private String ColumnId; 

    @ApiModelProperty(notes = "欄位種類")
    private String ColumnType; 

    @ApiModelProperty(notes = "欄位值")
    private String ColumnValue; 

    @ApiModelProperty(notes = "欄位長度")
    private Integer Length; 

    @ApiModelProperty(notes = "欄位備註")
    private String Memo; 

    @ApiModelProperty(notes = "父欄位")
    private String PColumnId; 

    @ApiModelProperty(notes = "前文字")
    private String FText; 

    @ApiModelProperty(notes = "後文字")
    private String BText; 

    @ApiModelProperty(notes = "群組編號")
    private String VGroup; 

    @ApiModelProperty(notes = "顯示順序")
    private Integer OrderNum; 

    @ApiModelProperty(notes = "所在行數")
    private Integer Row; 

    @ApiModelProperty(notes = "是否重複檢核0-否，1-是")
    private Integer IsCheckFrequency; 

    @ApiModelProperty(notes = " 欄位勾選0-否，1-是")
    private Integer IsFieldCheck; 


    public String getCColumnId() { 
        return CColumnId; 
    } 

    public void setCColumnId(String CColumnId) { 
        this.CColumnId = CColumnId; 
    } 

    public String getCaseSetId() { 
        return CaseSetId; 
    } 

    public void setCaseSetId(String CaseSetId) { 
        this.CaseSetId = CaseSetId; 
    } 

    public Integer getVersion() { 
        return Version; 
    } 

    public void setVersion(Integer Version) { 
        this.Version = Version; 
    } 

    public String getColumnId() { 
        return ColumnId; 
    } 

    public void setColumnId(String ColumnId) { 
        this.ColumnId = ColumnId; 
    } 

    public String getColumnType() { 
        return ColumnType; 
    } 

    public void setColumnType(String ColumnType) { 
        this.ColumnType = ColumnType; 
    } 

    public String getColumnValue() { 
        return ColumnValue; 
    } 

    public void setColumnValue(String ColumnValue) { 
        this.ColumnValue = ColumnValue; 
    } 

    public Integer getLength() { 
        return Length; 
    } 

    public void setLength(Integer Length) { 
        this.Length = Length; 
    } 

    public String getMemo() { 
        return Memo; 
    } 

    public void setMemo(String Memo) { 
        this.Memo = Memo; 
    } 

    public String getPColumnId() { 
        return PColumnId; 
    } 

    public void setPColumnId(String PColumnId) { 
        this.PColumnId = PColumnId; 
    } 

    public String getFText() { 
        return FText; 
    } 

    public void setFText(String FText) { 
        this.FText = FText; 
    } 

    public String getBText() { 
        return BText; 
    } 

    public void setBText(String BText) { 
        this.BText = BText; 
    } 

    public String getVGroup() { 
        return VGroup; 
    } 

    public void setVGroup(String VGroup) { 
        this.VGroup = VGroup; 
    } 

    public Integer getOrderNum() { 
        return OrderNum; 
    } 

    public void setOrderNum(Integer OrderNum) { 
        this.OrderNum = OrderNum; 
    } 

    public Integer getRow() { 
        return Row; 
    } 

    public void setRow(Integer Row) { 
        this.Row = Row; 
    } 

    public Integer getIsCheckFrequency() { 
        return IsCheckFrequency; 
    } 

    public void setIsCheckFrequency(Integer IsCheckFrequency) { 
        this.IsCheckFrequency = IsCheckFrequency; 
    } 

    public Integer getIsFieldCheck() { 
        return IsFieldCheck; 
    } 

    public void setIsFieldCheck(Integer IsFieldCheck) { 
        this.IsFieldCheck = IsFieldCheck; 
    } 



    public static GeoKgoCasesetColumnChildOrganModelBase changeToModel(GeoKgoCasesetColumnChildOrgan entity) { 
        GeoKgoCasesetColumnChildOrganModelBase model = new GeoKgoCasesetColumnChildOrganModelBase();
        model.setColumnType(entity.getColumnType()); 
        model.setColumnValue(entity.getColumnValue()); 
        model.setLength(entity.getLength()); 
        model.setMemo(entity.getMemo()); 
        model.setPColumnId(entity.getPColumnId()); 
        model.setFText(entity.getFText()); 
        model.setBText(entity.getBText()); 
        model.setVGroup(entity.getVGroup()); 
        model.setOrderNum(entity.getOrderNum()); 
        model.setRow(entity.getRow()); 
        model.setIsCheckFrequency(entity.getIsCheckFrequency()); 
        model.setIsFieldCheck(entity.getIsFieldCheck()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoCasesetColumnChildOrganModelBase> changeListToModel(List<GeoKgoCasesetColumnChildOrgan> entityList) { 
        List<GeoKgoCasesetColumnChildOrganModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoCasesetColumnChildOrganModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
