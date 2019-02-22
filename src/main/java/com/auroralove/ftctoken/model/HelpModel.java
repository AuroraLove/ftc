package com.auroralove.ftctoken.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author zyu
 * @date 2019/2/16
 */
@Data
public class HelpModel {

    /**
     * 目录id
     */
    private Long hid;

    /**
     * 图片id
     */
    private Long pid;

    /**
     * 目录
     */
    private String catalog;

    /**
     * 目录内容
     */
    private String content;

    /**
     * 内容图片地址
     */
    private List<String> pictureUrl;
    /**
     * 内容图片
     */
    private List<MultipartFile> pictures;
}
