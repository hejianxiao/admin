package com.hjhl.admin.controller.upload;

import com.hjhl.admin.service.UploadService;
import com.hjhl.admin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 创建人: Hjx
 * Date: 2019/3/19
 * Description:
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    private UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/pic")
    public ResultVO upload(MultipartFile file) {
        return uploadService.picUpload(file);
    }

}
