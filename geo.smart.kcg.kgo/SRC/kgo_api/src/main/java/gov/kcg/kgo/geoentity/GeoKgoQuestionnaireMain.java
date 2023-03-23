package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 
import java.sql.Timestamp; 

/** 
 * GEO 20210829 add
 *
 * 問卷主檔
 */

@Entity
@DynamicInsert
@Table(name = "GEO_KGO_QUESTIONNAIRE_MAIN", schema = "dbo")
public class GeoKgoQuestionnaireMain implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String questionId; //問卷id 
    private String questionName; //問卷主題 
    private String questionDesc; //問卷描述
    private Integer isDefault; //是否為預設問卷不可刪除(GeoBooleanType)
    private Integer isEnable; //是否有效(GeoBooleanType) 
    private String editOrgan; //編輯單位 
    private String editUser; //編輯人員 
    private Timestamp editTime; //編輯時間 

    @Id 
    @Column(name = "question_id", columnDefinition = "varchar(30) NOT NULL") 
    public String getQuestionId() { 
        return questionId; 
    } 

    public void setQuestionId(String questionId) { 
        this.questionId = questionId; 
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
