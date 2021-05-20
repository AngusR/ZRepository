package com.test.service;

import com.test.entity.UserFile;

import java.util.List;

public interface UserFileService {

    List<UserFile> findByUserId(Integer id);

    void save(UserFile userFile);

    UserFile findById(String id);
}
