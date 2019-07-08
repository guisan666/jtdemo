package com.jt.service;

import com.jt.vo.EasyUI_Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {

    @Value("${image.localDirPath}")
    private String localDirPath = "E:/images/";
    @Value("${image.urlDirPath}")
    private String urlDirPath;

    /**
     * 文件上传思路
     *  1.校验文件类型是否为图片    (文件后缀)
     *  2.防止恶意文件    (将文件交给工具API校验从中获取宽高)
     *  3.众多图片如何保存  (分文件存储 ---按照yyyy/MM/dd格式)
     *  4.文件名重名    (自定义UUID为文件名称)
     * @param file  上传的文件
     * @return
     */
    @Override
    public EasyUI_Image fileUpload(MultipartFile file) {
        EasyUI_Image image = new EasyUI_Image();

        // 获取文件名
        String fileName = file.getOriginalFilename();
        //校验文件名称
        fileName = fileName.toLowerCase();

        if(!fileName.matches("^.+\\.(jpg|png|gif|jpeg|icon)$")){
            image.setError(1);
            return image;
        }
        //利用API读取用户提交数据
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            if (height==0 || width==0){
                image.setError(1);
                return image;
            }
            //封装图片属性
            image.setHeight(height).setWidth(width);
            //以时间格式创建文件夹保存数据
            String datePathDirs = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
            String realDirPath = localDirPath + datePathDirs;
            File dirFile = new File(realDirPath);
            if (!dirFile.exists()){
                dirFile.mkdirs();
            }
            //采用UUID文件名称
            String uuid = UUID.randomUUID().toString().replace("-","");
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            String realName = uuid + fileType;
            File uFile = new File(realDirPath + realName);
            file.transferTo(uFile);

            //虚拟lujing
            String realUrlPath = urlDirPath + "/" + datePathDirs + "/" + realName;
            image.setUrl(realUrlPath);

        }catch (Exception e){
            e.printStackTrace();
            image.setError(1);   //对象转换时异常
            return image;
        }

        return image;
    }
}
