package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespMineTeamList;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/14 - 下午8:15.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespMineTeamList.class, protocal = "group/getMine.do")
public class ReqMineTeamList implements Serializable {

}
