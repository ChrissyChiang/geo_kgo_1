package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 

/** 
 * GEO 20210829 add
 *
 * 服務問卷題組檔
 */

@Entity
@DynamicInsert
@Table(name = "GEO_KGO_QUESTIONNAIRE_CASESET_TOPIC", schema = "dbo")
public class GeoKgoQuestionnaireCasesetTopic implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String topicCasesetId; //服務問卷題組id 
    private String caseSetId; //服務id 
    private Integer questiinVersion; //問卷版本 
    private String topicName; //題組名稱 
    private String topicDesc; //題組描述 
    private Integer topicSort; //題組排序 

    @Id 
    @Column(name = "topic_caseset_id", columnDefinition = "varchar(30) NOT NULL") 
    public String getTopicCasesetId() { 
        return topicCasesetId; 
    } 

    public void setTopicCasesetId(String topicCasesetId) { 
        this.topicCasesetId = topicCasesetId; 
    } 

    @Basic 
    @Column(name = "case_set_id", columnDefinition = "varchar(30)") 
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
    @Column(name = "topic_name", columnDefinition = "nvarchar(100)") 
    public String getTopicName() { 
        return topicName; 
    } 

    public void setTopicName(String topicName) { 
        this.topicName = topicName; 
    } 

    @Basic 
    @Column(name = "topic_desc", columnDefinition = "nvarchar(500)") 
    public String getTopicDesc() { 
        return topicDesc; 
    } 

    public void setTopicDesc(String topicDesc) { 
        this.topicDesc = topicDesc; 
    } 

    @Basic 
    @Column(name = "topic_sort", columnDefinition = "int") 
    public Integer getTopicSort() { 
        return topicSort; 
    } 

    public void setTopicSort(Integer topicSort) { 
        this.topicSort = topicSort; 
    } 

} 
