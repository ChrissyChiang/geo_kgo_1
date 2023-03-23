package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import gov.kcg.kgo.model.KgoCasesetMydata;
import gov.kcg.kgo.model.KgoMydata;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路申辦-表單設定-MYDAYA維護-初始畫面-CASESET_MYDATA資料集查詢")
@Entity
public class CasesetMydataQueryDto {

	/** MYDATA資料集代碼 */
	@Id
	@ApiModelProperty(notes = "MYDATA資料集代碼")
	@Column(name = "MYDATA_ID")
	private String myDataId;

	/** MYDATA資料集名稱 */
	@ApiModelProperty(notes = "MYDATA資料集名稱")
	@Column(name = "MYDATA_NAME")
	private String myDataName;

	/** 來源代碼 */
	@ApiModelProperty(notes = "來源代碼")
	@Column(name = "S_TYPE")
	private String sType;

	/** 欄位使用數 */
	@ApiModelProperty(notes = "欄位使用數")
	@Column(name = "COUNT")
	private Integer count;

	public CasesetMydataQueryDto() {
	}

	public String getMyDataId() {
		return myDataId;
	}

	public void setMyDataId(String myDataId) {
		this.myDataId = myDataId;
	}

	public String getMyDataName() {
		return myDataName;
	}

	public void setMyDataName(String myDataName) {
		this.myDataName = myDataName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}

}
