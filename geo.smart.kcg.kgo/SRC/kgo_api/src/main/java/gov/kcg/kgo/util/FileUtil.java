package gov.kcg.kgo.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static void createFile(File targetDir, String filename, byte[] data) {
        try {
            if (!targetDir.exists() || !targetDir.isDirectory()) {
                targetDir.mkdirs();
            }
            File packFile = Paths.get(targetDir + File.separator + filename).toFile();
            if (packFile.exists()) packFile.delete();
            FileUtils.writeByteArrayToFile(packFile, data);
        } catch (Exception e) {
            LOGGER.error("Create File Error", e);
        }
    }

    public static void unzip(File sourceFile, File targetDir) {
        try {
            if (!targetDir.exists() || !targetDir.isDirectory()) {
                targetDir.mkdirs();
            }
            ZipEntry entry = null;

            String entryFilePath = null, entryDirPath = null;

            File entryFile = null, entryDir = null;

            int index = 0, count = 0, bufferSize = 1024;

            byte[] buffer = new byte[bufferSize];

            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            ZipFile zip = new ZipFile(sourceFile.getAbsoluteFile());

            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();

            while (entries.hasMoreElements()) {
                entry = entries.nextElement();
                entryFilePath = targetDir.getAbsolutePath() + File.separator + entry.getName().replace("/", "\\");
                index = entryFilePath.lastIndexOf(File.separator);

                if (index != -1) {
                    entryDirPath = entryFilePath.substring(0, index);
                } else {
                    entryDirPath = "";
                }

                entryDir = new File(entryDirPath);

                if (!entryDir.exists() || !entryDir.isDirectory()) {
                    entryDir.mkdirs();
                }

                entryFile = new File(entryFilePath);

                if (!entryFile.isDirectory()) {
                    bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                    bis = new BufferedInputStream(zip.getInputStream(entry));

                    while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
                        bos.write(buffer, 0, count);
                    }

                    bos.flush();
                    bos.close();
                }

            }
        } catch (IOException e) {
            LOGGER.error("Create Zip Error", e);
        }
    }

    public static void unzipFile(final File zipFile, final File destinationDirectory, Charset fileNameEncodingCharset) throws IOException {
        if (!zipFile.exists()) {
            throw new IOException("ZipFile '" + zipFile.getAbsolutePath() + "' does not exist");
        } else if (!zipFile.isFile()) {
            throw new IOException("ZipFile '" + zipFile.getAbsolutePath() + "' is not a file");
        } else if (!destinationDirectory.exists() || !destinationDirectory.isDirectory()) {
            destinationDirectory.mkdirs();
        }

        if (fileNameEncodingCharset == null) {
            fileNameEncodingCharset = Charset.forName("Cp437");
        }

        final String destinationDirectoryPath = destinationDirectory.getCanonicalPath();
        final byte[] buffer = new byte[1024];
        try (final ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile), fileNameEncodingCharset)) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                if (zipEntry.getName().endsWith("/")) {
                    final File newDirectory = new File(destinationDirectory, zipEntry.getName());
                    final String newDirectoryPath = newDirectory.getCanonicalPath();
                    if (!newDirectoryPath.startsWith(destinationDirectoryPath + File.separator)) {
                        throw new IOException("ZipEntry is outside of the destination directory: " + zipEntry.getName());
                    }

                    if (!newDirectory.exists()) {
                        newDirectory.mkdirs();
                    } else if (!newDirectory.isDirectory()) {
                        throw new IOException("Destination directory exists but is not a directory: " + newDirectory.getAbsolutePath());
                    }
                } else {
                    final File destFile = new File(destinationDirectory, zipEntry.getName());
                    final String destFilePath = destFile.getCanonicalPath();
                    if (!destFilePath.startsWith(destinationDirectoryPath + File.separator)) {
                        throw new IOException("ZipEntry is outside of the destination directory: " + zipEntry.getName());
                    }

                    final File parentDirectory = destFile.getParentFile();
                    if (!parentDirectory.exists()) {
                        parentDirectory.mkdirs();
                    } else if (!parentDirectory.isDirectory()) {
                        throw new IOException("Destination directory exists but is not a directory: " + parentDirectory.getAbsolutePath());
                    }

                    try (final FileOutputStream fos = new FileOutputStream(destFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
    }

    public static Boolean checkFileSha256(File file, String digest) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            String file256 = DigestUtils.sha256Hex(fileInputStream);
            return digest.equals(file256);
        } catch (Exception e) {
            LOGGER.error("Check DpFile sha256 Failed", e);
        }
        return false;
    }

    /**
     *
     * @param nameList 判斷重複的檔案名稱清單
     * @param fullFileName 被判斷檔案名稱
     * @return 新檔案名稱 ex fileName(1)
     */
    public static String renameFileFromList(List<String> nameList, String fullFileName){
        String fileName = fullFileName.substring(0,fullFileName.lastIndexOf("."));
        String fileExt = fullFileName.substring(fullFileName.lastIndexOf("."));

        String inputFileName = fileName;
        if(nameList.stream().filter(s-> inputFileName.equals(s.substring(0,s.lastIndexOf(".")))).findAny().isPresent()){
            int brackets;
            if( ( brackets = fileName.lastIndexOf("(") ) > 0 && fileName.lastIndexOf(")") > brackets ){
                String seq = fileName.substring(brackets+1, fileName.lastIndexOf(")"));
                try{
                    fileName = fileName.substring(0,brackets) + "(" + (Integer.parseInt(seq)+1) + ")";
                }catch (NumberFormatException e){
                    fileName += "(1)";
                }
            }else{
                fileName += "(1)";
            }
        }
        String newName = fileName ;
        if(nameList.stream().filter(s-> newName.equals(s.substring(0,s.lastIndexOf(".")))).findAny().isPresent()){
           return renameFileFromList(nameList,newName + fileExt);
        }
        return newName + fileExt;
    }

}
