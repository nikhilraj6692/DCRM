package com.smp.app.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Component
public class AttachmentUtils {

    static Logger log = LoggerFactory.getLogger(AttachmentUtils.class.getName());

    @Autowired
    private CommonUtils commonUtils;

    public Boolean uploadCompanyLogo(MultipartFile fileObject, String filePath, Integer companyId) {
        try {
            File fileToDir = new File(filePath);
            if (!fileToDir.exists()) {
                fileToDir.mkdirs();
            }
            File fileToCreate = new File(filePath, fileObject.getOriginalFilename());
            FileUtils.copyFile(this.convertMultipartToFile(fileObject), fileToCreate);
        } catch (Exception e) {
            log.error("Error while upload image " + e.getMessage());
            return false;
        }
        return true;
    }

    public Boolean uploadFile(MultipartFile fileObject, String folderName, String fileUploadFileName) {
        try {
            String filePath = commonUtils.readUserDefinedMessages("files.basePath").trim();
            File fileToDir = new File(null != folderName ? filePath + folderName : filePath);
            if (!fileToDir.exists()) {
                fileToDir.mkdirs();
            }
            File fileToCreate = new File(fileToDir,
                StringUtils.hasText(fileUploadFileName) ? fileUploadFileName : fileObject.getOriginalFilename());
            FileUtils.copyInputStreamToFile(fileObject.getInputStream(), fileToCreate);
        } catch (Exception e) {
            log.error("Error while upload image " + e.getMessage());
            return false;
        }
        return true;
    }

    public Boolean uploadFile(File fileObject, String folderName, String fileUploadFileName) {
        try {
            String filePath = commonUtils.readUserDefinedMessages("files.basePath").trim();
            File fileToDir = new File(null != folderName ? filePath + folderName : filePath);
            if (!fileToDir.exists()) {
                fileToDir.mkdirs();
            }
            File fileToCreate = new File(fileToDir,
                StringUtils.hasText(fileUploadFileName) ? fileUploadFileName : fileObject.getName());
            fileToCreate.createNewFile();
        } catch (Exception e) {
            log.error("Error while upload image " + e.getMessage());
            return false;
        }
        return true;
    }


    private File convertMultipartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        //convFile.createNewFile();
        /*FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();*/
        return convFile;
    }

    public List<String> getFileDetails(String attachmentPath) {
        File folder = new File(attachmentPath);
        File[] listOfFiles = folder.listFiles();
        List<String> filePaths = new ArrayList<>();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    filePaths.add(file.getAbsolutePath());
                }
            }
        }
        return filePaths;
    }

    public void deleteFileDirectory(String filePath) {
        File directory = new File(filePath);
        if (directory.exists()) {
            try {
                FileUtils.forceDelete(directory);
            } catch (IOException e) {
                log.error("Error while delete file directory " + e.getMessage());
            }
        }
    }
}
