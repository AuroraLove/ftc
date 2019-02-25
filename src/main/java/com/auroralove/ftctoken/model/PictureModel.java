package com.auroralove.ftctoken.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zyu
 * @date 2019/2/24
 */
@Data
public class PictureModel {

    /**
     * 图片id
     */
    private Long id;

    /**
     * 帮助中心图片外键pid
     */
    private Long pid;

    /**
     * 帮助中心图片地址
     */
    private String pictureUrl;

    /**
     * 帮助中心图片
     */
    private MultipartFile picture;

    public PictureModel() {
    }

    public PictureModel(String fileName, Long pid) {
        this.pictureUrl = fileName;
        this.pid = pid;
    }
}
