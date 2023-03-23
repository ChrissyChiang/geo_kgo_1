package gov.kcg.kgo.viewModel.backend.kgoLog.exportPdf.rs.bean;

import java.awt.Image;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

public class PdfBean extends BaseViewForm {

	private static final long serialVersionUID = 1L;

   /** 圖檔標題 */
	private String imageTitle;
	
	/**.圖檔 */
	private Image image;

	public String getImageTitle() {
		return imageTitle;
	}

	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
