package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.EasyUI_Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping("/file")
    public String fileUp(MultipartFile fileImage){

        //获取文件名称
        String fileName = fileImage.getOriginalFilename();
        File fileDir = new File("E:/images");
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        File file = new File("E:/images/" + fileName);
        try {
            fileImage.transferTo(file);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("上传失败!");
        }
        return "redirect:file.jsp";
    }

    /**
     * 实现文件上传
     */
    @RequestMapping("/pic/upload")
    @ResponseBody
    public EasyUI_Image fileUpload(MultipartFile uploadFile){

        return fileService.fileUpload(uploadFile);
    }
}
