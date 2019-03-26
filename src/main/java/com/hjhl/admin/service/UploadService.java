package com.hjhl.admin.service;

import com.hjhl.admin.util.OssUtil;
import com.hjhl.admin.util.ResultVOUtil;
import com.hjhl.admin.vo.ResultVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 创建人: Hjx
 * Date: 2019/3/20
 * Description:
 */
@Service
public class UploadService {

    public ResultVO picUpload(MultipartFile file) {
        OssUtil ossClient = new OssUtil();
        try {
            String name = ossClient.uploadImg2Oss(file);
            String imgUrl = ossClient.getImgUrl(name,
                    "image/resize,m_fixed,limit_0,w_50,h_50");
            return ResultVOUtil.Success(imgUrl);
        } catch (Exception e) {
            return ResultVOUtil.Error();
        }
    }


}
