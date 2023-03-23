package gov.kcg.kgo.viewModel.mydata.bo;

public class DpManifestBO {
	private String filename;
	private String digest;

	public DpManifestBO(String filename, String digest) {
		this.filename = filename;
		this.digest = digest;
	}

	public DpManifestBO() {
		super();
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}
}
