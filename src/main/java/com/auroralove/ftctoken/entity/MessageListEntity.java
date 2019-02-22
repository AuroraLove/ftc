package com.auroralove.ftctoken.entity;

import com.github.pagehelper.PageInfo;
import lombok.Data;

/**
 * @author zyu
 * @date 2019/2/20
 */
@Data
public class MessageListEntity extends PageInfo {

    private PageInfo messages;

    public MessageListEntity(PageInfo messageInfos) {
        this.messages = messageInfos;
    }
}
