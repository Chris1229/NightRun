package com.quncao.http;


import com.quncao.core.http.annotation.HttpReqParam;

/**
 * 
 * @Description: 获取我的收藏
 * 
 * @author zhengtianyin
 * @date 2016-2-2 下午16:45:53
 * @version V4.0
 */

@HttpReqParam(responseType = HttpGetDialogConfigRequest.class, protocal = "http://192.168.30.6/hybrid/h5zip/raw/develop/app/config_dialog.json")
public class HttpGetDialogConfigRequest {
	
	/*
	 * 每页返回多少条记录
	 */
	private int requestCnt;
	
	/*
	 * 请求的页数
	 */
	private int pageNum;
	
	/*
	 * 吐槽的用户
	 */
	private String uid;

	public int getRequestCnt() {
		return requestCnt;
	}

	public void setRequestCnt(int requestCnt) {
		this.requestCnt = requestCnt;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
}
