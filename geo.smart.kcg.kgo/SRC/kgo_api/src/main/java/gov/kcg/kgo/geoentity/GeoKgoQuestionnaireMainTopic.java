package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 

/** 
 * GEO 20210829 add
 *
 * 問卷題組對應
 */

@Entity
@DynamicInsert
@IdClass(GeoKgoQuestionnaireMainTopicPK.class)
@Table(name = "GEO_KGO_QUESTIONNAIRE_MAIN_TOPIC", schema = "dbo")
public class GeoKgoQuestionnaireMainTopic implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String questionId; //問卷id 
    private String topicId; //題組id 
    private Integer topicSort; //題組排序 

    @Id 
    @Column(name = "question_id", columnDefinition = "varchar(30) NOT NULL") 
    public String getQuestionId() { 
        return questionId; 
    } 

    public void setQuestionId(String questionId) { 
        this.questionId = questionId; 
    } 

    @Id 
    @Column(name = "topic_id", columnDefinition = "varchar(30) NOT NULL") 
    public String getTopicId() { 
        return topicId; 
    } 

    public void setTopicId(String topicId) { 
        this.topicId = topicId; 
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
