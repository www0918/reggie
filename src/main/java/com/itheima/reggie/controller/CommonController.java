package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Multicast;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 图片上传和转存
 */

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
     @Value("${reggie.path}")
    private String basePath;

    /**
     * 上传照片
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        //获取上传文件名
        String originalFilename = file.getOriginalFilename();
        //截取文件名后缀，截取最后一个”.“开始截取   .jpg
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用UUID重新生产文件名，防止文件名重复造成文件覆盖
        String filename = UUID.randomUUID().toString() + substring;

        //判断文件路径是否存在
        File dir=new File(basePath);
        if (!dir.exists()){
            //如果没有，则创建文件目录
            dir.mkdir();
        }
        log.info(file.toString());
        //将临时文件转存到指定位置
        file.transferTo(new File(basePath+filename));
        //返回转存的路径
        return R.success(filename);
    }
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        //输入流，通过输入流读取文件内容
        FileInputStream fileInputStream=new FileInputStream(basePath+name);
        //输出流，将文件写回浏览器，在浏览器展示照片
        ServletOutputStream servletOutputStream=response.getOutputStream();
        //使客户端浏览器区分不同的种类的数据
        response.setContentType("image/jpeg");

        //遍历
        int len=0;
        byte[] bytes=new byte[1024];
        while ((len=fileInputStream.read(bytes))!=-1){
            servletOutputStream.write(bytes,0,len);
            //强制输出
            servletOutputStream.flush();
        }
        //关闭资源
        servletOutputStream.close();
        fileInputStream.close();

    }
}
