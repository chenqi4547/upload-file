package com.chenqi.upload.entity;

import com.alibaba.fastjson.JSONObject;
import com.chenqi.upload.util.DateUtil;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "upload_file")
@Data
public class UploadFile {

    private final static String TYPE_IMAGE = "IMAGE";
    private final static String TYPE_FILE = "FILE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String mark;

    private String type;

    private String url;

    private Date createTime;

    public UploadFile() {
    }

    public UploadFile(String mark, String url, String type) {
        this.mark = mark;
        this.url = url;
        this.type = type;
        this.createTime = new Date();
    }

    public static boolean checkType(String type) {
        return TYPE_FILE.equals(type) || TYPE_IMAGE.equals(type);
    }

    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("id", id);
        result.put("mark", mark);
        result.put("type", type);
        result.put("url", url);
        result.put("createTime", DateUtil.formatDateTime(createTime, DateUtil.DATETIME_ALL));
        return result;
    }
}
