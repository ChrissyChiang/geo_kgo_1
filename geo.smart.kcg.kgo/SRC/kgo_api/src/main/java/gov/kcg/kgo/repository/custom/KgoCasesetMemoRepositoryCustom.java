package gov.kcg.kgo.repository.custom;

public interface KgoCasesetMemoRepositoryCustom {

	/**
	 * 臨櫃申請-申請說明資料儲存
	 * 
	 * @return List<CaseManagementQueryDto>
	 */
	public int saveData(String caseSetId, String applyType, String title, String contenHtml, String fileName);

}
