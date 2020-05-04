package com.chenqi.upload.dao;

import com.chenqi.upload.entity.UploadFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface UploadFileDao extends CrudRepository<UploadFile, Integer> {
    @Query(value = " SELECT * FROM upload_file WHERE mark LIKE ?1 ORDER BY create_time DESC LIMIT ?2,?3 ", nativeQuery = true)
    List<UploadFile> listByMark(String markStr, Integer start, Integer limit);

    @Query(value = " SELECT COUNT(*) FROM upload_file WHERE mark LIKE ?1 ", nativeQuery = true)
    BigInteger countByMark(String mark);

    @Query(value = " SELECT COUNT(*) FROM upload_file WHERE mark LIKE ?1 AND type = ?2 ", nativeQuery = true)
    BigInteger countByType(String markStr, String type);

    @Query(value = " SELECT * FROM upload_file WHERE mark LIKE ?1 AND type = ?2 ORDER BY create_time DESC LIMIT ?3,?4 ", nativeQuery = true)
    List<UploadFile> listByType(String markStr, String type, Integer start, Integer limit);
}
