package com.chenqi.upload;

import com.chenqi.upload.util.UploadUtil;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
class UploadApplicationTests {

    @Test
    void contextLoads() throws IOException, MyException {
        String tracker = UploadUtil.class.getResource("/tracker.conf").getPath();
        ClientGlobal.init(tracker);
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageClient storageClient = new StorageClient(trackerServer, null);

        String[] file = storageClient.upload_file("/Users/chenqi/Pictures/result-1.png", "png", null);
        for (String s : file) {
            System.out.println(s);
        }
    }

}
