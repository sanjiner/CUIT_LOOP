package cuit.emergency.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cuit.emergency.util.ToolUtil;

public class RoomOffline {

	private Context mContext;
	private DBHelper databaseHelper;
	private SQLiteDatabase sqLiteDatabase;
	private String filePath;

	public RoomOffline(Context context){
		mContext = context;
		filePath = ToolUtil.getDBCachePath(context);
	}
	
	/* 打开数据库 */
	public void open() throws SQLException {
		if (databaseHelper == null) {
			databaseHelper = new DBHelper(mContext, filePath
				+ DBHelper.DB_NAME, null, DBHelper.DB_VERSION);
		}
		sqLiteDatabase = databaseHelper.getWritableDatabase();
	}

	/* 关闭数据库 */
	public void close() {
		databaseHelper.close();
	}
	
	/* 插入数据（如果存在数据则修改即可） */
	public void insert(List<Map<String, String>> roomItems) {
		// TODO Auto-generated method stub
		open();
		sqLiteDatabase.delete(DBHelper.ROOM_TABLE, null, null);
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for (Map<String, String> map : roomItems) {
			if (i > 0) {
				builder.append(",'"+map.get(DBHelper.ROOM_ID)+"'");
			} else {
				builder.append("'"+map.get(DBHelper.ROOM_ID)+"'");
			}
			i++;
			ContentValues values = new ContentValues();
			values.put(DBHelper.ROOM_ID, map.get(DBHelper.ROOM_ID));
			values.put(DBHelper.ROOM_NAME, map.get(DBHelper.ROOM_NAME));
			sqLiteDatabase.insertOrThrow(DBHelper.ROOM_TABLE, null, values);
		}
		sqLiteDatabase.execSQL("delete from "+DBHelper.ROOM_TABLE+" where "+DBHelper.ROOM_ID+" not in(" + builder.toString()  
                            + ")");
		close();
	}
	
	/* 删除所有数据 */
	public void deleteAll(){
		open();
		sqLiteDatabase.delete(DBHelper.ROOM_TABLE, null, null);
		close();
	}
	
	/** 查询群组信息 */
	public List<Map<String, String>> selectAll() {
		open();
		List<Map<String, String>> tmpList = new ArrayList<Map<String,String>>();
		Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DBHelper.ROOM_TABLE , new String[]{});
		while (cursor.moveToNext()) {
			Map<String, String> child = new HashMap<String, String>();
			child.put(DBHelper.ROOM_ID, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ROOM_ID)));
			child.put(DBHelper.ROOM_NAME, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ROOM_NAME)));
			tmpList.add(child);
		}
		return tmpList;
	}
	
	/* 根据好友jid查询昵称 */
	public String selectNickName(final String roomJid) {
		String nickName = "";
		open();
		Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DBHelper.ROOM_TABLE 
				+ " where " + DBHelper.ROOM_ID +"=?", new String[]{roomJid});
		while (cursor.moveToNext()) {
			nickName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ROOM_NAME));
		}
		cursor.close();
		close();
		return nickName;
	}
}
