package com.zt.nightrun.blue.utils;

public class ConstantUtils {
	//消息类型
	public final static int WM_STOP_SCAN_BLE=1;
	public final static int WM_UPDATE_BLE_LIST=2;
	//蓝牙连接状态改变
	public final static int  WM_BLE_CONNECTED_STATE_CHANGE=3;
	//接受到蓝牙发来的消息
	public final static int WM_RECEIVE_MSG_FROM_BLE=4;
	//断开连接或未连接成功
	public final static int WM_STOP_CONNECT=5;

	//intent的action们
	public final static String ACTION_UPDATE_DEVICE_LIST="action.update.device.list";//更新设备列表
	public final static String ACTION_CONNECTED_ONE_DEVICE="action.connected.one.device";//连接上某个设备时发的广播
	public final static String ACTION_RECEIVE_MESSAGE_FROM_DEVICE="action.receive.message";
	public final static String ACTION_STOP_CONNECT="action.stop.connect";


	//UUID	
	public final static String UUID_SERVER="6e400001-b5a3-f393-e0a9-e50e24dcca9e";
	public final static String UUID_NOTIFY="6e400002-b5a3-f393-e0a9-e50e24dcca9e";

}
