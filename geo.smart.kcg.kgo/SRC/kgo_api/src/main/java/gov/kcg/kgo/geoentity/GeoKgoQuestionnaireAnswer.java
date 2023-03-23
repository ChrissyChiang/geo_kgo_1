package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 
import java.sql.Timestamp; 

/** 
 * GEO 20210829 add
 *
 * 問卷作答主檔
 */

@Entity
@DynamicInsert
@Table(name = "GEO_KGO_QUESTIONNAIRE_ANSWER", schema = "dbo")
public class GeoKgoQuestionnaireAnswer implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String answerId; //作答id 
    private String caseId; //案件id 
    private String caseSetId; //服務id 
    private Integer questiinVersion; //問卷版本 
    private String applyUserId; //作答者id 
    private String applyName; //作答者姓名 
    private Timestamp applyDate; //作答日期 

    @Id 
    @Column(name = "answer_id", columnDefinition = "varchar(30) NOT NULL") 
    public String getAnswerId() { 
        return answerId; 
    } 

    public void setAnswerId(String answerId) { 
        this.answerId = answerId; 
    } 

    @Basic 
    @Column(name = "case_id", columnDefinition = "varchar(30)") 
    public String getCaseId() { 
        return caseId; 
    } 

    public void setCaseId(String caseId) { 
        this.caseId = caseId; 
    } 

    @Basic 
    @Column(name = "case_set_id", columnDefinition = "varchar(30) NOT NULL") 
    public String getCaseSetId() { 
        return caseSetId; 
    } 

    public void setCaseSetId(String caseSetId) { 
        this.caseSetId = caseSetId; 
    } 

    @Basic 
    @Column(name = "questiin_version", columnDefinition = "int") 
    public Integer getQuestiinVersion() { 
        return questiinVersion; 
    } 

    public void setQuestiinVersion(Integer questiinVersion) { 
        this.questiinVersion = questiinVersion; 
    } 

    @Basic 
    @Column(name = "apply_user_id", columnDefinition = "varchar(50)") 
    public String getApplyUserId() { 
        return applyUserId; 
    } 

    public void setApplyUserId(String applyUserId) { 
        this.applyUserId = applyUserId; 
    } 

    @Basic 
    @Column(name = "apply_name", columnDefinition = "nvarchar(50)") 
    public String getApplyName() { 
        return applyName; 
    } 

    public void setApplyName(String applyName) { 
        this.applyName = applyName; 
    } 

    @Basic 
    @Column(name = "apply_date", columnDefinition = "datetime") 
    public Timestamp getApplyDate() { 
        return applyDate; 
    } 

    public void setApplyDate(Timestamp applyDate) { 
        this.applyDate = applyDate; 
    } 

} 
