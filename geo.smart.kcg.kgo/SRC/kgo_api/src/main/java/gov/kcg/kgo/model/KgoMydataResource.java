package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the KGO_MYDATA_RESOURCE database table.
 * 
 */
@Entity
@Table(name = "KGO_MYDATA_RESOURCE")
@NamedQuery(name = "KgoMydataResource.findAll", query = "SELECT k FROM KgoMydataResource k")
public class KgoMydataResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MyDataId", unique = true, nullable = false)
	private String myDataId;

	@Column(name = "DownloadMinutes")
	private Integer downloadMinutes;

	@Column(name = "IsEnable")
	private Boolean isEnable;

	@Column(name = "MyDataName")
	private String myDataName;

	@Column(name = "MyDataType")
	private String myDataType;

	@Column(name = "RsInfoFormat")
	private String rsInfoFormat;

	@Column(length = 30)
	private String SType;

	public KgoMydataResource() {
	}

	public String getMyDataId() {
		return this.myDataId;
	}

	public void setMyDataId(String myDataId) {
		this.myDataId = myDataId;
	}

	public Integer getDownloadMinutes() {
		return this.downloadMinutes;
	}

	public void setDownloadMinutes(Integer downloadMinutes) {
		this.downloadMinutes = downloadMinutes;
	}

	public Boolean getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getMyDataName() {
		return this.myDataName;
	}

	public void setMyDataName(String myDataName) {
		this.myDataName = myDataName;
	}

	public String getMyDataType() {
		return this.myDataType;
	}

	public void setMyDataType(String myDataType) {
		this.myDataType = myDataType;
	}

	public String getRsInfoFormat() {
		return this.rsInfoFormat;
	}

	public void setRsInfoFormat(String rsInfoFormat) {
		this.rsInfoFormat = rsInfoFormat;
	}

	public String getSType() {
		return this.SType;
	}

	public void setSType(String SType) {
		this.SType = SType;
	}

}