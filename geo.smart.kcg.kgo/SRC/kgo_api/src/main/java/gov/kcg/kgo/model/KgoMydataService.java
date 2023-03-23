package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the KGO_MYDATA_SERVICE database table.
 * 
 */
@Entity
@Table(name="KGO_MYDATA_SERVICE")
@NamedQuery(name="KgoMydataService.findAll", query="SELECT k FROM KgoMydataService k")
public class KgoMydataService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ClientId", unique=true, nullable=false)
	private String clientId;

	@Column(name="CbcIv")
	private String cbcIv;

	@Column(name="ClientSecret")
	private String clientSecret;

	@Column(name="ReturnUrl")
	private String returnUrl;

	@Column(name="ServiceName")
	private String serviceName;

	@Column(name="SpApiUrl")
	private String spApiUrl;

	public KgoMydataService() {
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getCbcIv() {
		return cbcIv;
	}

	public void setCbcIv(String cbcIv) {
		this.cbcIv = cbcIv;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSpApiUrl() {
		return spApiUrl;
	}

	public void setSpApiUrl(String spApiUrl) {
		this.spApiUrl = spApiUrl;
	}

	@Override
	public String toString() {
		return "KgoMydataService{" +
				"clientId='" + clientId + '\'' +
				", cbcIv='" + cbcIv + '\'' +
				", clientSecret='" + clientSecret + '\'' +
				", returnUrl='" + returnUrl + '\'' +
				", serviceName='" + serviceName + '\'' +
				", spApiUrl='" + spApiUrl + '\'' +
				'}';
	}
}