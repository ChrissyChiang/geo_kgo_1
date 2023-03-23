package gov.kcg.kgo.viewModel.mydata.bo;

public class ServiceDataFileBO {

	private String fileName;

	// Json 為檔案內容, PDF、CSV file Base64 String
	private String fileData;

	public ServiceDataFileBO(String fileName, String fileData) {
		this.fileName = fileName;
		this.fileData = fileData;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileData() {
		return fileData;
	}

	public void setFileData(String fileData) {
		this.fileData = fileData;
	}

}
