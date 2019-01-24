define({ "api": [
  {
    "type": "post",
    "url": "/rest/login",
    "title": "登录",
    "name": "__",
    "group": "01.登录",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "phone",
            "description": "<p>用户手机号，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "passWord",
            "description": "<p>用户密码，非空</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": " {\n     \"success\" : \"true\",\n     \"result\" : {\n\t        \"token\":\"String,登录验证成功的时候，返回给前台并存入Cookie\"\n         \"id\" : \"用户id(Long)\",\n         \"phone\" : \"用户手机号(Integer)\",\n         \"FTCLockedAcct\" : \"FTC锁仓额度(Double)\",\n         \"FTCRewardAcct\" : \"FTC总奖励额度(Double)\",\n         \"tradeableAcct\" : \"可交易额度(Double)\",\n         \"teamId\" : \"团队id(Long)\",\n         \"level\" : \"用户级别(Integer)\",\n         \"adminStatus\" : \"用户管理级别标识(Integer)\",\n         \"version\" : \"版本信息(String)\",\n         \"totalInfo\":\"公共信息查询(String)\"\n         \"FTCPrice\":\"FTC当日价格(Double)\",\n         \"FTCSytemAcct\":\"FTC系统锁仓额度(Double)\".\n         \"EOSPrice\":{\n              \"EOS\":\"EOS值，EOS与FTC价格比(Double)\",\n              \"FTC\":\"FTC值，EOS与FTC价格比(Double)\"\n          }\n     }\n }",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "01.登录"
  },
  {
    "type": "post",
    "url": "/rest/regist",
    "title": "注册",
    "name": "__",
    "group": "02.注册",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": false,
            "field": "id",
            "description": "<p>用户id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "passWord",
            "description": "<p>用户密码，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>手机验证码，非空</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n  \"status\": 517,\n  \"message\": \"注册手机号已存在\",\n  \"result\": false,\n  \"timestamp\": \"2019-01-23 10:41:43\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "02.注册"
  },
  {
    "type": "post",
    "url": "/rest/home/rewardRecord",
    "title": "分佣奖励记录查询",
    "name": "________",
    "group": "03.个人中心（分佣奖励记录查询）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "long",
            "optional": false,
            "field": "id",
            "description": "<p>用户id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageNum",
            "description": "<p>当前页，默认1</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageSize",
            "description": "<p>分页大小，默认10</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" : {\n        \"id\" : \"用户id\",\n        \"phone\" : \"用户手机号\",\n        \"level\" : \"用户级别\",\n        \"rewardDate\" : \"获取佣金时间\"\n      }\n\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "03.个人中心（分佣奖励记录查询）"
  },
  {
    "type": "post",
    "url": "/rest/home/commutableAssets",
    "title": "可交易资产",
    "name": "_____",
    "group": "04.个人中心（可交易资产）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": false,
            "field": "id",
            "description": "<p>用户Id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageNum",
            "description": "<p>当前页，默认1</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageSize",
            "description": "<p>分页大小，默认10</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" : {\n        \"id\" : \"用户id\",\n        \"phone\" : \"用户手机号\",\n        \"tradingStatus\" : \"交易状态(Integer)\",\n        \"tadingDate\" : \"交易时间(Date)\"\n      }\n\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "04.个人中心（可交易资产）"
  },
  {
    "type": "post",
    "url": "/rest/home/uerData",
    "title": "用户交易资料，银卡卡号，支付宝，微信前台校验三者必须有一种不为空",
    "name": "______",
    "group": "05.个人中心（用户交易资料）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": false,
            "field": "id",
            "description": "<p>用户id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "bankCard",
            "description": "<p>银行卡号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "aliPayAcct",
            "description": "<p>支付宝账号</p>"
          },
          {
            "group": "Parameter",
            "type": "MultipartFile",
            "optional": false,
            "field": "aliPayCode",
            "description": "<p>支付宝二维码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "wechatPayAcct",
            "description": "<p>微信支付账号</p>"
          },
          {
            "group": "Parameter",
            "type": "MultipartFile",
            "optional": false,
            "field": "wehatPayCode",
            "description": "<p>微信支付二维码</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" : {\n        \"flag\" : \"true or false\"\n      }\n\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "05.个人中心（用户交易资料）"
  },
  {
    "type": "post",
    "url": "/rest/home/team",
    "title": "我的团队",
    "name": "____",
    "group": "06.个人中心（我的团队）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": false,
            "field": "id",
            "description": "<p>用户id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" : {\n        \"id\" : \"用户id(Long)\",\n        \"phone\" : \"用户手机号(Integer)\",\n        \"levelList\" : [\n        {\n            \"level\":\"级别人数(Integer)\"\n        },\n        ...\n        ]，\n        \"rewardMessage\" : \"奖励信息说明\"\n      }\n\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "06.个人中心（我的团队）"
  },
  {
    "type": "post",
    "url": "/rest/home/teamInfo",
    "title": "团队详情",
    "name": "____",
    "group": "07.个人中心（团队详情）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "teamId",
            "description": "<p>团队id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" : [{\n        \"phone\" : \"用户手机号(Integer)\",\n        \"level\" : \"用户级别(Integer)\"，\n        \"relation\":\"从属关系（Integer）\"\n      },\n      ...\n    ]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "07.个人中心（团队详情）"
  },
  {
    "type": "post",
    "url": "/rest/home/messageInfo",
    "title": "留言簿信息",
    "name": "_____",
    "group": "08.个人中心（留言簿信息）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": false,
            "field": "id",
            "description": "<p>用户id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" : [{\n        \"leavingMsg\" : \"留言信息(String)\",\n        \"leavingDate\" : \"留言日期(Date)\"，\n        \"replayMsg\":\"回复信息（String）\",\n        \"replayDate\":\"回复日期（Date）\"\n      },\n      ...\n    ]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "08.个人中心（留言簿信息）"
  },
  {
    "type": "post",
    "url": "/rest/home/uploadMsg",
    "title": "上传留言",
    "name": "____",
    "group": "09.个人中心（上传留言）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": false,
            "field": "id",
            "description": "<p>用户id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>留言信息，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "MultipartFile",
            "optional": false,
            "field": "picture",
            "description": "<p>留言凭证</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" : {\n        \"flag\":\"true or flase\"\n    }\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "09.个人中心（上传留言）"
  },
  {
    "type": "post",
    "url": "/rest/home/changePayPwd",
    "title": "修改支付密码",
    "name": "______",
    "group": "10.个人中心（修改支付密码）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": false,
            "field": "id",
            "description": "<p>用户id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "payPwd",
            "description": "<p>支付密码，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" : {\n        \"flag\":\"true or flase\"\n    }\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "10.个人中心（修改支付密码）"
  },
  {
    "type": "post",
    "url": "/rest/home/changeLoginPwd",
    "title": "修改登录密码",
    "name": "______",
    "group": "11.个人中心（修改支付密码）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": false,
            "field": "id",
            "description": "<p>用户id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "loginPwd",
            "description": "<p>支付密码，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" : {\n        \"flag\":\"true or flase\"\n    }\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "11.个人中心（修改支付密码）"
  },
  {
    "type": "post",
    "url": "/rest/transaction/price",
    "title": "交易价格查询",
    "name": "______",
    "group": "12.交易大厅（交易价格查询）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": false,
            "field": "id",
            "description": "<p>用户id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" : [{\n        \"price\" : \"价格(Double)\",\n        \"amount\" : \"数量(Double)\"，\n        \"trctFlag\":\"买入卖出标识（Integer,0为买，1为卖）\"\n      },\n      ...\n    ]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "12.交易大厅（交易价格查询）"
  },
  {
    "type": "post",
    "url": "/rest/transaction/deal",
    "title": "交易",
    "name": "_____or__",
    "group": "13.交易大厅（交易）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": false,
            "field": "id",
            "description": "<p>用户id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "Ineger",
            "optional": false,
            "field": "trctFlag",
            "description": "<p>0为买，1为卖，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "Double",
            "optional": false,
            "field": "price",
            "description": "<p>价格，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "Double",
            "optional": false,
            "field": "amount",
            "description": "<p>数量，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" : {\n        \"flag\":\"true or false\"\n    }\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "13.交易大厅（交易）"
  },
  {
    "type": "post",
    "url": "/rest/myDeal/record",
    "title": "交易记录查询",
    "name": "______",
    "group": "14.我的交易（交易记录查询）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "long",
            "optional": false,
            "field": "id",
            "description": "<p>用户id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageNum",
            "description": "<p>当前页，默认1</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageSize",
            "description": "<p>分页大小，默认10</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" : [\n    {\n        \"id\" : \"用户id(Long)\",\n        \"phone\" : \"用户手机号(Ineteger)\",\n        \"flowId\":\"单笔流水id(Long),交易类别为买时（0）为买方流水id，反之为卖方\"，\n        \"orderId\":\"订单id（Long）,状态不为匹配中时才有值\",\n        \"dealType\" : \"交易类别（Integer）,0，买，1，卖 \"\n        \"price\" : \"价格(Double)\"，\n        \"amount\":\"数量(Double)\"，\n        \"dealDate\":\"交易日期（Date）\",\n        \"actionStatus\":\"操作状态（Integer）,0,交易中（匹配中），1，以完成\"，2，已撤销，3，已冻结\"\n        \"matchStatus\":\"匹配订单状态(Integer)，0，未匹配到，1，待支付，2，已支付未确认收款，3，确认收款，4，订单完成\"\n     },\n     ...\n     ]\n\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "14.我的交易（交易记录查询）"
  },
  {
    "type": "post",
    "url": "/rest/myDeal/orderInfo",
    "title": "订单详情查询",
    "name": "______",
    "group": "16.我的交易（订单详情查询）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": false,
            "field": "orderId",
            "description": "<p>订单id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" :\n    {\n        \"orderId\" : \"订单id(Long)\",\n        \"buyerId\" :\"买方id（Long）\",\n        \"sellerId\":\"卖方id（Long）\",\n        \"buyerNmae\":\"买方实名(String)\",\n        \"buyerPhone\" : \"买方手机号(Integer)\"，\n        \"CNY\":\"单价(Double)\"，\n        \"amount\":\"数量(Double)\"，\n        \"orderId\":\"订单id（Long）,只有matchStatus不为0时，才有订单id\"，\n        \"orderTime\":\"下单时间（Date）\",\n        \"payTime\":\"支付时间（Date）\",\n        \"payTime\":\"放币时间（Date）\",\n        \"sellerName\":\"卖方实名（String）\"，\n        \"orderStatus\":\"订单状态（Integer），1，待支付，2，已支付未确认收款，3，确认收款，4，订单完成\"，\n        \"payType\":[\n           {\"0\":{\n             \"bankName\":\"银行名称（String）\",\n             \"name\":\"银行卡实名（String）\",\n             \"bankCard\":\"银行卡号(String)\",\n             \"bankMsg\":\"银行地址(String)\",\n           }},\n           {\"1\":{\n              \"name\":\"支付宝实名(String)\",\n              \"phoneNum\":\"支付宝手机号(Integer)\"\n            }},\n           {\"2\":{\n              \"name\":\"支付宝实名(String)\",\n              \"phoneNum\":\"支付宝手机号(Integer)\"\n            }},\n        ]\"\n     }\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "16.我的交易（订单详情查询）"
  },
  {
    "type": "post",
    "url": "/rest/myDeal/orderStatus",
    "title": "修改订单状态",
    "name": "______",
    "group": "17.我的交易（修改订单状态）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": false,
            "field": "orderId",
            "description": "<p>订单id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "Ineger",
            "optional": false,
            "field": "orderStatus",
            "description": "<p>订单状态，非空，工作流推进对应工作状态，1，待支付，2，已支付未确认收款，3，确认收款，4，订单完成</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" :\n    {\n        \"orderId\" : \"订单id(Long)\",\n        \"buyerId\" :\"买方id（Long）\",\n        \"sellerId\":\"卖方id（Long）\",\n        \"buyerNmae\":\"买方实名(String)\",\n        \"buyerPhone\" : \"买方手机号(Integer)\"，\n        \"CNY\":\"单价(Double)\"，\n        \"amount\":\"数量(Double)\"，\n        \"orderId\":\"订单id（Long）,只有matchStatus不为0时，才有订单id\"，\n        \"orderTime\":\"下单时间（Date）\",\n        \"payTime\":\"支付时间（Date）\",\n        \"payTime\":\"放币时间（Date）\",\n        \"sellerName\":\"卖方实名（String）\"，\n        \"orderStatus\":\"订单状态（Integer），1，待支付，2，已支付未确认收款，3，确认收款，4，订单完成\"，\n        \"payType\":[\n           {\"0\":{\n             \"bankName\":\"银行名称（String）\",\n             \"name\":\"银行卡实名（String）\",\n             \"cardNum\":\"银行卡号(Integer)\",\n             \"bankMsg\":\"银行地址(String)\",\n           }},\n           {\"1\":{\n              \"name\":\"支付宝实名(String)\",\n              \"phoneNum\":\"支付宝手机号(Integer)\"\n            }},\n           {\"2\":{\n              \"name\":\"微信实名(String)\",\n              \"phoneNum\":\"微信手机号(Integer)\"\n            }},\n        ]\"\n     }\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "17.我的交易（修改订单状态）"
  },
  {
    "type": "post",
    "url": "/rest/myDeal/getCode",
    "title": "获取二维码",
    "name": "_____",
    "group": "18.个人中心（获取二维码）",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Long",
            "optional": false,
            "field": "sellerId",
            "description": "<p>卖方Id，非空</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "payType",
            "description": "<p>0,银联，1，支付宝，2，微信</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "result",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\" : \"true\",\n    \"result\" : {\n        \"codeUrl\" : \"二维码文件地址（String）\"\n      }\n\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.1.0",
    "filename": "src/demo.java",
    "groupTitle": "18.个人中心（获取二维码）"
  }
] });
