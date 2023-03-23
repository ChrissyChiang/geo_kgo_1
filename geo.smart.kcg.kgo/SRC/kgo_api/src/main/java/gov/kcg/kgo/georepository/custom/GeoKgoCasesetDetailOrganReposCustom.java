package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.dto.SaCaseViewOrganFormDetailColumnQueryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * GEO 20211109 add 機關審核表單
 * 網路申辦-表單設定- 機關審核表單 表單詳細資料
 */
@Repository
public class GeoKgoCasesetDetailOrganReposCustom extends GeoBaseReposCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoKgoCasesetDetailOrganReposCustom.class);

    /**
     * 取得服務申辦申請各欄位資料
     *
     * @param caseId
     * @return
     */
    public List<SaCaseViewOrganFormDetailColumnQueryDto> getSaCaseDetailData(String caseId) {
        String selectStr = "Select ROW_NUMBER() OVER(order by c.OrderNum, cc.Row,cc.OrderNum) AS ROW_NUMBER, c.ColumnName COLUMN_NAME, d.ColumnValue REAL_COLUMN_VALUE, d.ColumnValue REAL_PDF_COLUMN_VALUE\n" +
                "     , c. IsMustKey IS_MUST_KEY, c.ColumnValue SET_COLUMN_VALUE, c.ColumnType SET_COLUMN_TYPE, c.ColumnId SET_COLUMN_ID, cc.ColumnValue SET_CCOLUMN_VALUE\n" +
                "     , cc.ColumnType SET_CCOLUMN_TYPE, cc.CColumnId SET_CCOLUMN_ID, d.IsSource IS_SOURCE, d.IsCorrect IS_CORRECT, cc.FText F_TEXT, cc.BText B_TEXT\n" +
                "     , d.CorrectMemo CORRECT_MEMO, d.CorrectBValue CORRECT_B_VALUE, d.CorrectBValue CORRECT_B_PDF_VALUE, m.fdate FDATE, c.OrderNum COLUMN_ORDER_NUM, cc.Row CCOLUMN_ROW_NUM\n" +
                "     , cc.OrderNum CCOLUMN_ORDER_NUM, d.CommentId COMMENT_ID, d.CaseFormVersion CASE_FORM_VERSION ";
        String fromStr = "from GEO_KGO_CASE_DETAIL_ORGAN d\n" +
                "         Left join KGO_CASE_MAIN m on m.CaseId=d.CaseId AND m.IsOpenOrganForm = 1\n" +
                "         Left join GEO_KGO_CASESET_COLUMN_ORGAN c on m.CaseSetId=c.CaseSetId  and d.CaseFormVersion=c.CaseFormVersion  and d.ColumnId=c.ColumnId\n" +
                "         left join GEO_KGO_CASESET_COLUMN_CHILD_ORGAN cc on m.CaseSetId=cc.CaseSetId and d.CaseFormVersion=cc.CaseFormVersion  and c.ColumnId=cc.ColumnId and cc.CColumnId = d.CColumnId ";
        String whereStr = "where d.CaseId=:caseId  ORDER BY ROW_NUMBER ";

        Query query = getEntityManager().createNativeQuery(selectStr + fromStr + whereStr, SaCaseViewOrganFormDetailColumnQueryDto.class);
        query.setParameter("caseId", caseId);

        return query.getResultList();
    } //getSaCaseDetailData

    public Integer findIsShowByCasesetIdAndCaseFormVersion(String caseSetId, Integer caseFormVersion){
        String sqlStr = "select IsShow from GEO_KGO_CASESET_GROUP_ORGAN where CaseSetId = :caseSetId AND CaseFormVersion = :caseFormVersion ";
        Query query = getEntityManager().createNativeQuery(sqlStr);
        query.setParameter("caseSetId", caseSetId);
        query.setParameter("caseFormVersion", caseFormVersion);
        Integer isShow = null;
        try {
            isShow = (Integer) query.getSingleResult();
        } catch (Exception e) {
            LOGGER.error("GeoKgoCasesetOrganGroupReposCustom findIsShowByCasesetIdAndGroupSeq error:" + e.getMessage());
        }
        return isShow;
    } //findIsShowByCasesetIdAndCaseFormVersion

}
