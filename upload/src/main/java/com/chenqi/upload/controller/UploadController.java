package com.chenqi.upload.controller;

import com.alibaba.fastjson.JSONObject;
import com.chenqi.upload.entity.UploadFile;
import com.chenqi.upload.service.UploadFileService;
import com.chenqi.upload.util.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UploadController {

    private final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Value("${fastdfs.host}")
    private String host;

    @Autowired
    private UploadFileService uploadFileService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/file")
    public String file() {
        return "file";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public JSONObject upload(@RequestParam("file") MultipartFile file) {
        JSONObject result = new JSONObject();
        try {
            String url = host + UploadUtil.uploadFile(file);
            logger.info("文件上传成功：{}", url);
            result.put("code", "0");
            result.put("url", url);
        } catch (Exception e) {
            result.put("code", "-1");
        }
        return result;
    }

    @RequestMapping("/saveFile")
    @ResponseBody
    public JSONObject saveFile(@RequestParam String mark,
                               @RequestParam String url,
                               @RequestParam(required = false) String type) {
        JSONObject result = new JSONObject();
        if (!UploadFile.checkType(type)) {
            result.put("code", "-1");
            result.put("msg", "未知文件类型");
        } else {
            UploadFile uploadFile = new UploadFile(mark, url, type);
            try {
                uploadFileService.save(uploadFile);
                result.put("code", "0");
                result.put("msg", "保存成功");
            } catch (Exception e) {
                result.put("code", "-1");
                result.put("msg", "保存失败");
            }
        }
        return result;
    }

    @RequestMapping("/list")
    @ResponseBody
    public JSONObject list(@RequestParam Integer page, @RequestParam Integer limit,
                           @RequestParam(required = false) String type, @RequestParam(required = false) String mark) {
        JSONObject result = new JSONObject();
        Integer start = (page - 1) * limit;
        String markStr = getMarkStr(mark);
        int count = 0;
        List<UploadFile> fileList = new ArrayList<>();
        if (type == null) {
            count = uploadFileService.countByMark(markStr).intValue();
            fileList = uploadFileService.listByMark(markStr, start, limit);
        } else {
            count = uploadFileService.countByType(markStr, type).intValue();
            fileList = uploadFileService.listByType(markStr, type, start, limit);
        }
        result.put("code", 0);
        result.put("msg", "success");
        result.put("data", fileList.stream().map(UploadFile::toJson).collect(Collectors.toList()));
        result.put("count", count);
        return result;
    }

    private String getMarkStr(String mark) {
        return mark == null ? "%" : mark + "%";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public JSONObject delete(@RequestParam Integer id) {
        JSONObject result = new JSONObject();
        UploadFile file = uploadFileService.findById(id);
        if (file != null) {
            String url = file.getUrl();
            UploadUtil.deleteFile(url.replace(host, ""));
            uploadFileService.delete(file);
            result.put("code", 0);
            result.put("msg", "删除成功");
            logger.info("文件删除成功：{}", url);
        } else {
            result.put("code", -1);
            result.put("msg", "删除失败，文件不存在");
        }
        return result;
    }
}
