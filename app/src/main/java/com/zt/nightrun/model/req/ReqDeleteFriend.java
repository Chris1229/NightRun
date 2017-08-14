package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespDeleteFriend;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/7 - 下午10:06.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespDeleteFriend.class, protocal = "device/deleteDeviceFriend.do")
public class ReqDeleteFriend implements Serializable {
    private int friendId;

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    @Override
    public String toString() {
        return "ReqDeleteFriend{" +
                "friendId=" + friendId +
                '}';
    }
}
