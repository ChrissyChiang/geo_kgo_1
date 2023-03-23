package gov.kcg.kgo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.dto.CaseCorrectDataDto;
import gov.kcg.kgo.dto.SaCaseViewDetailColumnQueryDto;
import gov.kcg.kgo.repository.custom.KgoCaseDetailRepositoryCustom;

public class KgoCaseDetailRepositoryCustomImpl implements KgoCaseDetailRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoCaseDetailRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 取得服務申辦申請各欄位資料
	 * 
	 * @param caseId
	 * @return
	 */
	@Override
	public List<SaCaseViewDetailColumnQueryDto> getSaCaseDetailData(String caseId) {

		StringBuilder sb = new StringBuilder();

		sb.append("Select ROW_NUMBER() OVER(order by c.OrderNum, cc.Row,cc.OrderNum) AS ROW_NUMBER, c.ColumnName COLUMN_NAME, d.ColumnValue REAL_COLUMN_VALUE, d.ColumnValue REAL_PDF_COLUMN_VALUE");
		sb.append("     , c. IsMustKey IS_MUST_KEY, c.ColumnValue SET_COLUMN_VALUE, c.ColumnType SET_COLUMN_TYPE, c.ColumnId SET_COLUMN_ID, cc.ColumnValue SET_CCOLUMN_VALUE");
		sb.append("     , cc.ColumnType SET_CCOLUMN_TYPE, c.MyDataColumn, c.MyDataId, cc.CColumnId SET_CCOLUMN_ID, d.IsSource IS_SOURCE, d.IsCorrect IS_CORRECT, cc.FText F_TEXT, cc.BText B_TEXT ");
		sb.append("     , d.CorrectMemo CORRECT_MEMO, d.CorrectBValue CORRECT_B_VALUE, d.CorrectBValue CORRECT_B_PDF_VALUE, m.fdate FDATE, c.OrderNum COLUMN_ORDER_NUM, cc.Row CCOLUMN_ROW_NUM ");
		sb.append("     , cc.OrderNum CCOLUMN_ORDER_NUM ");
		sb.append("from KGO_CASE_DETAIL d  ");
		sb.append("Left join KGO_CASE_MAIN m on m.CaseId=d.CaseId ");
		sb.append("Left join KGO_CASESET_COLUMN c on m.CaseSetId=c.CaseSetId  and d.Version=c.Version  and d.ColumnId=c.ColumnId ");
		sb.append("left join KGO_CASESET_COLUMN_Child cc on m.CaseSetId=cc.CaseSetId and d.Version=cc.Version  and c.ColumnId=cc.ColumnId and cc.CColumnId = d.CColumnId ");
		sb.append("where d.CaseId=:caseId  ORDER BY ROW_NUMBER");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), SaCaseViewDetailColumnQueryDto.class);
		query.setParameter("caseId", caseId);

		return query.getResultList();
	}

	/**
	 * GEO 20211102 add for 欄位勾選
	 * 取得服務申辦申請各欄位資料
	 * KGO_CASESET_COLUMN IsFieldCheck = 1
	 * @param caseId
	 * @return
	 */
	@Override
	public List<SaCaseViewDetailColumnQueryDto> getDetailData(String caseId) {

		StringBuilder sb = new StringBuilder();

		sb.append("Select ROW_NUMBER() OVER(order by c.OrderNum, cc.Row,cc.OrderNum) AS ROW_NUMBER, c.ColumnName COLUMN_NAME, d.ColumnValue REAL_COLUMN_VALUE, d.ColumnValue REAL_PDF_COLUMN_VALUE");
		sb.append("     , c. IsMustKey IS_MUST_KEY, c.ColumnValue SET_COLUMN_VALUE, c.ColumnType SET_COLUMN_TYPE, c.ColumnId SET_COLUMN_ID, cc.ColumnValue SET_CCOLUMN_VALUE");
		sb.append("     , cc.ColumnType SET_CCOLUMN_TYPE, c.MyDataColumn, c.MyDataId, cc.CColumnId SET_CCOLUMN_ID, d.IsSource IS_SOURCE, d.IsCorrect IS_CORRECT, cc.FText F_TEXT, cc.BText B_TEXT ");
		sb.append("     , d.CorrectMemo CORRECT_MEMO, d.CorrectBValue CORRECT_B_VALUE, d.CorrectBValue CORRECT_B_PDF_VALUE, m.fdate FDATE, c.OrderNum COLUMN_ORDER_NUM, cc.Row CCOLUMN_ROW_NUM ");
		sb.append("     , cc.OrderNum CCOLUMN_ORDER_NUM ");
		sb.append("from KGO_CASE_DETAIL d ");
		sb.append("Left join KGO_CASE_MAIN m on m.CaseId=d.CaseId ");
		sb.append("Left join KGO_CASESET_COLUMN c on m.CaseSetId=c.CaseSetId  and d.Version=c.Version  and d.ColumnId=c.ColumnId ");
		sb.append("left join KGO_CASESET_COLUMN_Child cc on m.CaseSetId=cc.CaseSetId and d.Version=cc.Version  and c.ColumnId=cc.ColumnId and cc.CColumnId = d.CColumnId ");
		sb.append("where d.CaseId=:caseId  And c.IsFieldCheck = 1 ORDER BY ROW_NUMBER");

		Query query = entityManager.createNativeQuery(sb.toString(), SaCaseViewDetailColumnQueryDto.class);
		query.setParameter("caseId", caseId);

		return query.getResultList();
	} //getDetailData

	@Override
	public List<CaseCorrectDataDto> listCaseCorrectData(String caseId, String caseSetId) {
		StringBuilder sb = new StringBuilder();

		sb.append(
				"select ROW_NUMBER() OVER(order by kcg.OrderNum asc,kcc.OrderNum asc, kccc.row asc, kccc.OrderNum asc) AS rowNumber, kcg.OrderNum kcgOrderNum, kcc.OrderNum kccOrderNum, kccc.row kcccRow, kccc.OrderNum kcccOrderNum, "
						+ "kcg.GroupSeq kcgGroupSeq, kcg.Memo kcgMemo, "
						+ "kcc.ColumnId kccColumnId, kcc.ColumnName kccColumnName, kcc.ColumnType kccColumnType, kcc.Version kccVersion, kcc.[Length] kccLength, kcc.IsMustKey kccIsMustKey, kcc.columnValue kccColumnValue, "
						+ "kccc.CColumnId kcccCColumnId, kccc.ColumnType kcccColumnType, kccc.[Length] kcccLength ,kccc.FText kcccFText, kccc.BText kcccBText, kccc.VGroup kcccVGroup, kccc.IsMustKey kcccIsMustKey, kccc.columnValue kcccColumnValue, kccc.PColumnId kcccPColumnId, "
						+ "kcd.ColumnValue kcdColumnValue, kcd.IsCorrect kcdIsCorrect, kcd.CorrectMemo kcdCorrectMemo, "
						+ "kcd2.ColumnValue kcd2ColumnValue, kcd2.IsCorrect kcd2IsCorrect, kcd2.CorrectMemo kcd2CorrectMemo " + "from KGO_CASESET_GROUP kcg "
						+ "inner join KGO_CASESET_COLUMN kcc on kcg.GroupSeq = kcc.GroupSeq "
						+ "left join KGO_CASESET_COLUMN_CHILD kccc on kcc.CaseSetId = kccc.CaseSetId and kcc.Version = kccc.Version and kcc.ColumnId = kccc.ColumnId "
						+ "left join KGO_CASE_DETAIL kcd on kccc.CaseSetId is null and kcd.ColumnId = kcc.ColumnId and kcd.CaseId = :caseId "
						+ "left join KGO_CASE_DETAIL kcd2 on kcd2.ColumnId = kcc.ColumnId and kcd2.CColumnId = kccc.CColumnId and kcd2.CaseId = :caseId "
						+ "where kcg.CaseSetId = :caseSetId and kcc.Version = (select MAX(Version) from KGO_CASE_DETAIL where CaseId = :caseId )");
		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), CaseCorrectDataDto.class);
		query.setParameter("caseId", caseId);
		query.setParameter("caseSetId", caseSetId);

		return query.getResultList();
	}

}
