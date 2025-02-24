package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
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
import java.util.HashMap;
import java.util.Map;

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
    /**
     * wang-editor编辑器文件上传接口
     */
    @PostMapping("/wang/upload")
    public Map<String, Object> wangEditorUpload(MultipartFile file) {
        // 生成唯一标识，解决并发上传文件名冲突问题
        String flag = System.currentTimeMillis() + "";
        String fileName = file.getOriginalFilename();

        try {
            // 持久化文件：时间戳前缀 + 原始文件名 的存储策略
            FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);
            System.out.println(fileName + "--上传成功");
            Thread.sleep(1L);  // 微小延迟，用于防止快速重复提交
        } catch (Exception e) {
            System.err.println(fileName + "--文件上传失败");  // 异常处理需增强（当前仅日志打印）
        }

        // 构建文件访问地址（需确保fileBaseUrl正确配置域名/路径）
        String http = fileBaseUrl + "/files/download/";

        // 构造标准响应结构（严格遵循wangEditor接口规范）
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("errno", 0);  // 固定成功状态码
        resMap.put("data", CollUtil.newArrayList(
                Dict.create().set("url", http + flag + "-" + fileName)  // 生成可直接访问的URL
        ));
        return resMap;
    }

}