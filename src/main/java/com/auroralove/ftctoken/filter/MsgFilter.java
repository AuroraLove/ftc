package com.auroralove.ftctoken.filter;

import lombok.Data;

/**
 * 用户留言过滤器
 *
 * @author zyu
 * @date 2019/1/30
 */
@Data
public class MsgFilter {

    /**
     * 留言id
     */
    private Long mid;

    /**
     * 回复留言信息
     */
    private String message;

    /**
     * 留言类型，11，留言，12，回复留言
     */
    private Integer mType;
}
