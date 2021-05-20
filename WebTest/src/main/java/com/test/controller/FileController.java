package com.test.controller;


import com.sun.deploy.net.HttpResponse;
import com.sun.deploy.net.URLEncoder;
import com.test.entity.User;
import com.test.entity.UserFile;
import com.test.service.UserFileService;
import com.test.utils.R;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/file")
public class FileController {

    @Resource
    private UserFileService userFileService;

    /**
     * 返回JSON格式数据
     */
    @GetMapping("/getJson")
    @ResponseBody
    public R getJson(String id){
        UserFile userFile = userFileService.findById(id);
        return R.ok().data("FileJson",userFile);
    }

    /**
     * 文件下载接口
     */
    @GetMapping("/download")
    public void download(String id, HttpServletResponse response) throws IOException {
        //获取文件信息
        UserFile userFile = userFileService.findById(id);
        //根据文件信息中文件名字 文件存储路径获取文件输入流
        String realpath = ResourceUtils.getURL("classpath:").getPath() + "/static" + userFile.getPath();
        //获取文件输入流
        FileInputStream is = new FileInputStream(new File(realpath, userFile.getNewFileName()));
        //附件下载
        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(userFile.getOldFileName(),"UTF-8"));
        //获取响应输出流
        ServletOutputStream os = response.getOutputStream();
        //文件拷贝
        IOUtils.copy(is,os);
        //关闭流
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);

    }

    /**
     * 上传文件的处理 并保存文件信息到数据库中
     */
    @PostMapping("/upload")
    public String upload(MultipartFile aa,HttpSession session) throws IOException {
        //获取上传文件的用户id
        User user = (User) session.getAttribute("user");

        //获取文件的原始名称
        String oldFileName=aa.getOriginalFilename();

        //获取文件后缀
        String extension = "."+FilenameUtils.getExtension(aa.getOriginalFilename());

        //生成新的文件名称
        String newFileName=new SimpleDateFormat("yyyyMMdd").format(new Date())+ UUID.randomUUID().toString().replace("-","")+extension;

        //文件大小
        long size = aa.getSize();

        //文件类型
        String type = aa.getContentType();

        //处理文件上传
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static/files";
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath =realPath+"/"+ dateFormat;
        File dateDir = new File(dateDirPath);
        //根据日期创建目录
        if(!dateDir.exists()){

            dateDir.mkdirs();

        }
        //处理文件上传
        aa.transferTo(new File(dateDir,newFileName));

        //将文件信息存入数据库
        UserFile userFile = new UserFile();
        userFile.setOldFileName(oldFileName).setNewFileName(newFileName).setExt(extension).setSize(String.valueOf(size)).setType(type)
                .setPath("/files/"+dateFormat).setUserId(user.getId());
        userFileService.save(userFile);


        return "redirect:/file/showAll";
    }

    /**
     * 查询所有文件
     */
    @GetMapping("/showAll")
    public String findAll(HttpSession session, Model model){
        //在登录的session中获取用户ID
        User user=(User) session.getAttribute("user");
        //根据用户id查询用户的文件信息
        List<UserFile> userFiles = userFileService.findByUserId(user.getId());
        //存入作用域中
        model.addAttribute("files",userFiles);

        return "/showAll";
    }

}
