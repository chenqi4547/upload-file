package com.chenqi.upload.util;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UploadUtil {

    public static StorageClient getStorageClient() {
        /*String tracker = UploadUtil.class.getResource("/tracker.conf").getPath();
        try {
            ClientGlobal.init(tracker);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        Properties properties = new Properties();
        InputStream in = UploadUtil.class.getClassLoader().getResourceAsStream("tracker.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ClientGlobal.initByProperties(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerClient.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StorageClient storageClient = new StorageClient(trackerServer, null);

        try {
            trackerServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storageClient;
    }

    public static String uploadFile(MultipartFile multipartFile) {
        StorageClient storageClient = getStorageClient();
        //文件位置，后缀，元数据
        String[] uploadInfo = new String[0];
        try {
            byte[] bytes = multipartFile.getBytes();
            String originalFilename = multipartFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf('.');
            String extName = originalFilename.substring(lastIndexOf + 1);
            uploadInfo = storageClient.upload_file(bytes, extName, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = "";
        for (String s : uploadInfo) {
            url += "/" + s;
        }
        return url.substring(1);
    }

    public static void deleteFile(String url) {
        StorageClient storageClient = getStorageClient();
        int index = url.indexOf('/');
        String groupName = url.substring(0, index);
        String remoteFileName = url.substring(index + 1);
        try {
            storageClient.delete_file(groupName, remoteFileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
