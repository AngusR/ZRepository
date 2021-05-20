package com.test.service;

import com.test.dao.UserFileDao;
import com.test.entity.UserFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserFileServiceImpl implements UserFileService {

    @Resource
    private UserFileDao userFileDao;

    @Override
    public List<UserFile> findByUserId(Integer id) {
        return userFileDao.findByUserId(id);
    }

    @Override
    public void save(UserFile userFile) {
        String isImg = userFile.getType().startsWith("image")?"是":"否";
        userFile.setIsImg(isImg);
        userFile.setUploadTime(new Date());
        userFileDao.save(userFile);
    }

    @Override
    public UserFile findById(String id) {
        return userFileDao.findById(id);
    }
}
