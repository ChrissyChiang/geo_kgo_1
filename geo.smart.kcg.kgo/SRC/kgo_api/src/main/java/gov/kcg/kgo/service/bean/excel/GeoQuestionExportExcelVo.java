package gov.kcg.kgo.service.bean.excel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireAnswerDetail;
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetDetail;
import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireCaseSetTopicModel;

import java.util.List;
import java.util.Map;

/**
 * GEO 20211030 add
 * 問卷結果 ExcelVo.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoQuestionExportExcelVo {

	/** 民眾姓名 */
	private String apply_name;

	/** 民眾email */
	private String apply_email;

	/** 民眾手機 */
	private String apply_phone;

	/** 填寫時間 */
	private String apply_date;

	/** 作答清單 */
	private List<GeoKgoQuestionnaireAnswerDetail> answerList;


	public GeoQuestionExportExcelVo(){ }

	public String getApply_name() {
		return apply_name;
	}

	public void setApply_name(String apply_name) {
		this.apply_name = apply_name;
	}

	public String getApply_email() {
		return apply_email;
	}

	public void setApply_email(String apply_email) {
		this.apply_email = apply_email;
	}

	public String getApply_phone() {
		return apply_phone;
	}

	public void setApply_phone(String apply_phone) {
		this.apply_phone = apply_phone;
	}

	public String getApply_date() {
		return apply_date;
	}

	public void setApply_date(String apply_date) {
		this.apply_date = apply_date;
	}

	public List<GeoKgoQuestionnaireAnswerDetail> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<GeoKgoQuestionnaireAnswerDetail> answerList) {
		this.answerList = answerList;
	}
}
