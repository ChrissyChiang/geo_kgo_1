package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import gov.kcg.kgo.model.KgoKcd;
import gov.kcg.kgo.model.KgoZip;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "段小段")
@Entity
public class KgoKcdDto {

	/**
	 * 段小段代碼
	 */
	@Id
	@ApiModelProperty(notes = "段小段代碼")
	@Column(name = "KCD")
	private String kcd;

	/**
	 * 段小段名稱
	 */
	@ApiModelProperty(notes = "段小段名稱")
	@Column(name = "KCNT")
	private String kcnt;

	@ApiModelProperty(notes = "城市資料平台段小段鄉鎮區")
	@Column(name = "KRMK")
	private String krmk;

	@ApiModelProperty(notes = "鄉鎮區")
	@Column(name = "ZIP")
	private String zip;

	public KgoKcdDto(KgoKcd u, KgoZip o) {
		this.kcd = u.getKcd();
		this.kcnt = u.getKcnt();
		this.krmk = u.getKrmk();
		this.zip = o.getZip();

	}

	public String getKcd() {
		return kcd;
	}

	public void setKcd(String kcd) {
		this.kcd = kcd;
	}

	public String getKcnt() {
		return kcnt;
	}

	public void setKcnt(String kcnt) {
		this.kcnt = kcnt;
	}

	public String getKrmk() {
		return krmk;
	}

	public void setKrmk(String krmk) {
		this.krmk = krmk;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
