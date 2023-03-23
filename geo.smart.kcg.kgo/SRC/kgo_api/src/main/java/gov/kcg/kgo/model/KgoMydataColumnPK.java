package gov.kcg.kgo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the KGO_MYDATA_COLUMN database table.
 * 
 */
@Embeddable
public class KgoMydataColumnPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "MyDataId", unique = true, nullable = false, length = 50)
	private String myDataId;

	@Column(name = "MyDataColumn", unique = true, nullable = false, length = 50)
	private String myDataColumn;

	public KgoMydataColumnPK() {
	}

	public KgoMydataColumnPK(String myDataId, String myDataColumn) {
		this.myDataId = myDataId;
		this.myDataColumn = myDataColumn;
	}

	public String getMyDataId() {
		return this.myDataId;
	}

	public void setMyDataId(String myDataId) {
		this.myDataId = myDataId;
	}

	public String getMyDataColumn() {
		return this.myDataColumn;
	}

	public void setMyDataColumn(String myDataColumn) {
		this.myDataColumn = myDataColumn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KgoMydataColumnPK)) {
			return false;
		}
		KgoMydataColumnPK castOther = (KgoMydataColumnPK) other;
		return this.myDataId.equals(castOther.myDataId) && this.myDataColumn.equals(castOther.myDataColumn);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.myDataId.hashCode();
		hash = hash * prime + this.myDataColumn.hashCode();

		return hash;
	}
}