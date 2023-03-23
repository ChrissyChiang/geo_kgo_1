package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 

/** 
 * GEO 20210829 add
 *
 * 服務問卷子題目檔
 */

@Entity
@DynamicInsert
@Table(name = "GEO_KGO_QUESTIONNAIRE_CASESET_DETAIL", schema = "dbo")
public class GeoKgoQuestionnaireCasesetDetail implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String detailCasesetId; //服務問卷子題目id 
    private String topicCasesetId; //服務問卷題組id 
    private String detailName; //子題目名稱 
    private Integer detailType; //子題目類型(GeoQuestionnaireDetailType) 
    private Integer detailSort; //子題目排序
    private String detailChoose; //選項資料(ex.A-是,B-否)
    private Integer isMust; //是否必填(GeoBooleanType) 

    @Id 
    @Column(name = "detail_caseset_id", columnDefinition = "varchar(30) NOT NULL") 
    public String getDetailCasesetId() { 
        return detailCasesetId; 
    } 

    public void setDetailCasesetId(String detailCasesetId) { 
        this.detailCasesetId = detailCasesetId; 
    } 

    @Basic 
    @Column(name = "topic_caseset_id", columnDefinition = "varchar(30)") 
    public String getTopicCasesetId() { 
        return topicCasesetId; 
    } 

    public void setTopicCasesetId(String topicCasesetId) { 
        this.topicCasesetId = topicCasesetId; 
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
