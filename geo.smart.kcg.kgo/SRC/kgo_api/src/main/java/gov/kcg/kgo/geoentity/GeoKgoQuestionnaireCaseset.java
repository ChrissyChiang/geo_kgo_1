package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 
import java.sql.Timestamp; 

/** 
 * GEO 20210829 add
 *
 * 服務問卷主檔
 */

@Entity
@DynamicInsert
@IdClass(GeoKgoQuestionnaireCasesetPK.class)
@Table(name = "GEO_KGO_QUESTIONNAIRE_CASESET", schema = "dbo")
public class GeoKgoQuestionnaireCaseset implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String caseSetId; //服務id 
    private Integer questiinVersion; //問卷版本 
    private String questionName; //問卷主題 
    private String questionDesc; //問卷描述 
    private String dateStart; //起始日 
    private String dateEnd; //結束日 
    private Integer isDefault; //是否為預設問卷不可刪除(GeoBooleanType) 
    private Integer isEnable; //是否有效(GeoBooleanType) 
    private String editOrgan; //編輯單位 
    private String editUser; //編輯人員 
    private Timestamp editTime; //編輯時間 

    @Id 
    @Column(name = "case_set_id", columnDefinition = "varchar(30) NOT NULL") 
    public String getCaseSetId() { 
        return caseSetId; 
    } 

    public void setCaseSetId(String caseSetId) { 
        this.caseSetId = caseSetId; 
    } 

    @Id 
    @Column(name = "questiin_version", columnDefinition = "int DEFAULT 1 NOT NULL") 
    public Integer getQuestiinVersion() { 
        return questiinVersion; 
    } 

    public void setQuestiinVersion(Integer questiinVersion) { 
        this.questiinVersion = questiinVersion; 
    } 

    @Basic 
    @Column(name = "question_name", columnDefinition = "nvarchar(100)") 
    public String getQuestionName() { 
        return questionName; 
    } 

    public void setQuestionName(String questionName) { 
        this.questionName = questionName; 
    } 

    @Basic 
    @Column(name = "question_desc", columnDefinition = "nvarchar(500)") 
    public String getQuestionDesc() { 
        return questionDesc; 
    } 

    public void setQuestionDesc(String questionDesc) { 
        this.questionDesc = questionDesc; 
    } 

    @Basic 
    @Column(name = "date_start", columnDefinition = "varchar(30)") 
    public String getDateStart() { 
        return dateStart; 
    } 

    public void setDateStart(String dateStart) { 
        this.dateStart = dateStart; 
    } 

    @Basic 
    @Column(name = "date_end", columnDefinition = "varchar(30)") 
    public String getDateEnd() { 
        return dateEnd; 
    } 

    public void setDateEnd(String dateEnd) { 
        this.dateEnd = dateEnd; 
    } 

    @Basic 
    @Column(name = "is_default", columnDefinition = "int DEFAULT 0") 
    public Integer getIsDefault() { 
        return isDefault; 
    } 

    public void setIsDefault(Integer isDefault) { 
        this.isDefault = isDefault; 
    } 

    @Basic 
    @Column(name = "is_enable", columnDefinition = "int DEFAULT 1")
    public Integer getIsEnable() { 
        return isEnable; 
    } 

    public void setIsEnable(Integer isEnable) { 
        this.isEnable = isEnable; 
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
