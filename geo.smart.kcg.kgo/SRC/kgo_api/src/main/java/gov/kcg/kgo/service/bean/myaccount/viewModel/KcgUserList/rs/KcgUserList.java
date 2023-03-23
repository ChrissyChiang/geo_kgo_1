package gov.kcg.kgo.service.bean.myaccount.viewModel.KcgUserList.rs;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KcgUserList implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("UserList")
	private List<KcgUserInfo> userList;

    public List<KcgUserInfo> getUserList() {
        return userList;
    }

    public void setUserList(List<KcgUserInfo> userList) {
        this.userList = userList;
    }
}
