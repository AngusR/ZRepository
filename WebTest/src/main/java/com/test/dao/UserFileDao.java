package com.test.dao;

import com.test.entity.UserFile;

import java.util.List;

public interface UserFileDao {

    //根据登录用户的ID获取用户的文献列表信息
    List<UserFile> findByUserId(Integer id);

    //保存用户文件
    void save(UserFile userFile);

    //根据文件id获取文件信息，下载
    UserFile findById(String id);
}
