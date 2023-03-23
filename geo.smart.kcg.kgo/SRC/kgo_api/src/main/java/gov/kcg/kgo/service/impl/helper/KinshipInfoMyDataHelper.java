package gov.kcg.kgo.service.impl.helper;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

/**
 * 親屬關係資料
 * 
 * @author joelee
 *
 */
public class KinshipInfoMyDataHelper extends KgoMydataBaseServiceHelper {
	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KinshipInfoMyDataHelper.class);

	/**
	 * Instantiates a new service helper.
	 */
	public KinshipInfoMyDataHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final KinshipInfoMyDataHelper instance = new KinshipInfoMyDataHelper();
	}

	/**
	 * Gets the single instance of KinshipInfoMyDataHelper.
	 *
	 * @return single instance of KinshipInfoMyDataHelper
	 */
	public static KinshipInfoMyDataHelper getInstance() {
		return Loader.instance;
	}

	@Override
	public String getMyDataId() {
		// TODO Auto-generated method stub
		return "API.Kr1C3b1ijJ";
	}

	@Override
	public Object getCurrentColumnData(Map<String, Object> originalData, Map<String, Object> currentData,
			String columnName, ColumnModel columnModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mockMyDataJsonString() {
		// TODO Auto-generated method stub
		return "{\n" + "  \"RelationGradeData\": [\n" + "    {\n" + "      \"PersonId\": \"F128205045\",\n"
				+ "      \"Relationship\": \"己身\",\n" + "      \"FullPersonName\": \"謝永強\",\n"
				+ "      \"RelationGrade\": \"-\",\n" + "      \"SpousePersonId\": \"S241368949\",\n"
				+ "      \"DeathDate\": \"\",\n" + "      \"FullSpouseName\": \"羅𩉘骫\",\n"
				+ "      \"BirthDate\": \"056/09/14\"\n" + "    },\n" + "    {\n"
				+ "      \"PersonId\": \"S241368949\",\n" + "      \"Relationship\": \"配偶\",\n"
				+ "      \"FullPersonName\": \"羅𩉘骫\",\n" + "      \"RelationGrade\": \"-\",\n"
				+ "      \"SpousePersonId\": \"F128205045\",\n" + "      \"DeathDate\": \"\",\n"
				+ "      \"FullSpouseName\": \"謝永強\",\n" + "      \"BirthDate\": \"041/08/23\"\n" + "    },\n"
				+ "    {\n" + "      \"PersonId\": \"9131570336\",\n" + "      \"Relationship\": \"養父\",\n"
				+ "      \"FullPersonName\": \"外未婚男\",\n" + "      \"RelationGrade\": \"1\",\n"
				+ "      \"SpousePersonId\": \"\",\n" + "      \"DeathDate\": \"\",\n"
				+ "      \"FullSpouseName\": \"\",\n" + "      \"BirthDate\": \"010/11/10\"\n" + "    },\n" + "    {\n"
				+ "      \"PersonId\": \"S231810258\",\n" + "      \"Relationship\": \"養母\",\n"
				+ "      \"FullPersonName\": \"蕭皏󽜧\",\n" + "      \"RelationGrade\": \"1\",\n"
				+ "      \"SpousePersonId\": \"\",\n" + "      \"DeathDate\": \"084/12/30\",\n"
				+ "      \"FullSpouseName\": \"\",\n" + "      \"BirthDate\": \"-01/02/12\"\n" + "    },\n" + "    {\n"
				+ "      \"PersonId\": \"S130744833\",\n" + "      \"Relationship\": \"父\",\n"
				+ "      \"FullPersonName\": \"韓鯲𥵫\",\n" + "      \"RelationGrade\": \"1\",\n"
				+ "      \"SpousePersonId\": \"\",\n" + "      \"DeathDate\": \"090/02/11\",\n"
				+ "      \"FullSpouseName\": \"\",\n" + "      \"BirthDate\": \"-01/08/04\"\n" + "    },\n" + "    {\n"
				+ "      \"PersonId\": \"S237659428\",\n" + "      \"Relationship\": \"母\",\n"
				+ "      \"FullPersonName\": \"方𢻕頜\",\n" + "      \"RelationGrade\": \"1\",\n"
				+ "      \"SpousePersonId\": \"\",\n" + "      \"DeathDate\": \"091/06/04\",\n"
				+ "      \"FullSpouseName\": \"\",\n" + "      \"BirthDate\": \"-01/01/20\"\n" + "    },\n" + "    {\n"
				+ "      \"PersonId\": \"S233394284\",\n" + "      \"Relationship\": \"養女\",\n"
				+ "      \"FullPersonName\": \"金󽜧󽜧\",\n" + "      \"RelationGrade\": \"1\",\n"
				+ "      \"SpousePersonId\": \"\",\n" + "      \"DeathDate\": \"\",\n"
				+ "      \"FullSpouseName\": \"\",\n" + "      \"BirthDate\": \"063/03/18\"\n" + "    },\n" + "    {\n"
				+ "      \"PersonId\": \"S238123016\",\n" + "      \"Relationship\": \"女\",\n"
				+ "      \"FullPersonName\": \"紀禒󽜧\",\n" + "      \"RelationGrade\": \"1\",\n"
				+ "      \"SpousePersonId\": \"\",\n" + "      \"DeathDate\": \"095/07/19\",\n"
				+ "      \"FullSpouseName\": \"\",\n" + "      \"BirthDate\": \"063/11/26\"\n" + "    },\n" + "    {\n"
				+ "      \"PersonId\": \"S130574671\",\n" + "      \"Relationship\": \"養子\",\n"
				+ "      \"FullPersonName\": \"薛𥓊𪀚\",\n" + "      \"RelationGrade\": \"1\",\n"
				+ "      \"SpousePersonId\": \"\",\n" + "      \"DeathDate\": \"\",\n"
				+ "      \"FullSpouseName\": \"\",\n" + "      \"BirthDate\": \"065/03/11\"\n" + "    },\n" + "    {\n"
				+ "      \"PersonId\": \"S130069764\",\n" + "      \"Relationship\": \"子\",\n"
				+ "      \"FullPersonName\": \"柳𣤻榵\",\n" + "      \"RelationGrade\": \"1\",\n"
				+ "      \"SpousePersonId\": \"\",\n" + "      \"DeathDate\": \"101/02/07\",\n"
				+ "      \"FullSpouseName\": \"\",\n" + "      \"BirthDate\": \"065/05/14\"\n" + "    }\n" + "  ],\n"
				+ "  \"SelfFullPersonNameChangeRecord\": [\n" + "    {\n"
				+ "      \"OriginalFullPersonName\": \"謝大口\",\n" + "      \"OperationSite\": \"屏東縣東港戶政事務所\",\n"
				+ "      \"ChangeDate\": \"107/01/19\",\n" + "      \"AfterFullPersonName\": \"謝永強\"\n" + "    },\n"
				+ "    {\n" + "      \"OriginalFullPersonName\": \"謝大大\",\n"
				+ "      \"OperationSite\": \"屏東縣東港戶政事務所\",\n" + "      \"ChangeDate\": \"106/01/19\",\n"
				+ "      \"AfterFullPersonName\": \"謝大口\"\n" + "    }\n" + "  ],\n"
				+ "  \"SelfPersonIdChangeRecord\": {\n" + "    \"OriginalPersonId\": \"9230000015\",\n"
				+ "    \"OperationSite\": \"新北市瑞芳戶政事務所\",\n" + "    \"AfterPersonId\": \"F128205045\",\n"
				+ "    \"ChangeDate\": \"102/10/16\"\n" + "  }\n" + "}";
	}

	@Override
	public String mockMyDataPdfFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mockMyDataCsvFile() {
		// TODO Auto-generated method stub
		return null;
	}

}
