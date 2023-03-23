package gov.kcg.kgo.service.impl;

import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.service.CommonService;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.common.fileUpload.rs.UploadFileActionRs;
import gov.kcg.kgo.viewModel.backend.common.fileUpload.rs.bean.UploadFileActionViewForm;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service("CommonService")
public class CommonServiceImpl implements CommonService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private HttpServletResponse response;

    @Value("${attachment.upload.path}")
    private String attachmentUploadBasePath;

    @Value("${form.download.upload.path}")
    private String formDownloadUploadBasePath;

    /**
     * 檔案上傳
     *
     * @param file           檔案
     * @param uploadBasePath 上傳路徑
     * @return
     */
    public UploadFileActionRs uploadFileAction(MultipartFile file, String uploadBasePath) {
        UploadFileActionRs rs = new UploadFileActionRs();
        UploadFileActionViewForm viewForm = new UploadFileActionViewForm();
        try {
            if (!file.isEmpty()) {

                long fileSize = file.getSize() / 1024;
                String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
                Path basePath = Files.createDirectories(Paths.get(uploadBasePath)).toAbsolutePath().normalize();
                Path targetLocation = basePath.resolve(fileName).toAbsolutePath().normalize();

                // 確保寫入的檔案位置在現在的資料夾中
                if (!targetLocation.startsWith(basePath)) {
                    LOGGER.error("uploadBasePath:" + uploadBasePath + "\n");
                    LOGGER.error("targetLocation:" + targetLocation.toString() + "\n");
                    throw new KgoApiException(new ErrorResult(KgoCommonApiError.WRONG_TARGET_PATH));
                }

                // Copy file to the target location (Replacing existing file with the same name)
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                viewForm.setFileName(fileName);
                rs.setData(viewForm);
            }
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoCommonApiError.FAIL_TO_UPLOAD.getErrorMsgKey()); // TODO:
            throw new KgoApiException("uploadFileAction error " + e.getMessage(), e);
        }
        return rs;
    }

    /**
     * 檔案上傳
     *
     * @param file      檔案
     * @param path      檔案路徑
     * @param fileName  檔案名稱
     * @param sizeLimit 檔案大小限制
     * @return
     */
    public UploadFileActionRs uploadFileAction(MultipartFile file, String path, String fileName, long sizeLimit, List<String> extensionList) {
        UploadFileActionRs rs = new UploadFileActionRs();

        UploadFileActionViewForm viewForm = new UploadFileActionViewForm();

        try {
            if (!file.isEmpty()) {
                long fileSize = file.getSize() / 1048576;

                if (fileSize >= sizeLimit)
                    throw new KgoApiException("File size is exceeds limit");

                String extension = FilenameUtils.getExtension(file.getOriginalFilename());

                if (!extensionList.stream().anyMatch(e -> e.equals(extension)))
                    throw new KgoApiException(extension + " is Illegal file extension");

                Path basePath = Files.createDirectories(Paths.get(path)).toAbsolutePath().normalize();

                Path targetLocation = basePath.resolve(fileName + "." + extension).toAbsolutePath().normalize();

                // 確保寫入的檔案位置在現在的資料夾中
                if (!targetLocation.startsWith(basePath)) {
                    throw new KgoApiException(new ErrorResult(KgoCommonApiError.WRONG_TARGET_PATH));
                }

                // Copy file to the target location (Replacing existing file with the same name)
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                viewForm.setFileName(fileName);
                rs.setData(viewForm);
            }
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoCommonApiError.FAIL_TO_UPLOAD.getErrorMsgKey()); // TODO:
            throw new KgoApiException("uploadFileAction error " + e.getMessage(), e);
        }
        return rs;
    }

    /**
     * 檔案下載
     *
     * @param file
     */
    @Override
    public void downloadFileAction(File file) {
        try {
            // encode the file name.
            final String name = file.getName();
            boolean isPic = name.contains(".png")  || name.contains(".jpg")|| name.contains(".jpeg");
            LOGGER.warn("name=={}", name);
            String fileName = isPic
                ?URLEncoder.encode(name)
                :URLEncoder.encode(name, "UTF-8");
            // set the ContentType.
            String mimeType = URLConnection.guessContentTypeFromName(name);
            if (isPic || mimeType == null) {
                // unknown mimetype so set the mimetype to application/octet-stream
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            // set the Header.
//            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"" + "; filename*=UTF-8" + fileName);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(inputStream, response.getOutputStream());

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoCommonApiError.FAIL_TO_DOWNLOAD.getErrorMsgKey());
            throw new KgoApiException("downloadFile error " + e.getMessage(), e);
        }
    }

    /**
     * 檔案下載
     *
     * @param fileName
     * @param fileBase64Str
     * @param mimeType
     */
    @Override
    public void downloadFileAction(String fileName, String fileBase64Str, String mimeType) {
        try {
            if (StringUtils.isBlank(mimeType)) {
                // unknown mimetype so set the mimetype to application/octet-stream
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            byte[] bytes = Base64.getDecoder().decode(fileBase64Str);

            // set the Header.
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"" + "; filename*=UTF-8" + fileName);
            response.setContentLength((int) bytes.length);

            InputStream inputStream = new ByteArrayInputStream(bytes);
            FileCopyUtils.copy(inputStream, response.getOutputStream());

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoCommonApiError.FAIL_TO_DOWNLOAD.getErrorMsgKey());
            throw new KgoApiException("downloadFile error " + e.getMessage(), e);

        }
    }

    /**
     * 取得申請說明附件上傳路徑
     *
     * @param caseSetId
     * @param applyType
     * @return
     */
    @Override
    public String getAttachmentUploadBasePath(String caseSetId, String applyType) {
        String path = StringUtils.EMPTY;
        try {
            // 取得申請說明附件上傳路徑
            path = KgoUtil.getAttachmentUploadBasePath(caseSetId, applyType);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoCommonApiError.WRONG_TARGET_PATH.getErrorMsgKey());
            throw new KgoApiException("getAttachmentUploadBasePath error " + e.getMessage(), e);
        }
        return path;
    }

    /**
     * 取得書表下載功能中的檔案上傳路徑
     *
     * @param caseSetId
     * @return
     */
    @Override
    public String getFormDownloadUploadBasePath(String caseSetId) {
        String path = StringUtils.EMPTY;
        try {
            // 取得書表下載功能中的檔案上傳路徑
            path = KgoUtil.getFormDownloadUploadBasePath(caseSetId);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoCommonApiError.WRONG_TARGET_PATH.getErrorMsgKey());
            throw new KgoApiException("getAttachmentUploadBasePath error " + e.getMessage(), e);
        }
        return path;
    }

    @Override
    public String getFileSHAEncoding(String filePath, String encodingType) throws Exception {
        File file = new File(filePath);
        MessageDigest md = MessageDigest.getInstance(encodingType);
        try (FileInputStream fis = new FileInputStream(file);) {
            byte[] ba = new byte[1024];
            int len = 0;
            while ((len = fis.read(ba)) != -1) {
                md.update(ba, 0, len);
            }
        }
        byte[] digest = md.digest();
        final StringBuffer buffer = new StringBuffer();
        for (byte b : digest) {
            final int value = Byte.toUnsignedInt(b);
            buffer.append(value < 16 ? "0" : "");
            buffer.append(Integer.toHexString(value));
        }
        return buffer.toString();
    }

    @Override
    public String getStringSHAEncoding(String message, String encodingType) throws Exception {
        final StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance(encodingType);
            md.update(message.getBytes());
            byte[] digest = md.digest();
            for (final byte b : digest) {
                final int value = Byte.toUnsignedInt(b);
                buffer.append(value < 16 ? "0" : "");
                buffer.append(Integer.toHexString(value));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return buffer.toString();
    }

}
