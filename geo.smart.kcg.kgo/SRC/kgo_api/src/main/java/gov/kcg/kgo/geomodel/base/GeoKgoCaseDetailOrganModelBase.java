package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoCaseDetailOrgan; 

/** 
 * GEO 20211109 add
 * Model for 機關審核表單 案件資料檔
 */
@Component
@ApiModel(description = "機關審核表單 案件資料檔")
public class GeoKgoCaseDetailOrganModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "案件ID")
    private String CaseId; 

    @ApiModelProperty(notes = "欄位ID")
    private String ColumnId; 

    @ApiModelProperty(notes = "服務版本號")
    private Integer Version; 

    @ApiModelProperty(notes = "欄位設定值")
    private String ColumnValue; 

    @ApiModelProperty(notes = "補正說明")
    private String CorrectMemo; 

    @ApiModelProperty(notes = "複合欄位ID")
    private String CColumnId; 

    @ApiModelProperty(notes = "補正值")
    private String CorrectBValue; 

    public String getCaseId() { 
        return CaseId; 
    } 

    public void setCaseId(String CaseId) { 
        this.CaseId = CaseId; 
    } 

    public String getColumnId() { 
        return ColumnId; 
    } 

    public void setColumnId(String ColumnId) { 
        this.ColumnId = ColumnId; 
    } 

    public Integer getVersion() { 
        return Version; 
    } 

    public void setVersion(Integer Version) { 
        this.Version = Version; 
    } 

    public String getColumnValue() { 
        return ColumnValue; 
    } 

    public void setColumnValue(String ColumnValue) { 
        this.ColumnValue = ColumnValue; 
    } 

    public String getCorrectMemo() { 
        return CorrectMemo; 
    } 

    public void setCorrectMemo(String CorrectMemo) { 
        this.CorrectMemo = CorrectMemo; 
    } 

    public String getCColumnId() { 
        return CColumnId; 
    } 

    public void setCColumnId(String CColumnId) { 
        this.CColumnId = CColumnId; 
    } 

    public String getCorrectBValue() { 
        return CorrectBValue; 
    } 

    public void setCorrectBValue(String CorrectBValue) { 
        this.CorrectBValue = CorrectBValue; 
    } 

    public static GeoKgoCaseDetailOrganModelBase changeToModel(GeoKgoCaseDetailOrgan entity) { 
        GeoKgoCaseDetailOrganModelBase model = new GeoKgoCaseDetailOrganModelBase();
        model.setColumnValue(entity.getColumnValue()); 
        model.setCorrectMemo(entity.getCorrectMemo());
        model.setCorrectBValue(entity.getCorrectBValue());
        return model; 
    } //changeToModel 

    public static List<GeoKgoCaseDetailOrganModelBase> changeListToModel(List<GeoKgoCaseDetailOrgan> entityList) { 
        List<GeoKgoCaseDetailOrganModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoCaseDetailOrganModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
