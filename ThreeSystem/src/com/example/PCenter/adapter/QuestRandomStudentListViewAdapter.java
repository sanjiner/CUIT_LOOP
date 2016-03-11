package com.example.PCenter.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.example.PCenter.Constant;
import com.example.PCenter.QuestionRandomStudentListActivity;
import com.exam.ThreeSystem.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class QuestRandomStudentListViewAdapter extends BaseAdapter {
	private List<Map<String, String>> data;
	private List<String> fenList;
	private QuestionRandomStudentListActivity context;
	private String className;
	private String TeachingClassID;
	private String AddedQuestionID;
	private String question_Score;//提问的分数
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;// 用于保存SharedPreferences的文件
	private String StuNum_and_questID = ";";  //将学生学号和对应的回答问题ID拼接起来
	
	public QuestRandomStudentListViewAdapter(Context context, List<Map<String, String>> data, 
			String TeachingClassID, String question_Score, List<String> fenList, String className,String StuNum_and_questID) {
		this.context = (QuestionRandomStudentListActivity) context;
		this.data = data;
		this.TeachingClassID = TeachingClassID;
		this.question_Score = question_Score;
		this.fenList = fenList;
		this.className = className;
		this.StuNum_and_questID = StuNum_and_questID;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void StuNumAndQuestID()
	{
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		String Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
		String TeacherName = sp.getString(Constant.SPFIELD.NAME, "");
		params.put("TeacherName", TeacherName);
		params.put("Term", Term);
		action = "GetAllStudentByTeacher";
		String url2 = URLTools
				.buildURL(Constant.INTERFACE_SITE + action, params);
		L.d("TTTTT", url2);
		StringRequest stringRequest2 = new StringRequest(url2, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (response.length() != 0) {
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if (success.equals("true")) {
							JSONArray jsonArray = jsonObj.getJSONArray("ClassList");
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject json = jsonArray.getJSONObject(i);
								if(json.getString("TeachingClassName").equals(className))
								{
									JSONArray jsonArray2 = json.getJSONArray("StudentList");									
									for (int j = 0; j < jsonArray2.length(); j++) {
										JSONObject json2 = jsonArray2.getJSONObject(j);
										if(json2.getString("isAddedQuestionToday").equals("YES")){	
											//将学生学号和对应的回答问题ID拼接起来
											String stuNum = json2.getString("StuNumber");
											String questID = json2.getString("AddedQuestionID");
											StuNum_and_questID = StuNum_and_questID + stuNum + "=" + questID + ";";
										}
									}														
								}
							}
						}
						else {
							L.d("TTTTT", "Success is false");
						}
					} catch (JSONException e) {
						L.e("TTTTT", e.toString());
					}
				}
			}
		},
			new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(
						context,
						VolleyErrorHelper
								.getErrorType(error),
						Toast.LENGTH_LONG).show();
			}
		});
		context.executeRequest(stringRequest2);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		sp = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		final int posit = position;
		
		if (convertView == null) {
			
			holder = new Holder();
			convertView = View.inflate(context, R.layout.more_queststudent_listview_item, null);
			holder.stuNumber = (TextView) convertView
					.findViewById(R.id.tv_more_question_stunumber_listview_item);
			holder.stuName = (TextView) convertView
					.findViewById(R.id.tv_more_question_studentname_listview_item);
			holder.score = (EditText) convertView
					.findViewById(R.id.tv_more_question_score);
			holder.stuBtn = (Button) convertView
					.findViewById(R.id.tv_more_question_button_setscore);
			holder.seekBar = (SeekBar) convertView
					.findViewById(R.id.tv_more_question_seekbar_score);
			if (data.get(position).get("isAddedQuestionToday").equals("YES")) {
				holder.stuBtn.setText("修改");				
			}
			convertView.setTag(holder);
			
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		final SeekBar seekBar1 = holder.seekBar;
		final Button stuBtn1 = holder.stuBtn;
		final EditText score1 = holder.score;
		final AlertDialog dialog = new AlertDialog.Builder(context).setTitle("正在打分，请等待。。。。").create();
		final AlertDialog dialog2 = new AlertDialog.Builder(context).setTitle("正在修改，请等待。。。。").create();
		holder.stuNumber.setText(data.get(position).get("stuNumber"));
		holder.stuName.setText(data.get(position).get("stuName"));
		holder.score.setText(fenList.get(position));
		
		//设置Button的监听器
		holder.stuBtn.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(final View v) {
				//打分时相应
				if (stuBtn1.getText().equals("打分")) {
					dialog.show();
					v.setEnabled(false);
					String stuNum = data.get(posit).get("stuNumber");
					String fen = score1.getText().toString();
					String url = Constant.INTERFACE_SITE+"AddQuestionScore?StuNumber="+stuNum
							+"&TeachingClassID="+TeachingClassID + "&Score="+fen+ "&QuestionState="+"ANDROID";
					L.d("TTTTT", url);
					StringRequest stringRequest = new StringRequest(url,
							new Response.Listener<String>() {
								@Override
								public void onResponse(String response) {
									try {
										JSONObject json = new JSONObject(response);
										String success = json.getString("Success");
										if (success.equals("true")) {
											Toast.makeText(context, "提问打分成功", 1000)
													.show();
											stuBtn1.setText("修改");	
											StuNumAndQuestID();
										}
										else{
											Toast.makeText(context, "提问打分失败", 1000)
											.show();
										}
										dialog.dismiss();
										v.setEnabled(true);
									}catch (JSONException e) {
										L.e("QuestStudentListViewAdapter",
												e.toString());
									}
								}
							}, new Response.ErrorListener() {
								@Override
								public void onErrorResponse(VolleyError error) {
									Toast.makeText(
											context,
											VolleyErrorHelper
													.getErrorType(error),
											Toast.LENGTH_LONG).show();
								}
							});
					context.executeRequest(stringRequest);
					
				}////修改分时响应
				else {	
					dialog2.show();
					v.setEnabled(false);
					//StuNumAndQuestID();
					
					System.out.println(StuNum_and_questID);
					String stuNum = data.get(posit).get("stuNumber");				
					String[] s = StuNum_and_questID.split(";");
					for (int i = 0; i < s.length; i++) {
						if (s[i].contains(stuNum)) {
							String QID = s[i].substring(s[i].lastIndexOf('=')+1);
							AddedQuestionID = QID;
							break;
						}
					}
								
					String fen = score1.getText().toString();
					String url = "http://222.18.158.198:8016/Authentication.svc/ModifyQuestionScore?QuestionID="+AddedQuestionID+ "&Score="+fen;
					StringRequest stringRequest = new StringRequest(url,
							new Response.Listener<String>() {
								@Override
								public void onResponse(String response) {
									try {
										JSONObject json = new JSONObject(
												response);
										String success = json
												.getString("Success");
										if (success.equals("true")) {
											Toast.makeText(context, "修改成功",
													1000).show();
										} else {
											Toast.makeText(context, "修改失败",
													1000).show();
										}
										v.setEnabled(true);
										dialog2.dismiss();
									} catch (JSONException e) {
										L.e("QuestStudentListViewAdapter",
												e.toString());
									}
								}
					
							}, new Response.ErrorListener() {
								@Override
								public void onErrorResponse(VolleyError error) {
									Toast.makeText(
											context,
											VolleyErrorHelper
													.getErrorType(error),
											Toast.LENGTH_LONG).show();
								}
							});
					context.executeRequest(stringRequest);										
				}
			}
		});
		
		//设置seekBar的监听器
		holder.seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				seekBar1.setMax(2*Integer.parseInt(question_Score));
				int s = seekBar1.getProgress()-Integer.parseInt(question_Score);				
				score1.setText(s + "");
				//Toast.makeText(context, "改变了", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				//Toast.makeText(context, "开始改变了", Toast.LENGTH_SHORT).show();
			}			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				//Toast.makeText(context, "停止改变了", Toast.LENGTH_SHORT).show();
			}			
		});
		
		
		return convertView;
	}
	
	private class Holder {
		TextView stuNumber;
		TextView stuName;
		EditText score;
		Button stuBtn;
		SeekBar seekBar;
	}	
}



//String fen = score1.getText().toString();
//String url = "http://222.18.158.198:8016/Authentication.svc/ModifyQuestionScore?QuestionID="+AddedQuestionID+ "&Score="+fen;
//StringRequest stringRequest = new StringRequest(url,
//		new Response.Listener<String>() {
//			@Override
//			public void onResponse(String response) {
//				try {
//					JSONObject json = new JSONObject(
//							response);
//					String success = json
//							.getString("Success");
//					if (success.equals("true")) {
//						Toast.makeText(context, "修改成功",
//								1000).show();
//					} else {
//						Toast.makeText(context, "修改失败",
//								1000).show();
//					}
//					v.setEnabled(true);
//					dialog2.dismiss();
//				} catch (JSONException e) {
//					L.e("QuestStudentListViewAdapter",
//							e.toString());
//				}
//			}
//
//		}, new Response.ErrorListener() {
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				Toast.makeText(
//						context,
//						VolleyErrorHelper
//								.getErrorType(error),
//						Toast.LENGTH_LONG).show();
//			}
//		});
//context.executeRequest(stringRequest);