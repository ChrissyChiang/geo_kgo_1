package gov.kcg.kgo.viewModel.mydata.vo.ec.rq;

import gov.kcg.kgo.viewModel.mydata.vo.ec.MyDataBaseRq;

public class GetServiceDataRq extends MyDataBaseRq {

    private String permissionTicket;
    
    private String clientId;

    public String getPermissionTicket() {
        return permissionTicket;
    }

    public void setPermissionTicket(String permissionTicket) {
        this.permissionTicket = permissionTicket;
    }

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
    
}
