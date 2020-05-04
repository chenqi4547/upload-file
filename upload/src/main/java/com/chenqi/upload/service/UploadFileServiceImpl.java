package com.chenqi.upload.service;

import com.chenqi.upload.dao.UploadFileDao;
import com.chenqi.upload.entity.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private UploadFileDao uploadFileDao;

    @Override
    public void save(UploadFile uploadFile) {
        uploadFileDao.save(uploadFile);
    }

    @Override
    public List<UploadFile> listByMark(String markStr, Integer start, Integer limit) {
        return uploadFileDao.listByMark(markStr, start, limit);
    }

    @Override
    public BigInteger countByMark(String mark) {
        return uploadFileDao.countByMark(mark);
    }

    @Override
    public BigInteger countByType(String markStr, String type) {
        return uploadFileDao.countByType(markStr, type);
    }

    @Override
    public List<UploadFile> listByType(String markStr, String type, Integer start, Integer limit) {
        return uploadFileDao.listByType(markStr, type, start, limit);
    }

    @Override
    public UploadFile findById(Integer id) {
        Optional<UploadFile> uploadFile = uploadFileDao.findById(id);
        if (uploadFile.isPresent()) {
            return uploadFile.get();
        }
        return null;
    }

    @Override
    public void delete(UploadFile file) {
        uploadFileDao.delete(file);
    }
}
