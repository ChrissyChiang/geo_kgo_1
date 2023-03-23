package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Generated;

import java.io.Serializable; 

/** 
 * GEO 20211123 add 服務申辦統計名次排序條件
 * 服務申辦統計名次排序條件
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_CASESET_APPLY_COUNT_RULE", schema = "dbo")
public class GeoKgoCasesetApplyCountRule implements Serializable {

    private static final long serialVersionUID = 1L; 

    private Integer ruleId; //條件id
    private Integer searchRangeType; //搜尋區間 
    private Integer searchRank; //搜尋前幾名，預設10 
    private String dateStart; //起始日 yyyy-MM-dd，搜尋區間=4：必填 
    private String dateEnd; //結束日 yyyy-MM-dd，搜尋區間=4：必填 
    private String caseSetStatus; //案件啟用狀態 (開啟-On關閉-Off)，不填時包含所有狀態 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id")
    public Integer getRuleId() {
        return ruleId; 
    } 

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId; 
    } 

    @Basic 
    @Column(name = "search_range_type", columnDefinition = "int NULL") 
    public Integer getSearchRangeType() { 
        return searchRangeType; 
    } 

    public void setSearchRangeType(Integer searchRangeType) { 
        this.searchRangeType = searchRangeType; 
    } 

    @Basic 
    @Column(name = "search_rank", columnDefinition = "int NULL") 
    public Integer getSearchRank() { 
        return searchRank; 
    } 

    public void setSearchRank(Integer searchRank) { 
        this.searchRank = searchRank; 
    } 

    @Basic 
    @Column(name = "date_start", columnDefinition = "varchar(50)") 
    public String getDateStart() { 
        return dateStart; 
    } 

    public void setDateStart(String dateStart) { 
        this.dateStart = dateStart; 
    } 

    @Basic 
    @Column(name = "date_end", columnDefinition = "varchar(50) NULL") 
    public String getDateEnd() { 
        return dateEnd; 
    } 

    public void setDateEnd(String dateEnd) { 
        this.dateEnd = dateEnd; 
    } 

    @Basic 
    @Column(name = "case_set_status", columnDefinition = "varchar(50)") 
    public String getCaseSetStatus() { 
        return caseSetStatus; 
    } 

    public void setCaseSetStatus(String caseSetStatus) { 
        this.caseSetStatus = caseSetStatus; 
    } 

} 
