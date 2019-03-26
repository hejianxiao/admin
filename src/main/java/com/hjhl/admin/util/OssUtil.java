package com.hjhl.admin.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.hjhl.admin.constant.OssConstant;
import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;

/**
 * @author:zhs
 * @date 2018/8/21 16:14
 */
@Slf4j
@Component
public class OssUtil {
    private static OssConstant oss;
    @Resource
    private OssConstant ossConstant;

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @return String
     * @para
     */
    public static String getcontentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase("jpeg") || filenameExtension.equalsIgnoreCase("jpg")
                || filenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }


    /**
     * 删除存储空间buckName
     *
     * @param bucketName oss对象
     * @param bucketName 存储空间
     */
    public void deleteBucket(String bucketName) {
        OSSClient ossClient = new OSSClient(oss.getEndpoint(), oss.getAccessKeyId(), oss.getAccessKeySecret());
        ossClient.deleteBucket(bucketName);
        log.info("删除" + bucketName + "Bucket成功");
    }

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        oss = ossConstant;

    }

    /**
     * 销毁
     */
    public void destory() {
        OSSClient ossClient = new OSSClient(oss.getEndpoint(), oss.getAccessKeyId(), oss.getAccessKeySecret());
        ossClient.shutdown();
    }

    /**
     * 上传图片
     *
     * @param url
     */
    public void uploadImg2Oss(String url) throws GlobalException {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2OSS(fin, split[split.length - 1]);
        } catch ( FileNotFoundException e ) {
            throw new GlobalException(ResultEnum.OSS_IMAGE_ERROR);
        }
    }

    public String uploadImg2Oss(MultipartFile file) throws GlobalException {
        if (file.getSize() > 2 * 1024 * 1024) {
            throw new GlobalException(ResultEnum.OSS_IMAGE_MAX);
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name);
            return name;
        } catch ( Exception e ) {
            throw new GlobalException(ResultEnum.OSS_IMAGE_ERROR);
        }
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     */
    public String getImgUrl(String fileUrl, String process) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(oss.getFiledir() +"/" +split[split.length - 1], process);
        }
        return null;
    }

    /**
     * 上传到OSS服务器 如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream instream, String fileName) {
        OSSClient ossClient = new OSSClient(oss.getEndpoint(), oss.getAccessKeyId(), oss.getAccessKeySecret());
        String ret = "";
        try {
            if (!ossClient.doesBucketExist(oss.getBucketName())) {
                //创建存储空间
                Bucket bucket = ossClient.createBucket(oss.getBucketName());
                log.info("创建存储空间成功");
            }
            final String keySuffixWithSlash = oss.getFiledir();
            //判断文件夹是否存在，不存在则创建
            if (!ossClient.doesObjectExist(oss.getBucketName(), keySuffixWithSlash)) {
                //创建文件夹
                ossClient.putObject(oss.getBucketName(), keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
                log.info("创建文件夹成功");
            }
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);

            // 上传文件
            PutObjectRequest request = new PutObjectRequest(oss.getBucketName(), oss.getFiledir()+"/"+fileName, instream, objectMetadata);
            PutObjectResult putResult = ossClient.putObject(request);
            ret = putResult.getETag();
        } catch ( IOException e ) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 获得url链接
     */
    public String getUrl(String key, String process) {

        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        OSSClient ossClient = new OSSClient(oss.getEndpoint(), oss.getAccessKeyId(), oss.getAccessKeySecret());
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(oss.getBucketName(), key);
        urlRequest.setExpiration(expiration);
        urlRequest.setProcess(process);

        // 生成URL
        URL url = ossClient.generatePresignedUrl(urlRequest);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

//    public String getObject(String fileName) {
//        OSSClient ossClient = new OSSClient(oss.getEndpoint(), oss.getAccessKeyId(), oss.getAccessKeySecret());
//
//        GetObjectRequest getObjectRequest = new GetObjectRequest(oss.getBucketName(), oss.getFiledir()+"/"+fileName);
//
//        ossClient.getObject()
//
//    }
}
