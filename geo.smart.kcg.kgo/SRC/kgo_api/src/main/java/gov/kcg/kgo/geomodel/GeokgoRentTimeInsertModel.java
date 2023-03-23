package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoCasesetRentTime;
import gov.kcg.kgo.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GEO 20221019 add
 *  場地/活動當日預約-時段
 */
@Component
@ApiModel(description = "場地/活動當日預約-時段")
public class GeokgoRentTimeInsertModel implements Serializable {
    private final Logger logger = LoggerFactory.getLogger(GeokgoRentTimeInsertModel.class);

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "預約時段id 新增不填")
    private String rentTimeId;

    @ApiModelProperty(notes = "預約日期id 新增不填")
    private String rentDateId;

    @ApiModelProperty(notes = "預約時段起始 yyyy-MM-dd hh:mm:ss", required = true)
    private String startTime;

    @ApiModelProperty(notes = "預約時段結束 yyyy-MM-dd hh:mm:ss", required = true)
    private String endTime;

    @ApiModelProperty(notes = "可預約人數")
    private Integer availableUserQuota;

    @ApiModelProperty(notes = "時段鎖定", required = true)
    private Integer isLocked;

    @ApiModelProperty(notes = "已使用人數")
    private Integer usedUsersNum;

    @ApiModelProperty(notes = "已使用人數")
    private Integer unitPrice;


    public String getRentTimeId() {
        return rentTimeId;
    }

    public void setRentTimeId(String rentTimeId) {
        this.rentTimeId = rentTimeId;
    }

    public String getRentDateId() {
        return rentDateId;
    }

    public void setRentDateId(String rentDateId) {
        this.rentDateId = rentDateId;
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

    public Integer getAvailableUserQuota() {
        return availableUserQuota;
    }

    public void setAvailableUserQuota(Integer availableUserQuota) {
        this.availableUserQuota = availableUserQuota;
    }

    public Integer getIsLocked() {return isLocked;}

    public void setIsLocked(Integer isLocked) {this.isLocked = isLocked;}

    public Integer getUsedUsersNum() {
        return usedUsersNum;
    }

    public void setUsedUsersNum(Integer usedUsersNum) {
        this.usedUsersNum = usedUsersNum;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public static List<GeokgoRentTimeInsertModel> changeListToModel(List<GeoKgoCasesetRentTime> entityList) {
        if(entityList != null){
            return entityList.stream().map(entity ->changeToModel(entity)).collect(Collectors.toList());
        }
        return null;
    }
    public static List<GeokgoRentTimeInsertModel> changeListToQueryDateModel(List<GeoKgoCasesetRentTime> entityList) {
        if (entityList != null) {
            return entityList.stream().map(entity -> changeToQueryTimeModel(entity)).collect(Collectors.toList());
        }
        return null;
    }
    /**
     * 變更時間格式為字串: yyyy-MM-dd HH:mm
     * */
    public static GeokgoRentTimeInsertModel changeToModel(GeoKgoCasesetRentTime entity) {
        GeokgoRentTimeInsertModel model = new GeokgoRentTimeInsertModel();
        model.setRentTimeId(entity.getRentTimeId());
        model.setRentDateId(entity.getRentDateId());
        model.setStartTime(DateUtil.dateToString(entity.getStartTime(), DateUtil.PATTEN_HOUR_MINUTE));
        model.setEndTime(DateUtil.dateToString(entity.getEndTime(), DateUtil.PATTEN_HOUR_MINUTE));
        model.setAvailableUserQuota(entity.getAvailableUserQouta());
        model.setIsLocked(entity.getIsLocked());
        model.setUsedUsersNum(entity.getUsedUsersNum());
        model.setUnitPrice(entity.getUnitPrice());
        return model;
    }
    /**
     * 變更時間格式為字串: HH:mm:ss
     * */
    public static GeokgoRentTimeInsertModel changeToQueryTimeModel(GeoKgoCasesetRentTime entity) {
        GeokgoRentTimeInsertModel model = new GeokgoRentTimeInsertModel();
        model.setRentTimeId(entity.getRentTimeId());
        model.setRentDateId(entity.getRentDateId());
        model.setStartTime(DateUtil.dateToString(entity.getStartTime(), DateUtil.PATTEN_FULL_TIME));
        model.setEndTime(DateUtil.dateToString(entity.getEndTime(), DateUtil.PATTEN_FULL_TIME));
        model.setIsLocked(entity.getIsLocked());
        model.setAvailableUserQuota(entity.getAvailableUserQouta());
        model.setUsedUsersNum(entity.getUsedUsersNum());
        model.setUnitPrice(entity.getUnitPrice());
        return model;
    }

    public static GeoKgoCasesetRentTime changeToEntity(GeokgoRentTimeInsertModel model ) throws ParseException {
        GeoKgoCasesetRentTime entity = new GeoKgoCasesetRentTime();
        entity.setRentDateId(model.getRentDateId());
        entity.setRentTimeId(model.getRentTimeId());
        entity.setAvailableUserQouta(model.getAvailableUserQuota());
        entity.setStartTime(DateUtil.strToTimestamp(model.getStartTime(),DateUtil.PATTEN_FULL_TIME));
        entity.setEndTime(DateUtil.strToTimestamp(model.getEndTime(),DateUtil.PATTEN_FULL_TIME));
        entity.setIsLocked(model.getIsLocked());
        entity.setUsedUsersNum(model.getUsedUsersNum());
        entity.setUnitPrice(model.getUnitPrice());
        return entity;
    }

    @Override
    public String toString() {
        return "GeokgoRentTimeInsertModel{" +
                "rentTimeId='" + rentTimeId + '\'' +
                ", rentDateId='" + rentDateId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", availableUserQuota=" + availableUserQuota +
                ", isLocked=" + isLocked +
                ", usedUsersNum=" + usedUsersNum +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
