package gov.kcg.kgo.dto;

import gov.kcg.kgo.model.KgoCasesetForm;
import gov.kcg.kgo.model.KgoCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@ApiModel(description = "申辦說明頁-書表下載資料集合")
@Entity
public class KgoCasesetFormQueryDto {

	/** 序號 */
	@Id
	@ApiModelProperty(notes = "序號")
	@Column(name = "Seq")
	private Integer seq;

	/** 分類 */
	@ApiModelProperty(notes = "分類")
	@Column(name = "Type")
	private String type;

	/** 分類名稱 */
	@ApiModelProperty(notes = "分類名稱")
	@Column(name = "TypeName")
	private String typeName;

	/** 標題 */
	@ApiModelProperty(notes = "標題")
	@Column(name = "Title")
	private String title;

    /** 附件 */
    @ApiModelProperty(notes = "附件")
    @Column(name = "FileName")
    private String fileName;

	public KgoCasesetFormQueryDto(KgoCasesetForm cf, KgoCode c) {
		this.seq = cf.getSeq();
		this.type = cf.getType();
		this.typeName = c.getCodeName();
		this.title = cf.getTitle();
        this.fileName = cf.getFileName();
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
