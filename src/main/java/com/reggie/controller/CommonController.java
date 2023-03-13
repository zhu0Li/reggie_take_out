package com.reggie.controller;

import com.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件的上传和下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    //定义变量传入application.yml中设置的地址
    @Value("${riggie.path}")
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){//MultipartFile后的变量名需要和网页中name中的名字相同
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());

        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //获取原始文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //防止文件覆盖，使用UUID重新生成文件名，防止文件名字重复造成覆盖，并加上原始文件后缀
        String fileName = UUID.randomUUID().toString() + suffix;

        //创建一个目录对象
        File dir = new File(basePath);
        //判断当前目录是否存在
        if(!dir.exists()){
            //目录不存在，需要创建
            dir.mkdir();
        }

        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //最后需要给页面返回文件，用于新增菜品
        return R.success(fileName);
    }


    /**
     * 文件下载
     * 使用void不需要返回值是因为：通过输出流向浏览器页面写回数据
     * @param name 前端传回的数据，文件名
     * @param response 输出流需要使用response来获得
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));

            //输出流，通过输出流将文件写回浏览器，在浏览器展示图片
            //因为是向浏览器写回数据，所以直接使用response的输出流
            ServletOutputStream outputStream = response.getOutputStream();

            //设置响应回去的是什么类型文件
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1023];
            //以下while条件解释
            //输入字节流in按照byte数组缓冲区每4个字节循环一次进行read操作，直到读到-1这个整数（-1是一个标识，就是文件数据的末尾）。
            while((len = fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) { //因为是编译异常，需要现场处理
            throw new RuntimeException(e);
        }


    }
}
