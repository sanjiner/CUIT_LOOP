package cuit.emergency.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import android.util.Log;

public class RosterOffline {
	
	private Context mContext;
	private DBHelper databaseHelper;
	private SQLiteDatabase sqLiteDatabase;
	private String filePath;

	public RosterOffline(Context context){
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
	public void insert(List<Map<String, String>> rosters) {
		// TODO Auto-generated method stub
		open();
		for (Map<String, String> roster : rosters) {
			Cursor cursor = sqLiteDatabase.rawQuery("select * from "+DBHelper.ROSTER_TABLE +" where "
					+DBHelper.R_CUR_ROSTER+"=? and "+DBHelper.R_ROSTER+"=?", 
				new String[]{SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""), roster.get(DBHelper.R_ROSTER)});
			if (!cursor.moveToNext()) {
				ContentValues values = new ContentValues();
				values.put(DBHelper.R_CUR_ROSTER, SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""));
				values.put(DBHelper.R_ROSTER, roster.get(DBHelper.R_ROSTER));
				values.put(DBHelper.R_GROUP_NAME, roster.get(DBHelper.R_GROUP_NAME));
				values.put(DBHelper.R_GROUP_POS, roster.get(DBHelper.R_GROUP_POS));
				values.put(DBHelper.R_NICKNAME, roster.get(DBHelper.R_NICKNAME));
				values.put(DBHelper.R_MODE, roster.get(DBHelper.R_MODE));
				sqLiteDatabase.insertOrThrow(DBHelper.ROSTER_TABLE, null, values);
			} else {
				ContentValues values = new ContentValues();
				values.put(DBHelper.R_NICKNAME, roster.get(DBHelper.R_NICKNAME));
				values.put(DBHelper.R_GROUP_NAME, roster.get(DBHelper.R_GROUP_NAME));
				values.put(DBHelper.R_GROUP_POS, roster.get(DBHelper.R_GROUP_POS));
				values.put(DBHelper.R_MODE, roster.get(DBHelper.R_MODE));
				sqLiteDatabase.update(DBHelper.ROSTER_TABLE, values, DBHelper.R_CUR_ROSTER+"=? and "+DBHelper.R_ROSTER+"=? ", 
						new String[]{SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""), roster.get(DBHelper.R_ROSTER)});
			}
			cursor.close();	
		}
		close();
	}
	
	public void deleteData(final String currentRoster, final String rosterJID) {
		// TODO Auto-generated method stub
		open();
		sqLiteDatabase.delete(DBHelper.ROSTER_TABLE, DBHelper.R_CUR_ROSTER+"=? and "
				+DBHelper.R_ROSTER+"=?", new String[]{currentRoster, rosterJID});
		close();
	}
	
	/* 删除所有数据 */
	public void deleteAll(){
		open();
		sqLiteDatabase.delete(DBHelper.ROSTER_TABLE, null, null);
		close();
	}
	
	/** 查询好友信息 */
	public Map<String, List<Map<String, String>>> selectAll() {
		open();
		LinkedHashMap<String,List<Map<String,String>>> group = new LinkedHashMap<String, List<Map<String,String>>>();
		Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DBHelper.ROSTER_TABLE
				+ " where "+DBHelper.R_CUR_ROSTER+"=? order by grouppos", new String[]{SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, "")});
		while (cursor.moveToNext()) {
			List<Map<String, String>> children = group.get(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.R_GROUP_NAME)));
			if (children == null) {
				children = new ArrayList<Map<String,String>>();
				group.put(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.R_GROUP_NAME)), children);
				Log.e(">>>>>>>>>>>>>>",cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.R_GROUP_NAME)));
			}
			Map<String, String> child = new HashMap<String, String>();
			children.add(child);
			child.put(DBHelper.R_CUR_ROSTER, SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""));
			child.put(DBHelper.R_GROUP_NAME, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.R_GROUP_NAME)));
			child.put(DBHelper.R_MODE, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.R_MODE)));
			child.put(DBHelper.R_NICKNAME, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.R_NICKNAME)));
			child.put(DBHelper.R_ROSTER, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.R_ROSTER)));
		}
		return group;
	}
	
	/* 更新好友上线下状态 */
	public void updateMode(final String rosterUser, final String mode) {
		open();
		ContentValues values = new ContentValues();
		values.put(DBHelper.R_MODE, mode);
		sqLiteDatabase.update(DBHelper.ROSTER_TABLE, values, DBHelper.R_CUR_ROSTER+"=? and "
				+DBHelper.R_ROSTER+"=?", new String[]{SharedPreferenceUtil.getPrefString(mContext,
				Constants.UNAME, ""), rosterUser});
		close();
	}
	
	/* 根据好友jid查询昵称 */
	public String selectNickName(final String rosterJid) {
		String nickName = "";
		open();
		Cursor cursor = sqLiteDatabase.rawQuery("select "+DBHelper.R_NICKNAME+" from " + DBHelper.ROSTER_TABLE
				+ " where "+DBHelper.R_CUR_ROSTER+"=? and "+DBHelper.R_ROSTER+"=?", new String[]{SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""), rosterJid});
		while (cursor.moveToNext()) {
			nickName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.R_NICKNAME));
		}
		cursor.close();
		close();
		return nickName;
	}
	
	//查询当前用户的所有好友jid
	public List<String> selectJID() {
		open();
		List<String> mtempList = new ArrayList<String>();
		Cursor cursor = sqLiteDatabase.rawQuery("select * from "+DBHelper.ROSTER_TABLE
				+" where "+DBHelper.R_CUR_ROSTER+"=?", new String[]{SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, "")});
		while (cursor.moveToNext()) {
			String jid = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.R_ROSTER));
			if (!mtempList.contains(jid)) {
				mtempList.add(jid);
			}
		}
		cursor.close();
		close();
		mtempList.add(0, SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, "")+"@"+Constants.DEFAULT_SERVERNAME);
		return mtempList;
	}
	
	/* 根据房间成员jid查询昵称 */
	public String selectMemberNickName(final String rosterJid) {
		String nickName = "";
		open();
		Cursor cursor = sqLiteDatabase.rawQuery("select "+DBHelper.R_NICKNAME+" from " + DBHelper.ROSTER_TABLE
				+ " where "+DBHelper.R_CUR_ROSTER+"=? and "+DBHelper.R_ROSTER+"=?", new String[]{SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""), rosterJid});
		while (cursor.moveToNext()) {
			nickName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.R_NICKNAME));
		}
		cursor.close();
		close();
		return nickName;
	}
	
	//修改所有联系人在线状态,unavaiable
	public void updateAllStatus() {
		open();
		ContentValues values = new ContentValues();
		values.put(DBHelper.R_MODE, Constants.UNAVAILABLE);
		sqLiteDatabase.update(DBHelper.ROSTER_TABLE, values, null, null);
		close();
	}
	
}
