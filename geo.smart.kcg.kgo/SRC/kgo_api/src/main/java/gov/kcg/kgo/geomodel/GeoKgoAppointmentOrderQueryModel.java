package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoAppointment;
import gov.kcg.kgo.geoenum.RentalFuncEnum;
import gov.kcg.kgo.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
 * GEO 20211015 add
 * Model for 線上預約臨櫃-登錄預約主檔
 */
@Component
@ApiModel(description = "線上預約臨櫃-登錄預約主檔")
public class GeoKgoAppointmentOrderQueryModel implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "預約單id")
    private String appointmentId; 

    @ApiModelProperty(notes = "線上預約臨櫃主檔id")
    private String appointmentMainId; 

    @ApiModelProperty(notes = "線上預約臨櫃細節-預約時段id")
    private String appointmentDetailTimeId;

    @ApiModelProperty(notes = "線上預約臨櫃細節-號碼牌id")
    private String appointmentDetailNumbersId;

    @ApiModelProperty(notes = "預約者身分證字號")
    private String appointmentIdentity;

    @ApiModelProperty(notes = "預約者姓名")
    private String appointmentName; 

    @ApiModelProperty(notes = "預約者電子信箱")
    private String appointmentEmail; 

    @ApiModelProperty(notes = "預約者聯絡電話")
    private String appointmentPhone;

    @ApiModelProperty(value = "起始時間")
    private String dateStart;

    @ApiModelProperty(value = "結束時間 ")
    private String dateEnd;

    @ApiModelProperty(value = "日期 ")
    private String date;

    @ApiModelProperty(value = "號碼牌 ")
    private String numbersName;

    @ApiModelProperty(notes = "是否線上預約(GeoBooleanType)")
    private Integer isOnline;
 
    @ApiModelProperty(value = "案件狀態 ")
    private String caseStatus;     
    
    @ApiModelProperty(value = "繳費狀態 ")
    private String paymentStatus;

    @ApiModelProperty(value="是否繳費")
    private Boolean needPay;

    @ApiModelProperty(value = "預約狀態")
    private String rentalStatus;

    @ApiModelProperty(value = "流程 ")
    private String processId;
    
    @ApiModelProperty(value = "案件Id ")
    private String caseId;
    
    @ApiModelProperty(value = "案件類型 ")
    private String caseSetCategory;
    
    @ApiModelProperty(value = "繳費金額 ")
    private Integer payAmount;
    
    @ApiModelProperty(value = "繳費期限 ")
    private String payDeadline;
    
    @ApiModelProperty(value = "退費期限 ")
    private Integer refundDeadline;
    
    @ApiModelProperty(value = "繳費時間")
    private String paytime; 
    
    @ApiModelProperty(value = "最晚預約天數")
    private Integer appointmentLatestDay; 
    
    @ApiModelProperty(value = "最晚預約時間 ")
    private String appointmentLatestTime; 
    
    @ApiModelProperty(value = "案件申請時間 ")
    private String applyDate;

    @ApiModelProperty(value = "租借位置名稱")
    private String rentalObject;

    @ApiModelProperty(value = "繳費類型")
    private String payType;

    @ApiModelProperty(value="功能按鈕清單")
    private List<Map<String,String>> funcList;
       
    public String getAppointmentId() { 
        return appointmentId; 
    } 

    public void setAppointmentId(String appointmentId) { 
        this.appointmentId = appointmentId; 
    } 

    public String getAppointmentMainId() { 
        return appointmentMainId; 
    } 

    public void setAppointmentMainId(String appointmentMainId) { 
        this.appointmentMainId = appointmentMainId; 
    } 

    public String getAppointmentDetailTimeId() {
        return appointmentDetailTimeId;
    } 

    public void setAppointmentDetailTimeId(String appointmentDetailTimeId) {
        this.appointmentDetailTimeId = appointmentDetailTimeId;
    } 

    public String getAppointmentDetailNumbersId() {
        return appointmentDetailNumbersId;
    } 

    public void setAppointmentDetailNumbersId(String appointmentDetailNumbersId) {
        this.appointmentDetailNumbersId = appointmentDetailNumbersId;
    } 

    public String getAppointmentIdentity() {
        return appointmentIdentity;
    } 

    public void setAppointmentIdentity(String appointmentIdentity) {
        this.appointmentIdentity = appointmentIdentity;
    } 

    public String getAppointmentName() { 
        return appointmentName; 
    } 

    public void setAppointmentName(String appointmentName) { 
        this.appointmentName = appointmentName; 
    } 

    public String getAppointmentEmail() { 
        return appointmentEmail; 
    } 

    public void setAppointmentEmail(String appointmentEmail) { 
        this.appointmentEmail = appointmentEmail; 
    } 

    public String getAppointmentPhone() { 
        return appointmentPhone; 
    } 

    public void setAppointmentPhone(String appointmentPhone) { 
        this.appointmentPhone = appointmentPhone; 
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = DateUtil.dateToString(dateStart, DateUtil.PATTEN_HOUR_MINUTE);
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = DateUtil.dateToString(dateEnd, DateUtil.PATTEN_HOUR_MINUTE);
    }

    public String getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = DateUtil.dateToString(date, DateUtil.PATTEN_YEAR_MONTH_DAY);
    }

    public String getNumbersName() {
        return numbersName;
    }

    public void setNumbersName(String numbersName) {
        this.numbersName = numbersName;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }
        

    public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

    public String getPaymentStatus() {return paymentStatus;}

    public void setPaymentStatus(String paymentStatus) {this.paymentStatus = paymentStatus;}

    public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	
	public String getCaseSetCategory() {
		return caseSetCategory;
	}

	public void setCaseSetCategory(String caseSetCategory) {
		this.caseSetCategory = caseSetCategory;
	}
		

	public Integer getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}

    public String getPayType() {return payType;}

    public void setPayType(String payType) {this.payType = payType;}

    public String getPayDeadline() {
		return payDeadline;
	}

	public void setPayDeadline(String payDeadline) {
		this.payDeadline = payDeadline;
	}

	public String getPaytime() {
		return paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}

	public Integer getAppointmentLatestDay() {
		return appointmentLatestDay;
	}

	public void setAppointmentLatestDay(Integer appointmentLatestDay) {
		this.appointmentLatestDay = appointmentLatestDay;
	}

	public String getAppointmentLatestTime() {
		return appointmentLatestTime;
	}

	public void setAppointmentLatestTime(String appointmentLatestTime) {
		this.appointmentLatestTime = appointmentLatestTime;
	}
		

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

    public Integer getRefundDeadline() {return refundDeadline;}

    public void setRefundDeadline(Integer refundDeadline) {this.refundDeadline = refundDeadline;}

    public String getRentalObject() {return rentalObject;}

    public List<Map<String, String>> getFuncList() {return funcList;}

    public void setFuncList(List<Map<String, String>> funcList) {this.funcList = funcList;}

    public void setRentalObject(String rentalObject) {this.rentalObject = rentalObject;}

    public String getRentalStatus() {return rentalStatus;}

    public void setRentalStatus(String rentalStatus) {this.rentalStatus = rentalStatus;}

    public Boolean getNeedPay() {return needPay;}

    public void setNeedPay(Boolean needPay) {this.needPay = needPay;}

    public static GeoKgoAppointmentOrderQueryModel changeToModel(GeoKgoAppointment entity) {
        GeoKgoAppointmentOrderQueryModel model = new GeoKgoAppointmentOrderQueryModel();
        model.setAppointmentId(entity.getAppointmentId());
        model.setAppointmentMainId(entity.getAppointmentMainId());
        model.setAppointmentDetailTimeId(entity.getAppointmentDetailTimeId());
        model.setAppointmentDetailNumbersId(entity.getAppointmentDetailNumbersId());
        model.setAppointmentIdentity(entity.getAppointmentIdentity());
        model.setAppointmentName(entity.getAppointmentName());
        model.setAppointmentEmail(entity.getAppointmentEmail());
        model.setAppointmentPhone(entity.getAppointmentPhone());
        model.setIsOnline(entity.getIsOnline());
        return model; 
    } //changeToModel 

    public static List<GeoKgoAppointmentOrderQueryModel> changeListToModel(List<GeoKgoAppointment> entityList) {
        List<GeoKgoAppointmentOrderQueryModel> modelList = null;
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoAppointmentOrderQueryModel model = changeToModel(entityList.get(i));
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
