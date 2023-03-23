package gov.kcg.kgo.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "案件進度查詢-補正畫面")
@Entity
public class CaseCorrectDataDto {

	@Id
	private Integer rowNumber;

	private Integer kcgOrderNum;

	private Integer kccOrderNum;

	private Integer kcccOrderNum;

	private Integer kcccRow;

	private Integer kcgGroupSeq;

	private String kcgMemo;

	private String kccColumnId;

	private String kccColumnName;

	private String kccColumnType;

	private Integer kccVersion;

	private Integer kccLength;
	
	private String kcccVGroup;

	private Integer kccIsMustKey;

	private String kccColumnValue;

	private String kcccCColumnId;

	private String kcccColumnType;

	private Integer kcccLength;

	private String kcccFText;

	private String kcccBText;

	private Integer kcccIsMustKey;

	private String kcccColumnValue;

	private String kcccPColumnId;

	private String kcdColumnValue;

	private Integer kcdIsCorrect;

	private String kcdCorrectMemo;

	private String kcd2ColumnValue;

	private Integer kcd2IsCorrect;

	private String kcd2CorrectMemo;

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Integer getKcgOrderNum() {
		return kcgOrderNum;
	}

	public void setKcgOrderNum(Integer kcgOrderNum) {
		this.kcgOrderNum = kcgOrderNum;
	}

	public Integer getKccOrderNum() {
		return kccOrderNum;
	}

	public void setKccOrderNum(Integer kccOrderNum) {
		this.kccOrderNum = kccOrderNum;
	}

	public Integer getKcccOrderNum() {
		return kcccOrderNum;
	}

	public void setKcccOrderNum(Integer kcccOrderNum) {
		this.kcccOrderNum = kcccOrderNum;
	}

	public Integer getKcgGroupSeq() {
		return kcgGroupSeq;
	}

	public void setKcgGroupSeq(Integer kcgGroupSeq) {
		this.kcgGroupSeq = kcgGroupSeq;
	}

	public String getKcgMemo() {
		return kcgMemo;
	}

	public void setKcgMemo(String kcgMemo) {
		this.kcgMemo = kcgMemo;
	}

	public String getKccColumnId() {
		return kccColumnId;
	}

	public void setKccColumnId(String kccColumnId) {
		this.kccColumnId = kccColumnId;
	}

	public String getKccColumnName() {
		return kccColumnName;
	}

	public void setKccColumnName(String kccColumnName) {
		this.kccColumnName = kccColumnName;
	}

	public String getKccColumnType() {
		return kccColumnType;
	}

	public void setKccColumnType(String kccColumnType) {
		this.kccColumnType = kccColumnType;
	}

	public Integer getKccVersion() {
		return kccVersion;
	}

	public void setKccVersion(Integer kccVersion) {
		this.kccVersion = kccVersion;
	}

	public Integer getKccLength() {
		return kccLength;
	}

	public void setKccLength(Integer kccLength) {
		this.kccLength = kccLength;
	}

	public Integer getKccIsMustKey() {
		return kccIsMustKey;
	}

	public void setKccIsMustKey(Integer kccIsMustKey) {
		this.kccIsMustKey = kccIsMustKey;
	}

	public String getKcccCColumnId() {
		return kcccCColumnId;
	}

	public void setKcccCColumnId(String kcccCColumnId) {
		this.kcccCColumnId = kcccCColumnId;
	}

	public String getKcccColumnType() {
		return kcccColumnType;
	}

	public void setKcccColumnType(String kcccColumnType) {
		this.kcccColumnType = kcccColumnType;
	}

	public Integer getKcccLength() {
		return kcccLength;
	}

	public void setKcccLength(Integer kcccLength) {
		this.kcccLength = kcccLength;
	}

	public Integer getKcccIsMustKey() {
		return kcccIsMustKey;
	}

	public void setKcccIsMustKey(Integer kcccIsMustKey) {
		this.kcccIsMustKey = kcccIsMustKey;
	}

	public String getKcdColumnValue() {
		return kcdColumnValue;
	}

	public void setKcdColumnValue(String kcdColumnValue) {
		this.kcdColumnValue = kcdColumnValue;
	}

	public Integer getKcdIsCorrect() {
		return kcdIsCorrect;
	}

	public void setKcdIsCorrect(Integer kcdIsCorrect) {
		this.kcdIsCorrect = kcdIsCorrect;
	}

	public String getKcdCorrectMemo() {
		return kcdCorrectMemo;
	}

	public void setKcdCorrectMemo(String kcdCorrectMemo) {
		this.kcdCorrectMemo = kcdCorrectMemo;
	}

	public String getKcd2ColumnValue() {
		return kcd2ColumnValue;
	}

	public void setKcd2ColumnValue(String kcd2ColumnValue) {
		this.kcd2ColumnValue = kcd2ColumnValue;
	}

	public Integer getKcd2IsCorrect() {
		return kcd2IsCorrect;
	}

	public void setKcd2IsCorrect(Integer kcd2IsCorrect) {
		this.kcd2IsCorrect = kcd2IsCorrect;
	}

	public String getKcd2CorrectMemo() {
		return kcd2CorrectMemo;
	}

	public void setKcd2CorrectMemo(String kcd2CorrectMemo) {
		this.kcd2CorrectMemo = kcd2CorrectMemo;
	}

	public String getKccColumnValue() {
		return kccColumnValue;
	}

	public void setKccColumnValue(String kccColumnValue) {
		this.kccColumnValue = kccColumnValue;
	}

	public String getKcccColumnValue() {
		return kcccColumnValue;
	}

	public void setKcccColumnValue(String kcccColumnValue) {
		this.kcccColumnValue = kcccColumnValue;
	}

	public String getKcccPColumnId() {
		return kcccPColumnId;
	}

	public void setKcccPColumnId(String kcccPColumnId) {
		this.kcccPColumnId = kcccPColumnId;
	}

	public Integer getKcccRow() {
		return kcccRow;
	}

	public void setKcccRow(Integer kcccRow) {
		this.kcccRow = kcccRow;
	}

	public String getKcccFText() {
		return kcccFText;
	}

	public void setKcccFText(String kcccFText) {
		this.kcccFText = kcccFText;
	}

	public String getKcccBText() {
		return kcccBText;
	}

	public void setKcccBText(String kcccBText) {
		this.kcccBText = kcccBText;
	}

	public String getKcccVGroup() {
		return kcccVGroup;
	}

	public void setKcccVGroup(String kcccVGroup) {
		this.kcccVGroup = kcccVGroup;
	}

	@Override
	public String toString() {
		return "CaseCorrectDataDto{" + "rowNumber=" + rowNumber + ", kcgOrderNum=" + kcgOrderNum + ", kccOrderNum=" + kccOrderNum + ", kcccRow=" + kcccRow + ", kcccOrderNum=" + kcccOrderNum
				+ ", kcgGroupSeq=" + kcgGroupSeq + ", kcgMemo='" + kcgMemo + '\'' + ", kccColumnId='" + kccColumnId + '\'' + ", kccColumnName='" + kccColumnName + '\'' + ", kccColumnType='"
				+ kccColumnType + '\'' + ", kccVersion=" + kccVersion + ", kccLength=" + kccLength + ", kccIsMustKey=" + kccIsMustKey + ", kccColumnValue='" + kccColumnValue + '\''
				+ ", kcccCColumnId='" + kcccCColumnId + '\'' + ", kcccColumnType='" + kcccColumnType + '\'' + ", kcccLength=" + kcccLength + ", kcccFText=" + kcccFText + ", kcccBText=" + kcccBText
				+ ", kcccIsMustKey=" + kcccIsMustKey + ", kcccColumnValue='" + kcccColumnValue + '\'' + ", kcccPColumnId='" + kcccPColumnId + '\'' + ", kcdColumnValue='" + kcdColumnValue + '\''
				+ ", kcdIsCorrect=" + kcdIsCorrect + ", kcdCorrectMemo='" + kcdCorrectMemo + '\'' + ", kcd2ColumnValue='" + kcd2ColumnValue + '\'' + ", kcd2IsCorrect=" + kcd2IsCorrect
				+ ", kcd2CorrectMemo='" + kcd2CorrectMemo + '\'' + ", kcccVGroup='" + kcccVGroup + '\'' + '}';
	}
	
	
}