package gov.kcg.kgo.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.repository.custom.KgoCasesetMemoRepositoryCustom;

public class KgoCasesetMemoRepositoryCustomImpl implements KgoCasesetMemoRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoCasesetMemoRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 臨櫃申請-申請說明資料儲存
	 * 
	 * @param caseSetId
	 * @param applyType
	 * @param title
	 * @param contenHtml
	 * @param fileName
	 * @return
	 */
	@Override
	public int saveData(String caseSetId, String applyType, String title, String contenHtml, String fileName) {
		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append(
				"INSERT INTO KGO_CASESET_MEMO (CaseSetId, ApplyType, Title, ContentHtml, FileName) VALUES (:caseSetId, :applyType, :title, :contenHtml, :fileName)");

		/**
		 * Native Query
		 */
		return entityManager.createNativeQuery(sb.toString()).setParameter("caseSetId", caseSetId)
				.setParameter("applyType", applyType).setParameter("title", title)
				.setParameter("contenHtml", contenHtml).setParameter("fileName", fileName).executeUpdate();
	}

}
