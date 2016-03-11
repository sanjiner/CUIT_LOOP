package cuit.emergency.util;

public class Constants {

	// 服务器
	public final static String SERVER = "server";
	public final static String PORT = "port";
//	public final static String DEFAULT_SERVER = "192.168.4.100"; // 服务器ip地址
//	public final static String DEFAULT_SERVERNAME = "nikodemacbook-pro.local"; // 服务器名称
//	public final static String DEFAULT_SERVER = "192.168.0.152"; // 服务器ip地址
//	public final static String DEFAULT_SERVERNAME = "win7-20131225qt"; // 服务器名称
//	public final static String DEFAULT_SERVERNAME = "win-m48s4rvvs39"; // 服务器名称
	public static String DEFAULT_SERVER = "192.168.173.1"; // 服务器ip地址
	public static String DEFAULT_SERVERNAME = "mason-pc"; // 服务器名称
	public final static String DEFAULT_BINDSOURCE = "EMERGENCY"; // 自定义绑定资源
	public final static String DEFAULT_PORT = "5222";
	public final static int DEFAULT_PORT_INT = 5222;

	// 设置相关
	public final static String VOICE = "voice"; // 声音
	public final static String VIBRATOR = "vibrator"; // 振动
	public final static String AUTO_START = "autostart"; // 是否开机启动
	public final static String MESSAGE_NOTIFY = "message_notify"; // 进入后台是否显示新消息通知
	public final static String SECURITY = "security"; // 安全监测

	// 用户信息
	public final static String SAVE_PASSWORD = "save_password";
	public final static String SAVE_SERVER = "save_server";
	public final static String UNAME = "uname";
	public final static String UNICKNAME = "nickName";
	public final static String UPASSWD = "upasswd";
	public final static String USERVER = "userver";

	// 用户状态相关
	public final static String STATUS_MODE = "status_mode";
	public final static String UNAVAILABLE = "unavailable"; // 离线状态，没有图标
	public final static String DND = "dnd"; // 请勿打扰
	public final static String XA = "xa"; // 隐身
	public final static String AWAY = "away"; // 离开
	public final static String AVAILABLE = "available"; // 在线
	public final static String CHAT = "chat"; // Q我吧
	
	public final static String MESSAGE_ROSTER = "message.roster";  //消息列表的roster记录
	public final static String FRIEND_STATUS = "friend.status"; // 好友状态提醒
	public final static String NEW_MESSAGE = "new.message"; // 新消息
	public final static String NEW_MESSAGE_MULTI = "new.message.multi"; // 群聊新消息
	public final static String FETCH_ROOM_ENDED = "fetch.room.ended"; // 获取到了房间信息
	
	//与广播相关
	public final static String AUTO_START_BROADCAST = "cuit.emergency.auto.start";
	public final static String ACCOUNT_CONFLICT = "cuit.emergency.account.confict";
	public final static String AUTO_RECONNECT = "cuit.emergency.auto.reconnect";
	
	//消息类型
	public final static String MSG_TYPE_GROUPCHAT = "groupchat";  //群聊消息
	public final static String MSG_TYPE_CHAT = "chat";            //普通消息
	public final static String MSG_TYPE_ERROR = "error";          //错误消息
	
	//是否被注销
	public final static String IS_LOGOUT = "is_logout";
	public final static byte LOGOUT_BYHAND = 0;         //手动注销
	public final static byte LOGOUT_BYCONFLICT = 1;     //异地登录注销
	
	//重新登陆
	public final static String IS_RECONNECT = "is_reconnect";
	public final static byte RECONNECT_BYHAND = 0;         //手动重新
	public final static byte RECONNECT_AUTO = 1;          //自动
	
	
}
