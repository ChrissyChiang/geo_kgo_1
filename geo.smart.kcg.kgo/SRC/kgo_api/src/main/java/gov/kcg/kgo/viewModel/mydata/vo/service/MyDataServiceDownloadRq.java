package gov.kcg.kgo.viewModel.mydata.vo.service;

import gov.kcg.kgo.viewModel.mydata.vo.service.base.MyDataServiceBaseRq;

public class MyDataServiceDownloadRq extends MyDataServiceBaseRq {

	private String clientId;
	
	private String clientIv;

    private String clientSecretKey;

    private String permissionTicket;

    private String secretKey;
    
    public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientIv() {
        return clientIv;
    }

    public void setClientIv(String clientIv) {
        this.clientIv = clientIv;
    }

    public String getClientSecretKey() {
        return clientSecretKey;
    }

    public void setClientSecretKey(String clientSecretKey) {
        this.clientSecretKey = clientSecretKey;
    }

    public String getPermissionTicket() {
        return permissionTicket;
    }

    public void setPermissionTicket(String permissionTicket) {
        this.permissionTicket = permissionTicket;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

}
