/**
 * @api {post} /rest/login 登录
 * @apiName 登录
 * @apiGroup 01.登录
 * @apiParam {Integer} phone 用户手机号，非空
 * @apiParam {String} passWord 用户密码，非空
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" : {
	        "token":"String,登录验证成功的时候，返回给前台并存入Cookie"
 *          "id" : "用户id(Long)",
 *          "phone" : "用户手机号(Integer)",
 *          "FTCLockedAcct" : "FTC锁仓额度(Double)",
 *          "FTCRewardAcct" : "FTC总奖励额度(Double)",
 *          "tradeableAcct" : "可交易额度(Double)",
 *          "teamId" : "团队id(Long)",
 *          "level" : "用户级别(Integer)",
 *          "adminStatus" : "用户管理级别标识(Integer)",
 *          "version" : "版本信息(String)",
 *          "totalInfo":"公共信息查询(String)"
 *          "FTCPrice":"FTC当日价格(Double)",
 *          "FTCSytemAcct":"FTC系统锁仓额度(Double)".
 *          "EOSPrice":{
 *               "EOS":"EOS值，EOS与FTC价格比(Double)",
 *               "FTC":"FTC值，EOS与FTC价格比(Double)"
 *           }
 *      }
 *  }
 * @apiVersion 0.1.0
 */

/**
 * @api {post} /rest/regist 注册
 * @apiName 注册
 * @apiGroup 02.注册
 * @apiParam {Long} id 用户id，非空
 * @apiParam {String} passWord 用户密码，非空
 * @apiParam {Integer} code 手机验证码，非空
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
{
  "status": 517,
  "message": "注册手机号已存在",
  "result": false,
  "timestamp": "2019-01-23 10:41:43"
}
 * @apiVersion 0.1.0
 */


/**
 * @api {post} /rest/home/rewardRecord 分佣奖励记录查询
 * @apiName 分佣奖励记录查询
 * @apiGroup 03.个人中心（分佣奖励记录查询）
 * @apiParam {long} id 用户id，非空
 * @apiParam {Integer} pageNum 当前页，默认1
 * @apiParam {Integer} pageSize 分页大小，默认10
 * @apiParam {String} token
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" : {
 *          "id" : "用户id",
 *          "phone" : "用户手机号",
 *          "level" : "用户级别",
 *          "rewardDate" : "获取佣金时间"
 *        }
 *
 *  }
 * @apiVersion 0.1.0
 */

/**
 * @api {post} /rest/home/commutableAssets 可交易资产
 * @apiName 可交易资产
 * @apiGroup 04.个人中心（可交易资产）
 * @apiParam {Long} id 用户Id，非空
 * @apiParam {Integer} pageNum 当前页，默认1
 * @apiParam {Integer} pageSize 分页大小，默认10
 * @apiParam {String} token
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" : {
 *          "id" : "用户id",
 *          "phone" : "用户手机号",
 *          "tradingStatus" : "交易状态(Integer)",
 *          "tadingDate" : "交易时间(Date)"
 *        }
 *
 *  }
 * @apiVersion 0.1.0
 */

/**
 * @api {post} /rest/home/uerData 用户交易资料，银卡卡号，支付宝，微信前台校验三者必须有一种不为空
 * @apiName 用户交易资料
 * @apiGroup 05.个人中心（用户交易资料）
 * @apiParam {Long} id 用户id，非空
 * @apiParam {String} bankCard 银行卡号
 * @apiParam {String} aliPayAcct 支付宝账号
 * @apiParam {MultipartFile} aliPayCode 支付宝二维码
 * @apiParam {String} wechatPayAcct 微信支付账号
 * @apiParam {MultipartFile} wehatPayCode 微信支付二维码
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" : {
 *          "flag" : "true or false"
 *        }
 *
 *  }
 * @apiVersion 0.1.0
 */


/**
 * @api {post} /rest/home/team 我的团队
 * @apiName 我的团队
 * @apiGroup 06.个人中心（我的团队）
 * @apiParam {Long} id 用户id
 * @apiParam {String} token
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" : {
 *          "id" : "用户id(Long)",
 *          "phone" : "用户手机号(Integer)",
 *          "levelList" : [
 *          {
 *              "level":"级别人数(Integer)"
 *          },
 *          ...
 *          ]，
 *          "rewardMessage" : "奖励信息说明"
 *        }
 *
 *  }
 * @apiVersion 0.1.0
 */

/**
 * @api {post} /rest/home/teamInfo 团队详情
 * @apiName 团队详情
 * @apiGroup 07.个人中心（团队详情）
 * @apiParam {Integer} teamId 团队id，非空
 * @apiParam {String} token
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" : [{
 *          "phone" : "用户手机号(Integer)",
 *          "level" : "用户级别(Integer)"，
 *          "relation":"从属关系（Integer）"
 *        },
 *        ...
 *      ]
 *  }
 * @apiVersion 0.1.0
 */

/**
 * @api {post} /rest/home/messageInfo 留言簿信息
 * @apiName 留言簿信息
 * @apiGroup 08.个人中心（留言簿信息）
 * @apiParam {Long} id 用户id，非空
 * @apiParam {String} token
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" : [{
 *          "leavingMsg" : "留言信息(String)",
 *          "leavingDate" : "留言日期(Date)"，
 *          "replayMsg":"回复信息（String）",
 *          "replayDate":"回复日期（Date）"
 *        },
 *        ...
 *      ]
 *  }
 * @apiVersion 0.1.0
 */


/**
 * @api {post} /rest/home/uploadMsg 上传留言
 * @apiName 上传留言
 * @apiGroup 09.个人中心（上传留言）
 * @apiParam {Long} id 用户id，非空
 * @apiParam {String} message 留言信息，非空
 * @apiParam {MultipartFile} picture 留言凭证
 * @apiParam {String} token
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" : {
 *          "flag":"true or flase"
 *      }
 *  }
 * @apiVersion 0.1.0
 */

/**
 * @api {post} /rest/home/changePayPwd 修改支付密码
 * @apiName 修改支付密码
 * @apiGroup 10.个人中心（修改支付密码）
 * @apiParam {Long} id 用户id，非空
 * @apiParam {String} payPwd 支付密码，非空
 * @apiParam {String} token
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" : {
 *          "flag":"true or flase"
 *      }
 *  }
 * @apiVersion 0.1.0
 */

/**
 * @api {post} /rest/home/changeLoginPwd 修改登录密码
 * @apiName 修改支付密码
 * @apiGroup 11.个人中心（修改支付密码）
 * @apiParam {Long} id 用户id，非空
 * @apiParam {String} loginPwd 支付密码，非空
 * @apiParam {String} token
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" : {
 *          "flag":"true or flase"
 *      }
 *  }
 * @apiVersion 0.1.0
 */

/**
 * @api {post} /rest/transaction/price 交易价格查询
 * @apiName 交易价格查询
 * @apiGroup 12.交易大厅（交易价格查询）
 * @apiParam {Long} id 用户id，非空
 * @apiParam {String} token
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" : [{
 *          "price" : "价格(Double)",
 *          "amount" : "数量(Double)"，
 *          "trctFlag":"买入卖出标识（Integer,0为买，1为卖）"
 *        },
 *        ...
 *      ]
 *  }
 * @apiVersion 0.1.0
 */

/**
 * @api {post} /rest/transaction/deal 交易
 * @apiName 交易,买入or卖出
 * @apiGroup 13.交易大厅（交易）
 * @apiParam {Long} id 用户id，非空
 * @apiParam {Ineger} trctFlag 0为买，1为卖，非空
 * @apiParam {Double} price 价格，非空
 * @apiParam {Double} amount 数量，非空
 * @apiParam {String} token
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" : {
 *          "flag":"true or false"
 *      }
 *  }
 * @apiVersion 0.1.0
 */

/**
 * @api {post} /rest/myDeal/record 交易记录查询
 * @apiName 交易记录查询
 * @apiGroup 14.我的交易（交易记录查询）
 * @apiParam {long} id 用户id，非空
 * @apiParam {Integer} pageNum 当前页，默认1
 * @apiParam {Integer} pageSize 分页大小，默认10
 * @apiParam {String} token
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" : [
 *      {
 *          "id" : "用户id(Long)",
 *          "phone" : "用户手机号(Ineteger)",
 *          "flowId":"单笔流水id(Long),交易类别为买时（0）为买方流水id，反之为卖方"，
 *          "orderId":"订单id（Long）,状态不为匹配中时才有值",
 *          "dealType" : "交易类别（Integer）,0，买，1，卖 "
 *          "price" : "价格(Double)"，
 *          "amount":"数量(Double)"，
 *          "dealDate":"交易日期（Date）",
 *          "actionStatus":"操作状态（Integer）,0,交易中（匹配中），1，以完成"，2，已撤销，3，已冻结"
 *          "matchStatus":"匹配订单状态(Integer)，0，未匹配到，1，待支付，2，已支付未确认收款，3，确认收款，4，订单完成"
 *       },
 *       ...
 *       ]
 *
 *  }
 * @apiVersion 0.1.0
 */




/**
 * @api {post} /rest/myDeal/orderInfo 订单详情查询
 * @apiName 订单详情查询
 * @apiGroup 16.我的交易（订单详情查询）
 * @apiParam {Long} orderId 订单id，非空
 * @apiParam {String} token
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" :
 *      {
 *          "orderId" : "订单id(Long)",
 *          "buyerId" :"买方id（Long）",
 *          "sellerId":"卖方id（Long）",
 *          "buyerNmae":"买方实名(String)",
 *          "buyerPhone" : "买方手机号(Integer)"，
 *          "CNY":"单价(Double)"，
 *          "amount":"数量(Double)"，
 *          "orderId":"订单id（Long）,只有matchStatus不为0时，才有订单id"，
 *          "orderTime":"下单时间（Date）",
 *          "payTime":"支付时间（Date）",
 *          "payTime":"放币时间（Date）",
 *          "sellerName":"卖方实名（String）"，
 *          "orderStatus":"订单状态（Integer），1，待支付，2，已支付未确认收款，3，确认收款，4，订单完成"，
 *          "payType":[
 *             {"0":{
 *               "bankName":"银行名称（String）",
 *               "name":"银行卡实名（String）",
 *               "bankCard":"银行卡号(String)",
 *               "bankMsg":"银行地址(String)",
 *             }},
 *             {"1":{
 *                "name":"支付宝实名(String)",
 *                "phoneNum":"支付宝手机号(Integer)"
 *              }},
 *             {"2":{
 *                "name":"支付宝实名(String)",
 *                "phoneNum":"支付宝手机号(Integer)"
 *              }},
 *          ]"
 *       }
 *  }
 * @apiVersion 0.1.0
 */

/**
 * @api {post} /rest/myDeal/orderStatus 修改订单状态
 * @apiName 修改订单状态
 * @apiGroup 17.我的交易（修改订单状态）
 * @apiParam {Long} orderId 订单id，非空
 * @apiParam {Ineger} orderStatus 订单状态，非空，工作流推进对应工作状态，1，待支付，2，已支付未确认收款，3，确认收款，4，订单完成
 * @apiParam {String} token
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" :
 *      {
 *          "orderId" : "订单id(Long)",
 *          "buyerId" :"买方id（Long）",
 *          "sellerId":"卖方id（Long）",
 *          "buyerNmae":"买方实名(String)",
 *          "buyerPhone" : "买方手机号(Integer)"，
 *          "CNY":"单价(Double)"，
 *          "amount":"数量(Double)"，
 *          "orderId":"订单id（Long）,只有matchStatus不为0时，才有订单id"，
 *          "orderTime":"下单时间（Date）",
 *          "payTime":"支付时间（Date）",
 *          "payTime":"放币时间（Date）",
 *          "sellerName":"卖方实名（String）"，
 *          "orderStatus":"订单状态（Integer），1，待支付，2，已支付未确认收款，3，确认收款，4，订单完成"，
 *          "payType":[
 *             {"0":{
 *               "bankName":"银行名称（String）",
 *               "name":"银行卡实名（String）",
 *               "cardNum":"银行卡号(Integer)",
 *               "bankMsg":"银行地址(String)",
 *             }},
 *             {"1":{
 *                "name":"支付宝实名(String)",
 *                "phoneNum":"支付宝手机号(Integer)"
 *              }},
 *             {"2":{
 *                "name":"微信实名(String)",
 *                "phoneNum":"微信手机号(Integer)"
 *              }},
 *          ]"
 *       }
 *  }
 * @apiVersion 0.1.0
 */


/**
 * @api {post} /rest/myDeal/getCode 获取二维码
 * @apiName 获取二维码
 * @apiGroup 18.个人中心（获取二维码）
 * @apiParam {Long} sellerId 卖方Id，非空
 * @apiParam {Integer} payType 0,银联，1，支付宝，2，微信
 * @apiParam {String} token
 *
 * @apiSuccess {json} result
 * @apiSuccessExample {json} Success-Response:
 *  {
 *      "success" : "true",
 *      "result" : {
 *          "codeUrl" : "二维码文件地址（String）"
 *        }
 *
 *  }
 * @apiVersion 0.1.0
 */