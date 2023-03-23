package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 

/** 
 * GEO 20210829 add
 *
 * 問卷題組子題目檔
 */

@Entity
@DynamicInsert
@Table(name = "GEO_KGO_QUESTIONNAIRE_TOPIC_DETAIL", schema = "dbo")
public class GeoKgoQuestionnaireTopicDetail implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String detailId; //子題目id 
    private String topicId; //題組id 
    private String detailName; //子題目名稱 
    private Integer detailType; //子題目類型(GeoQuestionnaireDetailType) 
    private Integer detailSort; //子題目排序
    private String detailChoose; //選項資料(ex.A-是,B-否)
    private Integer isMust; //是否必填(GeoBooleanType)

    @Id 
    @Column(name = "detail_id", columnDefinition = "varchar(30) NOT NULL") 
    public String getDetailId() { 
        return detailId; 
    } 

    public void setDetailId(String detailId) { 
        this.detailId = detailId; 
    } 

    @Basic 
    @Column(name = "topic_id", columnDefinition = "varchar(30)") 
    public String getTopicId() { 
        return topicId; 
    } 

    public void setTopicId(String topicId) { 
        this.topicId = topicId; 
    } 

    @Basic 
    @Column(name = "detail_name", columnDefinition = "nvarchar(500)") 
    public String getDetailName() { 
        return detailName; 
    } 

    public void setDetailName(String detailName) { 
        this.detailName = detailName; 
    } 

    @Basic 
    @Column(name = "detail_type", columnDefinition = "int") 
    public Integer getDetailType() { 
        return detailType; 
    } 

    public void setDetailType(Integer detailType) { 
        this.detailType = detailType; 
    } 

    @Basic 
    @Column(name = "detail_sort", columnDefinition = "int") 
    public Integer getDetailSort() { 
        return detailSort; 
    } 

    public void setDetailSort(Integer detailSort) { 
        this.detailSort = detailSort; 
    }

    @Basic
    @Column(name = "detail_choose", columnDefinition = "nvarchar(500)")
    public String getDetailChoose() {
        return detailChoose;
    }

    public void setDetailChoose(String detailChoose) {
        this.detailChoose = detailChoose;
    }

    @Basic 
    @Column(name = "is_must", columnDefinition = "int DEFAULT 0") 
    public Integer getIsMust() { 
        return isMust; 
    } 

    public void setIsMust(Integer isMust) { 
        this.isMust = isMust; 
    }

} 
