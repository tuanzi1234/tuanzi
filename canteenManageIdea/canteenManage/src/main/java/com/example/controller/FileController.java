package com.example.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/files")
public class FileController {
    /**
     * 日志记录器，用于记录文件上传过程中的信息
     */
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    /**
     * 文件存储路径，默认为项目根目录下的files文件夹
     */
    private static final String filePath = System.getProperty("user.dir") + "/files/";

    /**
     * 文件基础URL，从配置文件中读取，默认为空
     */
    @Value("${fileBaseUrl:}")
    private String fileBaseUrl;

    /**
     * 文件上传接口
     *
     * @param file 上传的文件
     * @return 上传成功后的文件访问URL
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        // 获取上传文件的原始文件名
        String fileName = file.getOriginalFilename();
        try {
            // 如果文件存储路径不存在，则创建该路径
            if (!FileUtil.isDirectory(filePath)) {
                FileUtil.mkdir(filePath);
            }
            // 生成新的文件名，格式为：时间戳-原始文件名
            fileName = System.currentTimeMillis() + "-" + fileName;
            // 构建文件的实际存储路径
            String realFilePath = filePath + fileName;
            // 将文件内容写入到实际存储路径中
            FileUtil.writeBytes(file.getBytes(), realFilePath);
        } catch (Exception e) {
            // 记录文件上传失败的错误信息
            log.error(fileName + "--文件上传失败", e);
        }
        // 构建文件的访问URL
        String url = fileBaseUrl + "/files/download/" + fileName;
        // 返回上传成功的结果，包含文件的访问URL
        return Result.success(url);
    }
    /**
     * 获取文件
     */
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) {
        OutputStream os;
        try {
            // 检查文件名是否为空
            if (StrUtil.isNotEmpty(fileName)) {
                // 设置响应头，指定文件名为原始文件名，并进行URL编码
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
                // 设置响应内容类型为二进制流
                response.setContentType("application/octet-stream");
                // 读取文件内容为字节数组
                byte[] bytes = FileUtil.readBytes(filePath + fileName);
                // 获取响应输出流
                os = response.getOutputStream();
                // 将文件内容写入输出流
                os.write(bytes);
                // 刷新输出流
                os.flush();
                // 关闭输出流
                os.close();
            }
        } catch (Exception e) {
            // 记录文件下载失败的警告信息
            log.warn("文件下载失败：" + fileName);
        }
    }


}