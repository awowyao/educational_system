package com.cwy.educationaladministrationsystem.util;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class uploadImage {
    // 定义一个目标路径，就是我们要把图片上传的位置
//    private static String BASE_PATH = "C:\\Users\\yao\\Desktop\\框架技术\\大作业\\GuChen\\src\\main\\resources\\static\\images\\";
    private static String BASE_PATH = "E:\\毕业设计\\源代码和查重报告\\SpringBoot后端\\educationaladministrationsystem\\src\\main\\resources\\static\\userData\\";
    // 定义访问图片路径
    private static final String SERVER_PATH = "http://localhost:8080/images/";
    public static String upload(MultipartFile file) {
        // 获取上传图片的名称
        String filename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 将生成的uuid和filename惊醒拼接
        String newFileName = uuid + '-' + filename;

        // 创建一个文件实例对象
        File image = new File(BASE_PATH, newFileName);
        // 对这个文件进行上传操作
        try {
            file.transferTo(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFileName;
    }

}
