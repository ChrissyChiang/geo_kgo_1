package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.common.fileUpload.rs.UploadFileActionRs;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * CommonService.
 */
public interface CommonService {

    /**
     * 檔案上傳
     *
     * @param file
     * @param uploadBasePath (上傳至Server的基本路徑)
     * @return
     */
    public UploadFileActionRs uploadFileAction(MultipartFile file, String uploadBasePath);

    /**
     * 檔案上傳
     *
     * @param file      檔案
     * @param path      檔案路徑
     * @param fileName  檔案名稱
     * @param sizeLimit 檔案大小限制
     * @return
     */
    UploadFileActionRs uploadFileAction(MultipartFile file, String path, String fileName, long sizeLimit, List<String> extensionList);

    /**
     * 檔案下載
     *
     * @param file
     */
    public void downloadFileAction(File file);

    /**
     * 檔案下載
     *
     * @param fileName
     * @param fileBase64Str
     * @param mimeType
     */
    public void downloadFileAction(String fileName, String fileBase64Str, String mimeType);

    /**
     * 取得申辦案件說明附件上傳路徑
     *
     * @param caseSetId
     * @param applyType
     * @return
     */
    public String getAttachmentUploadBasePath(String caseSetId, String applyType);

    /**
     * 取得書表下載功能中的檔案上傳路徑
     *
     * @param caseSetId
     * @return
     */
    String getFormDownloadUploadBasePath(String caseSetId);

    /**
     * Geo 20221009 add_Jim
     * Get file hash
     *
     * @param filePath
     * @param encodingType
     */
    String getFileSHAEncoding(String filePath, String encodingType) throws Exception;

    /**
     * Geo 20221009 add_Jim
     * Get String hash
     *
     * @param message
     * @param encodingType
     */
    String getStringSHAEncoding(String message, String encodingType) throws Exception;

}
