package com.smp.app.util;

import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUpload {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUpload.class);
    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private AttachmentUtils attachmentUtils;


    public Boolean uploadProjectImage(MultipartFile file, String fileUploadFileName) {
        try {

            String folderName = commonUtils.readUserDefinedMessages("smp.project_logo.folder_name").trim();
            attachmentUtils.uploadFile(file, folderName, fileUploadFileName);
            LOGGER.error("Image Uploaded Successfully !!!!!!!!!!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean uploadReviewerImage(MultipartFile file, String fileUploadFileName, Integer reviewerCommentId) {
        try {
            String folderName = commonUtils.readUserDefinedMessages("smp.project_reviewer_images.folder_name").trim();
            attachmentUtils.uploadFile(file, folderName + "/" + reviewerCommentId, fileUploadFileName);
            LOGGER.error("Image Uploaded Successfully !!!!!!!!!!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean uploadFileReturnAttachments(MultipartFile file, String fileUploadFileName, Integer returnRequestId) {
        try {
            String folderName = commonUtils.readUserDefinedMessages("smp.file_to_return.folder_name").trim();
            attachmentUtils.uploadFile(file, folderName + "/" + returnRequestId, fileUploadFileName);
            LOGGER.error("Image Uploaded Successfully !!!!!!!!!!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<String> getReviewerImageList(Integer userReviewId) {
        List<String> reviewerImageList = new LinkedList<String>();
        String folderName = null;
        try {
            folderName = commonUtils.readUserDefinedMessages("smp.project_reviewer_images.folder_name").trim();
            List<String> filePaths = attachmentUtils.getFileDetails(folderName + "/" + userReviewId);
            if (!CollectionUtils.isEmpty(filePaths)) {
                reviewerImageList.addAll(filePaths);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviewerImageList;
    }

    // Get Project Report List From AWS cloud
    public boolean getProjectReportStatus(Integer projectId, Integer projVersionNum, String companyName) {
        boolean isProjectReportExisted = false;
        String folderName = null;
        try {
            folderName = commonUtils.readUserDefinedMessages("smp.project_report.folder_name").trim() + "/" + projectId;
            List<String> filePaths = attachmentUtils.getFileDetails(folderName);
            String targetProjectReportName = projectId + "_" + companyName + "_v" + projVersionNum + ".pdf";
            for (String file : filePaths) {
                String[] arr = file.split("/");
                if (arr.length >= 2) {
                    if (targetProjectReportName.equalsIgnoreCase(arr[2])) {
                        isProjectReportExisted = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isProjectReportExisted;
    }


    // Delete uploaded Reviewer attachments
    public List<String> deleteReviewerImage(Integer userReviewId, String targetImageName) {
        List<String> reviewerImageList = new LinkedList<String>();
        String folderName = null;
        try {
            folderName = commonUtils.readUserDefinedMessages("smp.project_reviewer_images.folder_name").trim();
            List<String> filePaths = attachmentUtils.getFileDetails(folderName + "/" + userReviewId);
            for (String file : filePaths) {
                String[] arr = file.split("/");
                if (arr.length > 1 && arr[2].equalsIgnoreCase(targetImageName)) {
                    //s3client.deleteObject(bucketName, file.getKey());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviewerImageList;
    }


    /*public Boolean uploadCircularAttachment(MultipartFile file, String fileUploadFileName) {
        try {
            AWSCredentials credentials = new BasicAWSCredentials(commonUtils.readUserDefinedMessages("aws_access_key_id"),
				commonUtils.readUserDefinedMessages("aws_secret_access_key"));

            // create a client connection based on credentials
            AmazonS3 s3client = new AmazonS3Client(credentials);
            String bucketName = commonUtils.readUserDefinedMessages("smp.image_store.bucket_name").trim();
            String folderName = commonUtils.readUserDefinedMessages("smp.circular_attachment.folder_name").trim();
            InputStream inputStreamObj = file.getInputStream();
            s3client.putObject(new PutObjectRequest(bucketName, folderName + "/" + fileUploadFileName, inputStreamObj,
                new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));
            LOGGER.error("Circular Attachment Uploaded Successfully !!!!!!!!!!");
            return true;
        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
        } catch (AmazonClientException ace) {
            ace.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }*/
}
