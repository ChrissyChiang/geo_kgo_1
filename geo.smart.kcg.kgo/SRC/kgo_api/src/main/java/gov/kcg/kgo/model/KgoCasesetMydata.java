package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the KGO_CASESET_MYDATA database table.
 * 
 */
@Entity
@Table(name="KGO_CASESET_MYDATA")
@NamedQuery(name="KgoCasesetMydata.findAll", query="SELECT k FROM KgoCasesetMydata k")
public class KgoCasesetMydata implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCasesetMydataPK id;

	@Column(name="MyDataClientId")
	private String myDataClientId;

	public KgoCasesetMydata() {
	}

	public KgoCasesetMydataPK getId() {
		return this.id;
	}

	public void setId(KgoCasesetMydataPK id) {
		this.id = id;
	}

	public String getMyDataClientId() {
		return this.myDataClientId;
	}

	public void setMyDataClientId(String myDataClientId) {
		this.myDataClientId = myDataClientId;
	}

}