package gov.kcg.kgo.geocontroller.backend;

import gov.kcg.kgo.geoentity.GeoKgoCaseMainZipHash;
import gov.kcg.kgo.georepository.GeoKgoCaseMainZipHashRepository;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.repository.KgoCaseMainRepository;
import gov.kcg.kgo.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * GEO 20221010 add_Jim
 * 後台- MyData Zip hash validation API Controller
 */
@Controller
@RequestMapping("/backend/myData")
@Api(tags = {"geo-myData-validation-controller 後台-MyData Zip hash validation API Controller"})
public class GeoMyDataValidationController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoMyDataValidationController.class);

    @Autowired
    KgoCaseMainRepository kgoCaseMainRepository;
    @Autowired
    GeoKgoCaseMainZipHashRepository geoKgoCaseMainZipHashRepository;
    @Autowired
    CommonService commonService;

    /**
     * Geo 20221010 add_Jim
     * 後台-取得DB myData hash
     *
     * @param caseId
     * @return String "No records from DB." or hash
     */
    @ApiOperation(value = "後台-取得DB myData hash")
    @RequestMapping(value = {"/getHash/{caseId}"}, method = {RequestMethod.GET}, produces = {MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public String unitComboBoxGetByOrganIdAction(@PathVariable String caseId) {
        KgoCaseMain kgoCaseMain = kgoCaseMainRepository.findByCaseId(caseId);
        String message = "No records from DB.";
        if (kgoCaseMain != null) {
            GeoKgoCaseMainZipHash geoKgoCaseMainZipHash = geoKgoCaseMainZipHashRepository.findByTxId(kgoCaseMain.getMyDataTxId());
            if (geoKgoCaseMainZipHash != null)
                message = geoKgoCaseMainZipHash.getMixHash();
        }
        return message;
    }

    /**
     * Geo 20221010 add_Jim
     * 後台-上傳txt file and cer file
     *
     * @param files
     * @return String mixHash
     */
    @ApiOperation(value = "後台-上傳檔案取得hash")
    @RequestMapping(value = {"/uploadFiles"}, method = {RequestMethod.POST}, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public String uploadFiles(@RequestParam("files") MultipartFile[] files) throws Exception {
        List<String> sha256List = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = FilenameUtils.getName(file.getOriginalFilename());
            String fileExtension = FilenameUtils.getExtension(fileName);
            if ("txt".equalsIgnoreCase(fileExtension)) {
                InputStream inputStream = file.getInputStream();
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().forEach(sha256List::add);
            } else if ("cer".equalsIgnoreCase(fileExtension)) {
                String path = Paths.get("src", "main", "resources", "MyDataHashTextFile", fileName).toString();
                File cerFile = saveMultipartFile(file, path);
                sha256List.add(0, commonService.getFileSHAEncoding(path, "SHA-256"));
                if (cerFile.exists())
                    cerFile.delete();
            }
        }
        String allSha256 = String.join("", sha256List);
        String mixHash = commonService.getStringSHAEncoding(allSha256, "SHA-256");
        return mixHash;
    }

    private File saveMultipartFile(MultipartFile file, String fileRoute) throws Exception {
        File convFile = new File(fileRoute);
        if (!convFile.getParentFile().exists()) {
            convFile.getParentFile().mkdirs();
        }
        convFile.createNewFile();

        InputStream inputStream = file.getInputStream();
        FileOutputStream fos = new FileOutputStream(convFile);
        byte[] buffer = new byte[4096];
        int len = -1;
        while ((len = inputStream.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        inputStream.close();
        fos.close();
        return convFile;
    }
}
