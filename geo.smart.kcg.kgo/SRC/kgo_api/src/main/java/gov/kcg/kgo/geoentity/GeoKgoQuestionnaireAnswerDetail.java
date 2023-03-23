package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 

/** 
 * GEO 20210829 add
 *
 * 問卷作答內容
 */

@Entity
@DynamicInsert
@Table(name = "GEO_KGO_QUESTIONNAIRE_ANSWER_DETAIL", schema = "dbo")
public class GeoKgoQuestionnaireAnswerDetail implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String detailId; //作答內容id 
    private String answerId; //作答id 
    private String detailCasesetId; //問卷題目id 
    private String answerValue; //回答內容 
    private Integer answerScore; //回答配分 

    @Id 
    @Column(name = "detail_id", columnDefinition = "varchar(30) NOT NULL") 
    public String getDetailId() { 
        return detailId; 
    } 

    public void setDetailId(String detailId) { 
        this.detailId = detailId; 
    } 

    @Basic 
    @Column(name = "answer_id", columnDefinition = "varchar(30) NOT NULL") 
    public String getAnswerId() { 
        return answerId; 
    } 

    public void setAnswerId(String answerId) { 
        this.answerId = answerId; 
    } 

    @Basic 
    @Column(name = "detail_caseset_id", columnDefinition = "varchar(30) NOT NULL") 
    public String getDetailCasesetId() { 
        return detailCasesetId; 
    } 

    public void setDetailCasesetId(String detailCasesetId) { 
        this.detailCasesetId = detailCasesetId; 
    } 

    @Basic 
    @Column(name = "answer_value", columnDefinition = "nvarchar(500)") 
    public String getAnswerValue() { 
        return answerValue; 
    } 

    public void setAnswerValue(String answerValue) { 
        this.answerValue = answerValue; 
    } 

    @Basic 
    @Column(name = "answer_score", columnDefinition = "int DEFAULT 0") 
    public Integer getAnswerScore() { 
        return answerScore; 
    } 

    public void setAnswerScore(Integer answerScore) { 
        this.answerScore = answerScore; 
    } 

} 
