package cuit.emergency.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cc.util.android.core.SharedPreferenceUtil;
import cuit.emergency.util.Constants;
import cuit.emergency.util.ToolUtil;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ChatOffline {
	public final static int DEFAULT_ITEMS = 15;
	
	private Context mContext;
	private DBHelper databaseHelper;
	private SQLiteDatabase sqLiteDatabase;
	private String filePath;

	public ChatOffline(Context context){
		mContext = context;
		filePath = ToolUtil.getDBCachePath(context);
	}
	
	/* 打开数据库 */
	public void open() throws SQLException {
		databaseHelper = new DBHelper(mContext, filePath
				+ DBHelper.DB_NAME, null, DBHelper.DB_VERSION);
		sqLiteDatabase = databaseHelper.getWritableDatabase();
	}

	/* 关闭数据库 */
	public void close() {
		databaseHelper.close();
	}
	
	/* 将指定好友的消息修改为已读 */
	public void updateToReaded(String chatRoster) {
		open();
		ContentValues values = new ContentValues();
		values.put(DBHelper.C_MSG_STATUS, DBHelper.C_MSG_READED);
		sqLiteDatabase.update(DBHelper.CHAT_TABLE, values, 
				DBHelper.C_CHAT_ROSTER+"=? and "+DBHelper.C_CUR_ROSTER+"=?",
				new String[]{chatRoster, SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, "")});
		close();
	}
	
	/* 插入数据 */
	public void insert(Map<String, String> dataMap) {
		// TODO Auto-generated method stub
		open();
		ContentValues values = new ContentValues();
		values.put(DBHelper.C_PACKET_ID, dataMap.get(DBHelper.C_PACKET_ID));
		values.put(DBHelper.C_CUR_ROSTER, SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""));
		values.put(DBHelper.C_CHAT_ROSTER, dataMap.get(DBHelper.C_CHAT_ROSTER));
		values.put(DBHelper.C_CHAT_NICKNAME, dataMap.get(DBHelper.C_CHAT_NICKNAME));
		values.put(DBHelper.C_MSG, dataMap.get(DBHelper.C_MSG));
		values.put(DBHelper.C_MSG_PATH, dataMap.get(DBHelper.C_MSG_PATH));
		values.put(DBHelper.C_MSG_DURA, dataMap.get(DBHelper.C_MSG_DURA));
		values.put(DBHelper.C_MSG_TIME, dataMap.get(DBHelper.C_MSG_TIME));
		values.put(DBHelper.C_MSG_FROM, dataMap.get(DBHelper.C_MSG_FROM));
		values.put(DBHelper.C_MSG_STATUS, dataMap.get(DBHelper.C_MSG_STATUS));
		values.put(DBHelper.C_MSG_TYPE, dataMap.get(DBHelper.C_MSG_TYPE));
		sqLiteDatabase.insertOrThrow(DBHelper.CHAT_TABLE, null, values);
		close();
	}
	
	//查询未读消息
	public List<Map<String, String>> selectUnReadedMsg() {
		open();
		List<Map<String, String>> msgList = new ArrayList<Map<String,String>>();
		//首先查询有未读消息的联系人
		Cursor cursor = sqLiteDatabase.rawQuery("select distinct "+DBHelper.C_CHAT_ROSTER+" from " + DBHelper.CHAT_TABLE+ " where "+DBHelper.C_CUR_ROSTER+"=? "
				+ "and "+DBHelper.C_MSG_STATUS+"=?",
				new String[]{SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""), DBHelper.C_MSG_UNREAD+""});
		List<String> rosterJIDs = new ArrayList<String>();
		while (cursor.moveToNext()) {
			rosterJIDs.add(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_CHAT_ROSTER)));
		}
		cursor.close();

		//消息界面记录的联系人
		String message_roster = SharedPreferenceUtil.getPrefString(mContext, Constants.MESSAGE_ROSTER, "");
		if (!message_roster.equals("")) {
			String[] rosters = message_roster.split(",");
			for (String roster : rosters) {
				if (!rosterJIDs.contains(roster)) rosterJIDs.add(roster);
			}
		}
		message_roster = "";
		for (int i = 0; i < rosterJIDs.size(); i++) {
			message_roster += i > 0 ? ","+rosterJIDs.get(i) : rosterJIDs.get(i);
		}
		SharedPreferenceUtil.setPrefString(mContext, Constants.MESSAGE_ROSTER, message_roster);

		//查询有未读消息联系人的数据条数
		Map<String, Long> roster_count = new HashMap<String, Long>();
		for (String jid : rosterJIDs) {
			cursor = sqLiteDatabase.rawQuery("select count("+DBHelper.C_MSG_UNREAD+") from "+ DBHelper.CHAT_TABLE+ " where "+DBHelper.C_CUR_ROSTER+"=? "
					+ "and "+DBHelper.C_MSG_STATUS+"=? and "+DBHelper.C_CHAT_ROSTER+"=?", 
					new String[]{SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""), DBHelper.C_MSG_UNREAD+"", jid});
			cursor.moveToFirst();
			roster_count.put(jid, cursor.getLong(0));
			cursor.close();
		}

		//最后查询消息最后一条
		for (String jid : rosterJIDs) {
			cursor = sqLiteDatabase.rawQuery("select * from "+ DBHelper.CHAT_TABLE+ " where "+DBHelper.C_CUR_ROSTER+"=? "
					+ " and "+DBHelper.C_CHAT_ROSTER+"=? order by id desc limit 1 offset 0", 
					new String[]{SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""), jid});
			while (cursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put(DBHelper.C_ID, cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.C_ID))+"");
				map.put(DBHelper.C_CUR_ROSTER, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_CUR_ROSTER)));
				map.put(DBHelper.C_CHAT_ROSTER, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_CHAT_ROSTER)));
				map.put(DBHelper.C_CHAT_NICKNAME, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_CHAT_NICKNAME)));
				map.put(DBHelper.C_MSG, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_MSG)));
				map.put(DBHelper.C_MSG_PATH, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_MSG_PATH)));
				map.put(DBHelper.C_MSG_STATUS, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_MSG_STATUS)));
				map.put(DBHelper.C_MSG_DURA, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_MSG_DURA)));
				map.put(DBHelper.C_MSG_TIME, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_MSG_TIME)));
				map.put(DBHelper.C_MSG_FROM, cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.C_MSG_FROM))+"");
				map.put(DBHelper.C_MSG_TYPE, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_MSG_TYPE)));
				map.put(DBHelper.C_MSG_COUNT, roster_count.get(jid).toString());
				msgList.add(map);
			}
			cursor.close();
		}
		close();
		return msgList;
	}
	
	/* 查询聊天消息 */
	public List<Map<String, String>> selectChatMsg(final String chatRoster, final int offset) {
		// TODO Auto-generated method stub
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		open();
		Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DBHelper.CHAT_TABLE+ " where "+DBHelper.C_CUR_ROSTER+"=? "
				+ "and "+DBHelper.C_CHAT_ROSTER+"=? order by id desc limit "+DEFAULT_ITEMS+" offset "+offset,
				new String[]{SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""), chatRoster});
		while (cursor.moveToNext()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(DBHelper.C_ID, cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.C_ID))+"");
			map.put(DBHelper.C_CUR_ROSTER, SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""));
			map.put(DBHelper.C_CHAT_ROSTER, chatRoster);
			map.put(DBHelper.C_CHAT_NICKNAME, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_CHAT_NICKNAME)));
			map.put(DBHelper.C_MSG, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_MSG)));
			map.put(DBHelper.C_MSG_PATH, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_MSG_PATH)));
			map.put(DBHelper.C_MSG_STATUS, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_MSG_STATUS)));
			map.put(DBHelper.C_MSG_DURA, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_MSG_DURA)));
			map.put(DBHelper.C_MSG_TIME, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_MSG_TIME)));
			map.put(DBHelper.C_MSG_FROM, cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.C_MSG_FROM))+"");
			map.put(DBHelper.C_MSG_TYPE, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.C_MSG_TYPE)));
			resultList.add(map);
		}
		cursor.close();
		close();
		return resultList;
	}
	
}
