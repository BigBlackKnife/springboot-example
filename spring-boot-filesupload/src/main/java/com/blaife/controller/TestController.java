package com.blaife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Blaife
 * @description TODO
 * @data 2020/8/23 17:59
 */
@Controller
public class TestController {

    @RequestMapping("/toIndex")
    public String toIndex(Model model) {
        return "index";
    }

    /**
     * 单个文件上传方法
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(HttpServletRequest request, MultipartFile file) {

        String uploadDir = System.getProperty("user.dir")+"/upload/";

        // 如果文件不存在则自动创建文件夹
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            executeUpload(uploadDir, file);
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
        return "上传成功";
    }

    /**
     * 单个文件上传方法
     * @param files
     * @return
     */
    @RequestMapping(value = "/uploads", method = RequestMethod.POST)
    @ResponseBody
    public String uploads(HttpServletRequest request, MultipartFile[] files) {

        String uploadDir = System.getProperty("user.dir")+"/upload/";

        // 如果文件不存在则自动创建文件夹
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {

            for (int i = 0; i < files.length; i++) {
                if (files[i] != null) {
                    executeUpload(uploadDir, files[i]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
        return "上传成功";
    }



    /**
     * 提取公共上传方法
     * @param uploadDir 上传文件目录
     * @param file 上传文件
     * @throws IOException IO异常
     */
    private void executeUpload(String uploadDir, MultipartFile file) throws IOException {
        // 文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 上传文件名
        String fileName = UUID.randomUUID() + suffix;
        // 文件路径
        File serverFile = new File(uploadDir + fileName);
        file.transferTo(serverFile);
    }
}
