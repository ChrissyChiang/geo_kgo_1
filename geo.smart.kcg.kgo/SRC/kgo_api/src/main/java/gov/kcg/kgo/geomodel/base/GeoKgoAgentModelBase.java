package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import java.sql.Timestamp; 
import gov.kcg.kgo.geoentity.GeoKgoAgent; 

/** 
 * GEO 20211026 add
 * Model for 代理人機制
 */
@Component
@ApiModel(description = "代理人機制")
public class GeoKgoAgentModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "代理人機制id")
    private String agentId; 

    @ApiModelProperty(notes = "被代理人id")
    private String principalUserId; 

    @ApiModelProperty(notes = "代理人id")
    private String agentUserId; 

    @ApiModelProperty(notes = "代理起始時間")
    private Timestamp startTime; 

    @ApiModelProperty(notes = "代理結束時間")
    private Timestamp endTime; 

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

    public Timestamp getStartTime() { 
        return startTime; 
    } 

    public void setStartTime(Timestamp startTime) { 
        this.startTime = startTime; 
    } 

    public Timestamp getEndTime() { 
        return endTime; 
    } 

    public void setEndTime(Timestamp endTime) { 
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



    public static GeoKgoAgentModelBase changeToModel(GeoKgoAgent entity) { 
        GeoKgoAgentModelBase model = new GeoKgoAgentModelBase(); 
        model.setAgentId(entity.getAgentId()); 
        model.setPrincipalUserId(entity.getPrincipalUserId()); 
        model.setAgentUserId(entity.getAgentUserId()); 
        model.setStartTime(entity.getStartTime()); 
        model.setEndTime(entity.getEndTime()); 
        model.setIsSigned(entity.getIsSigned()); 
        model.setIsDelete(entity.getIsDelete()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoAgentModelBase> changeListToModel(List<GeoKgoAgent> entityList) { 
        List<GeoKgoAgentModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoAgentModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
