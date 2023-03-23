package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoCasesetColumnOrgan; 

/** 
 * GEO 20211109 add
 * Model for 機關審核表單 服務設定欄位
 */
@Component
@ApiModel(description = "機關審核表單 服務設定欄位")
public class GeoKgoCasesetColumnOrganModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "案件種類ID")
    private String CaseSetId; 

    @ApiModelProperty(notes = "群組序號")
    private Integer GroupSeq; 

    @ApiModelProperty(notes = "版本號")
    private Integer Version; 

    @ApiModelProperty(notes = "欄位ID")
    private String ColumnId; 

    @ApiModelProperty(notes = "欄位名稱")
    private String ColumnName; 

    @ApiModelProperty(notes = "欄位型態")
    private String ColumnType; 

    @ApiModelProperty(notes = "欄位設定值")
    private String ColumnValue; 

    @ApiModelProperty(notes = "顯示順序")
    private Integer OrderNum; 

    @ApiModelProperty(notes = "欄位長度")
    private Integer Length; 

    @ApiModelProperty(notes = "欄位備註")
    private String Memo; 

    @ApiModelProperty(notes = "附件上傳檔案限制副檔名")
    private String FileType; 

    @ApiModelProperty(notes = "是否重複檢核0-否，1-是")
    private Integer IsCheckFrequency; 

    @ApiModelProperty(notes = " 欄位勾選0-否，1-是")
    private Integer IsFieldCheck; 


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

    public String getColumnId() { 
        return ColumnId; 
    } 

    public void setColumnId(String ColumnId) { 
        this.ColumnId = ColumnId; 
    } 

    public String getColumnName() { 
        return ColumnName; 
    } 

    public void setColumnName(String ColumnName) { 
        this.ColumnName = ColumnName; 
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

    public Integer getOrderNum() { 
        return OrderNum; 
    } 

    public void setOrderNum(Integer OrderNum) { 
        this.OrderNum = OrderNum; 
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

    public String getFileType() { 
        return FileType; 
    } 

    public void setFileType(String FileType) { 
        this.FileType = FileType; 
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



    public static GeoKgoCasesetColumnOrganModelBase changeToModel(GeoKgoCasesetColumnOrgan entity) { 
        GeoKgoCasesetColumnOrganModelBase model = new GeoKgoCasesetColumnOrganModelBase();
        model.setGroupSeq(entity.getGroupSeq());
        model.setColumnName(entity.getColumnName()); 
        model.setColumnType(entity.getColumnType()); 
        model.setColumnValue(entity.getColumnValue()); 
        model.setOrderNum(entity.getOrderNum()); 
        model.setLength(entity.getLength()); 
        model.setMemo(entity.getMemo()); 
        model.setFileType(entity.getFileType()); 
        model.setIsCheckFrequency(entity.getIsCheckFrequency()); 
        model.setIsFieldCheck(entity.getIsFieldCheck()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoCasesetColumnOrganModelBase> changeListToModel(List<GeoKgoCasesetColumnOrgan> entityList) { 
        List<GeoKgoCasesetColumnOrganModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoCasesetColumnOrganModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
