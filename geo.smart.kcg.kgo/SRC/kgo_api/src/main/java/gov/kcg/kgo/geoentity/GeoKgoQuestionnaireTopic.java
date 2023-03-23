package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 

/** 
 * GEO 20210829 add
 *
 * 問卷題組檔
 */

@Entity
@DynamicInsert
@Table(name = "GEO_KGO_QUESTIONNAIRE_TOPIC", schema = "dbo")
public class GeoKgoQuestionnaireTopic implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String topicId; //題組id 
    private String topicName; //題組名稱 
    private String topicDesc; //題組描述 
    private Integer isEnable; //是否有效(GeoBooleanType) 

    @Id 
    @Column(name = "topic_id", columnDefinition = "varchar(30) NOT NULL") 
    public String getTopicId() { 
        return topicId; 
    } 

    public void setTopicId(String topicId) { 
        this.topicId = topicId; 
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
    @Column(name = "is_enable", columnDefinition = "int DEFAULT 1") 
    public Integer getIsEnable() { 
        return isEnable; 
    } 

    public void setIsEnable(Integer isEnable) { 
        this.isEnable = isEnable; 
    } 

} 
