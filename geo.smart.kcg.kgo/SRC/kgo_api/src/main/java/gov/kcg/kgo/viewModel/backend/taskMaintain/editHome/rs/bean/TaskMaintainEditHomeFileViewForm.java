package gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean;

import java.io.Serializable;

public class TaskMaintainEditHomeFileViewForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String key;

	private String name;

	private String base64Str = "";

	public TaskMaintainEditHomeFileViewForm() {

	}

	public TaskMaintainEditHomeFileViewForm(String key, String name) {
		this.key = key;
		this.name = name;
		this.base64Str = "";
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBase64Str() {
		return base64Str;
	}

	public void setBase64Str(String base64Str) {
		this.base64Str = base64Str;
	}

}
