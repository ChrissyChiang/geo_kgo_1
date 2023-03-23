package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 

/** 
 * GEO 20211224 add 線上預約表單
 * 預約表單 設定群組
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_APPOINTMENT_GROUP", schema = "dbo") 
public class GeoKgoAppointmentGroup implements Serializable {

    private static final long serialVersionUID = 1L; 

    @EmbeddedId
    private GeoKgoAppointmentGroupPK id;
    private String memo; //備註
    private Integer orderNum; //顯示順序
    private Integer isShow; //是否顯示
    private String checkFrequencyPeriod; //重複檢核時間

    public GeoKgoAppointmentGroupPK getId() {
        return id;
    }

    public void setId(GeoKgoAppointmentGroupPK id) {
        this.id = id;
    }

    @Basic 
    @Column(name = "Memo", columnDefinition = "nvarchar(200) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getMemo() { 
        return memo;
    } 

    public void setMemo(String Memo) { 
        this.memo = Memo;
    } 

    @Basic 
    @Column(name = "OrderNum", columnDefinition = "int  NULL") 
    public Integer getOrderNum() { 
        return orderNum;
    } 

    public void setOrderNum(Integer OrderNum) { 
        this.orderNum = OrderNum;
    } 

    @Basic 
    @Column(name = "IsShow", columnDefinition = "int") 
    public Integer getIsShow() { 
        return isShow;
    } 

    public void setIsShow(Integer IsShow) { 
        this.isShow = IsShow;
    } 

    @Basic 
    @Column(name = "CheckFrequencyPeriod", columnDefinition = "varchar(30) NULL") 
    public String getCheckFrequencyPeriod() { 
        return checkFrequencyPeriod;
    } 

    public void setCheckFrequencyPeriod(String CheckFrequencyPeriod) { 
        this.checkFrequencyPeriod = CheckFrequencyPeriod;
    } 

} 
