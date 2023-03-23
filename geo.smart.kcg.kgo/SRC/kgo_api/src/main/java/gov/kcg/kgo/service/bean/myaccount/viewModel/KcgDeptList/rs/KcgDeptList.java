package gov.kcg.kgo.service.bean.myaccount.viewModel.KcgDeptList.rs;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KcgDeptList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("DeptList")
	private List<KcgDeptInfo> deptList;

	public List<KcgDeptInfo> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<KcgDeptInfo> deptList) {
		this.deptList = deptList;
	}

}
