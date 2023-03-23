package gov.kcg.kgo.geoentity;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * GEO 20211015 add
 * 線上預約臨櫃名稱黑名單
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_APPOINTMENT_BLOCK_USER", schema = "dbo")
public class GeoKgoAppointmentBlockUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String blockUserId; //黑名單使用者id 
    private String appointmentMainId; //線上預約臨櫃主檔id 
    private String blockUseName; //黑名單使用者姓名 
    private String blockUserIdentity; //黑名單使用者身分證字號
    private Timestamp blockStartTime; //黑名單鎖定起始時間 
    private Timestamp blockEndTime; //黑名單鎖定結束時間 
    private Integer isTriggerBlock; //是否啟用封鎖(GeoBooleanType)
    private String editOrgan; //編輯單位 
    private String editUser; //編輯人員 
    private Timestamp editTime; //編輯時間 

    @Id
    @Column(name = "block_user_id", columnDefinition = "varchar(30) NOT NULL")
    public String getBlockUserId() {
        return blockUserId;
    }

    public void setBlockUserId(String blockUserId) {
        this.blockUserId = blockUserId;
    }

    @Basic
    @Column(name = "appointment_main_id", columnDefinition = "varchar(50) NOT NULL")
    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }

    @Basic
    @Column(name = "block_use_name", columnDefinition = "nvarchar(50) NULL")
    public String getBlockUseName() {
        return blockUseName;
    }

    public void setBlockUseName(String blockUseName) {
        this.blockUseName = blockUseName;
    }

    @Basic
    @Column(name = "block_user_identity", columnDefinition = "varchar(50) NULL")
    public String getBlockUserIdentity() {
        return blockUserIdentity;
    }

    public void setBlockUserIdentity(String blockUserIdetity) {
        this.blockUserIdentity = blockUserIdetity;
    }

    @Basic
    @Column(name = "block_start_time", columnDefinition = "datetime2(0) NULL")
    public Timestamp getBlockStartTime() {
        return blockStartTime;
    }

    public void setBlockStartTime(Timestamp blockStartTime) {
        this.blockStartTime = blockStartTime;
    }

    @Basic
    @Column(name = "block_end_time", columnDefinition = "datetime2(0) NULL")
    public Timestamp getBlockEndTime() {
        return blockEndTime;
    }

    public void setBlockEndTime(Timestamp blockEndTime) {
        this.blockEndTime = blockEndTime;
    }

    @Basic
    @Column(name = "is_trigger_block", columnDefinition = "varchar(1)")
    public Integer getIsTriggerBlock() {
        return isTriggerBlock;
    }

    public void setIsTriggerBlock(Integer isTriggerBlock) {
        this.isTriggerBlock = isTriggerBlock;
    }

    @Basic
    @Column(name = "edit_organ", columnDefinition = "varchar(50)")
    public String getEditOrgan() {
        return editOrgan;
    }

    public void setEditOrgan(String editOrgan) {
        this.editOrgan = editOrgan;
    }

    @Basic
    @Column(name = "edit_user", columnDefinition = "varchar(50)")
    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    @Basic
    @Column(name = "edit_time", columnDefinition = "datetime2(0)")
    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

} 
