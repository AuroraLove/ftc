package com.auroralove.ftctoken.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.auroralove.ftctoken.model.DealModel;


/**
 * @author lvyj
 *
 */
public class SysCache
{
		//存取买方订单
		public static BlockingQueue<DealModel> blockP = new LinkedBlockingQueue<>();
		//存取卖方订单
		public static BlockingQueue<DealModel> blockS = new LinkedBlockingQueue<>();
}
