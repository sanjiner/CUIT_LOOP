package cuit.emergency.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper{
	
	public static final String DB_NAME = "messenger.db"; //数据库文件名
	public static int DB_VERSION = 2;

	public static final String ROSTER_TABLE = "roster";  //好友表
	public static final String R_CUR_ROSTER = "cur_roster";   // 当前登录用户
	public static final String R_ROSTER = "chat_roster";           //好友名称
	public static final String R_NICKNAME = "chat_nickname";         //好友昵称
	public static final String R_GROUP_NAME = "groupname";           //所属组名
	public static final String R_GROUP_POS = "grouppos";           //所属组排位
	public static final String R_MODE = "roster_mode";        //好友状态
	public static final String CREATE_ROSTER = "CREATE TABLE IF NOT exists ["+ROSTER_TABLE+"] ([cur_roster] VARCHAR(50),"
			+ "[chat_roster] VARCHAR(60),[chat_nickname] VARCHAR(60), [groupname] VARCHAR(60), [grouppos] VARCHAR(60), [roster_mode] VARCHAR(20))";
	
	public static final String ROOM_TABLE = "room";       //群组表
	public static final String ROOM_ID = "room_id";       //当前群的id
	public static final String ROOM_NAME = "room_name";   //群名称
	public static final String CREATE_ROOM = "CREATE TABLE IF NOT exists ["+ROOM_TABLE+"] ([room_id] VARCHAR(50),"
			+ "[room_name] VARCHAR(100))";
	
	public static final String CHAT_TABLE = "chat";      //聊天历史记录表
	public static final String C_ID = "id";                        	//消息id
	public static final String C_PACKET_ID = "packet_id";             //服务器返回的消息id
	public static final String C_CUR_ROSTER = "cur_roster";  		  //当前登录用户
	public static final String C_CHAT_NICKNAME = "chat_nickname";     //聊天对象昵称
	public static final String C_CHAT_ROSTER = "chat_roster";         //聊天对象
	public static final String C_MSG = "msg";                         //消息内容
	public static final String C_MSG_PATH = "msg_path";               //如果消息为语音需要保存存放路径
	public static final String C_MSG_DURA = "msg_dura";               //如果是语音消息记录时长
	public static final String C_MSG_TIME = "msg_time";               //消息发送或接收时间
	public static final String C_MSG_FROM = "msg_from";               //消息来自(0：对方，1己方)
	public static final int    C_MSG_FROM_OTHER = 0;
	public static final int    C_MSG_FROM_ME = 1;
	public static final String C_MSG_STATUS = "msg_status";           //消息是否已读 (0:未读 1:已读)
	public static final int    C_MSG_UNREAD = 0;                      //新消息未读
	public static final int    C_MSG_READED = 1;                      //消息已读
	public static final String C_MSG_TYPE = "msg_type";           //消息类型(0:普通消息 1：群消息)

	public static final String C_MSG_COUNT = "msg_count";
	public static final String CREATE_CHAT = "CREATE TABLE IF NOT exists ["+CHAT_TABLE+"] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "[packet_id] VARCHAR(20), [cur_roster] VARCHAR(50),[chat_nickname] VARCHAR(50), [chat_roster] VARCHAR(50), [msg] TEXT,"
			+ "[msg_path] TEXT, [msg_dura] varchar(25), [msg_time] varchar(25), [msg_from] INTEGER, [msg_type] VARCHAR(20), [msg_status] INTEGER)";
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_ROSTER);
		db.execSQL(CREATE_CHAT);
		db.execSQL(CREATE_ROOM);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists " + ROSTER_TABLE);
		db.execSQL("drop table if exists " + CHAT_TABLE);
		db.execSQL("drop table if exists " + ROOM_TABLE);
		onCreate(db);
	}
}
