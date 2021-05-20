package com.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class UserFile {
    private Integer id;
    private String oldFileName;
    private String newFileName;
    private String ext;
    private String path;
    private String size;
    private String type;
    private String isImg;
    private Date uploadTime;
    private Integer userId;

    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
    }

    public String getPath() {
        return oldFileName;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public String getOldFileName() {
        return oldFileName;
    }
}
