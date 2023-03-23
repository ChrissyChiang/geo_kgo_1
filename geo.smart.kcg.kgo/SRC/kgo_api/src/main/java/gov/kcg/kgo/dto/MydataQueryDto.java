package gov.kcg.kgo.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

import gov.kcg.kgo.model.KgoMydataResource;
import gov.kcg.kgo.model.KgoMydataService;
import gov.kcg.kgo.model.KgoMydataServiceResource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路申辦-表單設定-MYDAYA維護-初始畫面-CASESET_MYDATA資料集查詢")
@Entity
public class MydataQueryDto {

	/** MYDATA資料集代碼 */
	@Id
	@ApiModelProperty(notes = "MYDATA資料集代碼")
	private String myDataId;

	/** MYDATA資料集名稱 */
	@ApiModelProperty(notes = "MYDATA資料集名稱")
	private String myDataName;

	public MydataQueryDto(KgoMydataServiceResource msr, KgoMydataService ms, KgoMydataResource mr) {
		this.myDataId = mr.getMyDataId();
		this.myDataName = mr.getMyDataName();
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

}
