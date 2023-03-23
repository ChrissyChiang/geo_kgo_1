package gov.kcg.kgo.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import gov.kcg.kgo.enums.common.ExcelType;

/**
 * The Class ExcelUtil.
 */
public class ExcelUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * 設置回應表頭、ContentType、檔案名稱.
	 *
	 * @param response the response
	 * @param fileName the file name
	 * @throws UnsupportedEncodingException
	 */
	public static void setExcelResponse(HttpServletResponse response, ExcelType excelType, String fileName)
			throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(fileName)) {
			// 預設檔案名稱
			fileName = DateUtil.dateToString(new Date(), DateUtil.PATTEN_FULL_TIME_NO_HYPHEN);
		}
		// excle檔案名稱 ex:fileName + ".xlsx"
		fileName = fileName + excelType.getExcelType();

		// encoding file name
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("file name encoding error！", e);
			throw e;
		}
		// setHeader
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"" + "; filename*=UTF-8‘‘" + fileName);
		// setContentType
		response.setContentType(excelType.getContentType());
	}
	
	/**
	 * 讀取下載範本
	 * 
	 * @param classPath
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static Workbook getTemplFile(String classPath) throws EncryptedDocumentException, InvalidFormatException, IOException {
		Workbook workbook = WorkbookFactory.create(new ClassPathResource(classPath).getInputStream());
		return workbook;
	}

	/**
	 * 設置檔案名稱.
	 *
	 * @param excelType
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String setExcelName(ExcelType excelType, String fileName) throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(fileName)) {
			// 預設檔案名稱
			fileName = DateUtil.dateToString(new Date(), DateUtil.PATTEN_FULL_TIME_NO_HYPHEN);
		}
		// excle檔案名稱 ex:fileName + ".xlsx"
		fileName = fileName + excelType.getExcelType();

		return fileName;
	}

	/**
	 * Excel套版並直接下載
	 * 
	 * @param workbook
	 * @param response
	 * @param context
	 * @throws IOException
	 */
	public static void setTempAndDownload(Workbook workbook, HttpServletResponse response, Context context)
			throws IOException {
		PoiTransformer transformer = PoiTransformer.createTransformer(workbook);
		OutputStream os = response.getOutputStream();
		transformer.setOutputStream(os);
		JxlsHelper.getInstance().processTemplate(context, transformer);
		os.flush();
		os.close();
	}
	
	/**
	 * Excel套版並輸出byte[]
	 * 
	 * @param workbook
	 * @param context
	 * @return
	 * @throws IOException
	 */
	public static byte[] setTempAndByteRes(Workbook workbook, Context context) throws IOException {

		PoiTransformer transformer = PoiTransformer.createTransformer(workbook);

		// 輸出excel套版至response.
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		transformer.setOutputStream(os);
		JxlsHelper.getInstance().processTemplate(context, transformer);

		return os.toByteArray();

	}
}