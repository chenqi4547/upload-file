package com.chenqi.upload.service;

import com.chenqi.upload.entity.UploadFile;
import javafx.beans.value.ObservableNumberValue;

import java.math.BigInteger;
import java.util.List;

public interface UploadFileService {
    void save(UploadFile uploadFile);

    List<UploadFile> listByMark(String mark, Integer start, Integer limit);

    BigInteger countByMark(String mark);

    BigInteger countByType(String markStr, String type);

    List<UploadFile> listByType(String markStr, String type, Integer start, Integer limit);

    UploadFile findById(Integer id);

    void delete(UploadFile file);
}
