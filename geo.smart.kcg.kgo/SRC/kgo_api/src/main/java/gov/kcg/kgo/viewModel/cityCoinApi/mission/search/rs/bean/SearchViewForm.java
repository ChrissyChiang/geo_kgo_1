package gov.kcg.kgo.viewModel.cityCoinApi.mission.search.rs.bean;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import gov.kcg.kgo.viewModel.cityCoinApi.base.response.CityCoinBaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 城市幣API-取得單筆城市幣任務資料 View Form
 */
@ApiModel(description = "城市幣API-取得單筆城市幣任務資料 View Form")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchViewForm extends CityCoinBaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "流水號")
	private Integer seq;

	@ApiModelProperty(value = "主辦單位Seq")
	private Integer organizerSeq;

	@ApiModelProperty(value = "任務代碼")
	private String appKey;

	private String systemFrom;
	private String missionType;
	private String missionQrcode;
	private String missionUrl;
	private Object organizerSeqNavigation;
	private Object missionCompleted;

	@ApiModelProperty(value = "任務標題	")
	private String title;

	@ApiModelProperty(value = "任務內容")
	private String content;

	@ApiModelProperty(value = "線上閱讀文章連結Url")
	private String ReadingUrl;

	@ApiModelProperty(value = "問卷Url")
	private String QuestionnaireUrl;

	@ApiModelProperty(value = "發幣方式（F:First 先到先得、L:Lottery 抽獎）")
	private String sendMode;

	@ApiModelProperty(value = "獎勵城市幣金額")
	private BigDecimal rewardAmount;

	@ApiModelProperty(value = "獎勵名額")
	private Integer rewardCount;

	@ApiModelProperty(value = "總發幣金額")
	private BigDecimal totalAmount;

	@ApiModelProperty(value = "目標對象最小出生日期（格式yyyy/MM/dd）")
	private String targetMinBirthday;

	@ApiModelProperty(value = "目標對象最大出生日期（格式yyyy/MM/dd）")
	private String targetMaxBirthday;

	@ApiModelProperty(value = "目標對象行政區代碼(若是多筆，以\",\"隔開）")
	private String targetDistrictCode;

	@ApiModelProperty(value = "目標對象性別（0:女、1:男）")
	private String gender;

	@ApiModelProperty(value = "任務起始時間")
	private String startTime;

	@ApiModelProperty(value = "任務結束時間")
	private String endTime;

	@ApiModelProperty(value = "建立人員會員編號")
	private String creator;

	@ApiModelProperty(value = "建立時間")
	private String createTime;

	@ApiModelProperty(value = "最後修改人員會員編號")
	private String lastModifier;

	@ApiModelProperty(value = "最後修改時間")
	private String lastModifiedTime;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getOrganizerSeq() {
		return organizerSeq;
	}

	public void setOrganizerSeq(Integer organizerSeq) {
		this.organizerSeq = organizerSeq;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSystemFrom() {
		return systemFrom;
	}

	public void setSystemFrom(String systemFrom) {
		this.systemFrom = systemFrom;
	}

	public String getMissionType() {
		return missionType;
	}

	public void setMissionType(String missionType) {
		this.missionType = missionType;
	}

	public String getMissionQrcode() {
		return missionQrcode;
	}

	public void setMissionQrcode(String missionQrcode) {
		this.missionQrcode = missionQrcode;
	}

	public String getMissionUrl() {
		return missionUrl;
	}

	public void setMissionUrl(String missionUrl) {
		this.missionUrl = missionUrl;
	}

	public Object getOrganizerSeqNavigation() {
		return organizerSeqNavigation;
	}

	public void setOrganizerSeqNavigation(Object organizerSeqNavigation) {
		this.organizerSeqNavigation = organizerSeqNavigation;
	}

	public Object getMissionCompleted() {
		return missionCompleted;
	}

	public void setMissionCompleted(Object missionCompleted) {
		this.missionCompleted = missionCompleted;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReadingUrl() {
		return ReadingUrl;
	}

	public void setReadingUrl(String readingUrl) {
		ReadingUrl = readingUrl;
	}

	public String getQuestionnaireUrl() {
		return QuestionnaireUrl;
	}

	public void setQuestionnaireUrl(String questionnaireUrl) {
		QuestionnaireUrl = questionnaireUrl;
	}

	public String getSendMode() {
		return sendMode;
	}

	public void setSendMode(String sendMode) {
		this.sendMode = sendMode;
	}

	public BigDecimal getRewardAmount() {
		return rewardAmount;
	}

	public void setRewardAmount(BigDecimal rewardAmount) {
		this.rewardAmount = rewardAmount;
	}

	public Integer getRewardCount() {
		return rewardCount;
	}

	public void setRewardCount(Integer rewardCount) {
		this.rewardCount = rewardCount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTargetMinBirthday() {
		return targetMinBirthday;
	}

	public void setTargetMinBirthday(String targetMinBirthday) {
		this.targetMinBirthday = targetMinBirthday;
	}

	public String getTargetMaxBirthday() {
		return targetMaxBirthday;
	}

	public void setTargetMaxBirthday(String targetMaxBirthday) {
		this.targetMaxBirthday = targetMaxBirthday;
	}

	public String getTargetDistrictCode() {
		return targetDistrictCode;
	}

	public void setTargetDistrictCode(String targetDistrictCode) {
		this.targetDistrictCode = targetDistrictCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

}
