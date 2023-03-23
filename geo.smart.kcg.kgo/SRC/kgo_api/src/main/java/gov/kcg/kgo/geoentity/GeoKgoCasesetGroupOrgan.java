package gov.kcg.kgo.geoentity;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import java.io.Serializable;

/**
 * GEO 20211109 add 機關審核表單
 * 機關審核表單 案件設定群組
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_CASESET_GROUP_ORGAN", schema = "dbo")
public class GeoKgoCasesetGroupOrgan implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private GeoKgoCasesetGroupOrganPK id;
    private String memo; //備註
    private Integer orderNum; //顯示順序
    private Integer isShow; //是否顯示
    private String checkFrequencyPeriod; //重複檢核時間

    public GeoKgoCasesetGroupOrganPK getId() {
        return id;
    }

    public void setId(GeoKgoCasesetGroupOrganPK id) {
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
    @Column(name = "OrderNum", columnDefinition = "int NULL")
    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer OrderNum) {
        this.orderNum = OrderNum;
    }

    @Basic
    @Column(name = "IsShow", columnDefinition = "int NULL")
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
