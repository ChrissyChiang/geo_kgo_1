package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoAgent;
import gov.kcg.kgo.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/** 
 * GEO 20211026 add
 * Model for 代理人資訊
 */
@Component
@ApiModel(description = "代理人資訊")
public class GeoKgoAgentModel implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "代理人機制id")
    private String agentId; 

    @ApiModelProperty(notes = "被代理人id")
    private String principalUserId; 

    @ApiModelProperty(notes = "代理人id")
    private String agentUserId;

    @ApiModelProperty(notes = "代理起始時間")
    private String startTime;

    @ApiModelProperty(notes = "代理結束時間")
    private String endTime;

    @ApiModelProperty(notes = "是否已簽核")
    private Integer isSigned; 

    @ApiModelProperty(notes = "是否已刪除")
    private Integer isDelete;

    public String getAgentId() { 
        return agentId; 
    } 

    public void setAgentId(String agentId) { 
        this.agentId = agentId; 
    } 

    public String getPrincipalUserId() { 
        return principalUserId; 
    } 

    public void setPrincipalUserId(String principalUserId) { 
        this.principalUserId = principalUserId; 
    } 

    public String getAgentUserId() { 
        return agentUserId; 
    } 

    public void setAgentUserId(String agentUserId) { 
        this.agentUserId = agentUserId; 
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getIsSigned() {
        return isSigned; 
    } 

    public void setIsSigned(Integer isSigned) { 
        this.isSigned = isSigned; 
    } 

    public Integer getIsDelete() { 
        return isDelete; 
    } 

    public void setIsDelete(Integer isDelete) { 
        this.isDelete = isDelete; 
    }

    public static GeoKgoAgentModel changeToModel(GeoKgoAgent entity) {
        GeoKgoAgentModel model = new GeoKgoAgentModel();
        model.setAgentId(entity.getAgentId());
        model.setPrincipalUserId(entity.getPrincipalUserId());
        model.setAgentUserId(entity.getAgentUserId());
        model.setStartTime(DateUtil.timestampToString(entity.getStartTime(),DateUtil.PATTEN_YEAR_MONTH_DAY));
        model.setEndTime(DateUtil.timestampToString(entity.getEndTime(),DateUtil.PATTEN_YEAR_MONTH_DAY));
        model.setIsSigned(entity.getIsSigned());
        model.setIsDelete(entity.getIsDelete());
        return model;
    } //changeToModel

    public static List<GeoKgoAgentModel> changeListToModel(List<GeoKgoAgent> entityList) {
        List<GeoKgoAgentModel> modelList = null;
        if (entityList!=null) {
            modelList = new ArrayList<>();
            for (int i=0; i<entityList.size(); i++) {
                GeoKgoAgentModel model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null)
        return modelList;
    } //changeListToModel
} 
