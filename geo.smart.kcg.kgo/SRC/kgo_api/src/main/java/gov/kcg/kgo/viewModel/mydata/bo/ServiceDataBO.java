package gov.kcg.kgo.viewModel.mydata.bo;

import java.util.ArrayList;
import java.util.List;

public class ServiceDataBO {
	private List<ServiceDataFileBO> jsonData;

	private List<ServiceDataFileBO> pdfFile;

	private List<ServiceDataFileBO> cvsFile;

	public ServiceDataBO() {
		this.jsonData = new ArrayList<ServiceDataFileBO>();
		this.pdfFile = new ArrayList<ServiceDataFileBO>();
		this.cvsFile = new ArrayList<ServiceDataFileBO>();
	}

	public List<ServiceDataFileBO> getJsonData() {
		return jsonData;
	}

	public void setJsonData(List<ServiceDataFileBO> jsonData) {
		this.jsonData = jsonData;
	}

	public List<ServiceDataFileBO> getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(List<ServiceDataFileBO> pdfFile) {
		this.pdfFile = pdfFile;
	}

	public List<ServiceDataFileBO> getCvsFile() {
		return cvsFile;
	}

	public void setCvsFile(List<ServiceDataFileBO> cvsFile) {
		this.cvsFile = cvsFile;
	}

}
