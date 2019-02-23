package com.auroralove.ftctoken.result;

import com.github.pagehelper.PageInfo;
import lombok.Data;

@Data
public class DealResult extends ResponseResult {

    /**
     * 未完成订单
     */
    private Integer unfinished;

    /**
     * 订单数
     */
    private Integer totalCount;

    public DealResult(ResponseMessage rm, PageInfo result) {
        super(rm,result);
    }
}
