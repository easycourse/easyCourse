package com.easyCourse.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.easyCourse.entity.OSSFile;


import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SPG
 * @version 1.0
 * @time 2019-05-07-15:29
 */
public class OSSClientUtil {


    public static String ENDPOINT = "oss-cn-hangzhou.aliyuncs.com";
    // LTAIsiYRN51xsn0b
    public static String ACCESSKEYID = "LTAIsiYRN51xsn0b";
    // Sz6jBb39XIHJHniCHsJ6wvWDnW1HAP
    public static String ACCESSKEYSECRET = "Sz6jBb39XIHJHniCHsJ6wvWDnW1HAP";
    // spg-test
    public static String BUCKETNAME = "easycoursefile";

    // 格式化最后一次修改时间
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

    // 获取ossClient
    public static OSSClient ossClientInit() {
        return new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
    }


    // 是否存在该Bucket
    public static boolean hasBucket(OSSClient ossClient) {
        return ossClient.doesBucketExist(BUCKETNAME);
    }

    /**
     * 创建文件名
     *
     * @param name 名称
     * @return 完整文件名
     */
    public static String createFileName(String name) {
        return name;
    }

    /**
     * 上传文件
     *
     * @param is       文件输入流
     * @param fileName 文件名
     * @return
     */
    public static String uploadFile(InputStream is, String fileName) {
        String result = "";
        try {
            OSSClient ossClient = ossClientInit();
            // 创建上传对象的元数据
            ObjectMetadata metadata = new ObjectMetadata();
            // 上传文件的长度

            metadata.setContentLength(is.available());
            //上传文件
            //PutObjectRequest request = new PutObjectRequest(BUCKETNAME, fileName, is);

            ossClient.putObject(BUCKETNAME, fileName, is);
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 创建存储空间
     */
    public static void init() {
        String temp_bucketName = "";
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        // 创建存储空间。
        ossClient.createBucket(temp_bucketName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 列举所有的文件
     *
     * @return
     */
    public static List<OSSFile> showFiles() {

        // 文件名前缀，这里列举所有的文件
        String KeyPrefix = "";
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        // 列举文件
        ObjectListing objectListing = ossClient.listObjects(BUCKETNAME, KeyPrefix);

        List<OSSFile> ossFiles = new ArrayList<>();
        for (OSSObjectSummary o : objectListing.getObjectSummaries()) {
            ossFiles.add(new OSSFile(o.getKey(),
                    calculateSize(o.getSize()),
                    getURL(o),
                    simpleDateFormat.format(o.getLastModified())));
        }
        return ossFiles;
    }

    // 计算文件大小
    public static String calculateSize(long size) {
        StringBuilder sb = new StringBuilder();
        if (size < 1024) {
            sb.append(size).append(" B");
        } else if (size < 1048576) {
            sb.append(String.format("%.3f", size / 1024.0)).append(" KB");
        } else {
            sb.append(String.format("%.3f", size / 1048576.0)).append(" MB");
        }
        return sb.toString();
    }

    // 给出文件下载URL
    public static String getURL(OSSObjectSummary o) {
        return "http://" + o.getBucketName() + "." + ENDPOINT + "/" + o.getKey();
    }

    public static String getURL(String fileName) {
        return "http://" + BUCKETNAME + "." + ENDPOINT + "/" + fileName;
    }

    public static void main(String[] args) {
    }
}
