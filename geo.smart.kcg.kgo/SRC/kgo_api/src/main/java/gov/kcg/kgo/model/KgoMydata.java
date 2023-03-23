package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the KGO_MYDATA database table.
 * 
 */
@Entity
@Table(name="KGO_MYDATA")
@NamedQuery(name="KgoMydata.findAll", query="SELECT k FROM KgoMydata k")
public class KgoMydata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MyDataId", unique=true, nullable=false, length=50)
	private String myDataId;

	@Column(name="MyDataName")
	private String myDataName;

	@Column(name="Type", length=30)
	private String type;

	public KgoMydata() {
	}

	public String getMyDataId() {
		return this.myDataId;
	}

	public void setMyDataId(String myDataId) {
		this.myDataId = myDataId;
	}

	public String getMyDataName() {
		return this.myDataName;
	}

	public void setMyDataName(String myDataName) {
		this.myDataName = myDataName;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}