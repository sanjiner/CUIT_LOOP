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
import com.example.PCenter.Home.HomeQuestionStudentListActivity;

import android.R.integer;
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

public class QuestStudentListViewAdapter extends BaseAdapter {
	private List<Map<String, String>> data;
	private HomeQuestionStudentListActivity context;
	private String className;
	private String TeachingClassID;
	private String AddedQuestionID;
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;// 用于保存SharedPreferences的文件
	
	public QuestStudentListViewAdapter(Context context, List<Map<String, String>> data, 
			String TeachingClassID, String className) {
		this.context = (HomeQuestionStudentListActivity) context;
		this.data = data;
		this.TeachingClassID = TeachingClassID;
		this.className = className;
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
			holder.question_count = (TextView) convertView
					.findViewById(R.id.tv_more_question_student_quest_count_item);
			holder.score = (EditText) convertView
					.findViewById(R.id.tv_more_question_score);
			holder.stuBtn = (Button) convertView
					.findViewById(R.id.tv_more_question_button_setscore);
			holder.seekBar = (SeekBar) convertView
					.findViewById(R.id.tv_more_question_seekbar_score);
			if(data.get(position).get("AddedScoreGrade").equals("A")){
				holder.seekBar.setProgress(0);
			}else if(data.get(position).get("AddedScoreGrade").equals("B")){
				holder.seekBar.setProgress(1);
			}else if(data.get(position).get("AddedScoreGrade").equals("C")){
				holder.seekBar.setProgress(2);
			}else if(data.get(position).get("AddedScoreGrade").equals("D")){
				holder.seekBar.setProgress(3);
			}else{
				holder.seekBar.setProgress(4);
			}
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
		final TextView count = holder.question_count;
		final AlertDialog dialog = new AlertDialog.Builder(context).setTitle("正在打分，请等待。。。。").create();
		final AlertDialog dialog2 = new AlertDialog.Builder(context).setTitle("正在修改，请等待。。。。").create();
		holder.stuNumber.setText(data.get(position).get("stuNumber"));
		holder.stuName.setText(data.get(position).get("stuName"));
		holder.question_count.setText(data.get(position).get("AlreadyQuestionNum"));
		holder.score.setText(data.get(position).get("AddedScoreGrade"));
		
		//设置Button的监听器
		holder.stuBtn.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(final View v) {
				//打分时响应
				if (stuBtn1.getText().equals("打分")){
					dialog.show();
					v.setEnabled(false);
					String stuNum = data.get(posit).get("stuNumber");
					String fen = score1.getText().toString();
					String url = Constant.INTERFACE_SITE+"New_AddQuestionScore?StuNumber="+stuNum
							+"&TeachingClassID="+TeachingClassID + "&ScoreGrade="+fen+ "&QuestionState="+"ANDROID";
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
											int a = Integer.parseInt(data.get(posit).get("AlreadyQuestionNum")) + 1;
											count.setText(a + "");
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
					
				}////修改分时相应
				else {		
					dialog2.show();
					v.setEnabled(false);
					Map<String, String> params = new HashMap<String, String>();
					String action = "";
					String Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
					String TeacherName = sp.getString(Constant.SPFIELD.NAME, "");
					params.put("TeacherName", TeacherName);
					params.put("Term", Term);
					action = "New_GetAllStudentByTeacher";
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
												JSONObject json2 = jsonArray2.getJSONObject(posit);
												if(json2.getString("isAddedQuestionToday").equals("YES"))
												{
													AddedQuestionID = json2.getString("AddedQuestionID");
													
													String fen = score1.getText().toString();
													String url = Constant.INTERFACE_SITE + "New_ModifyQuestionScore?QuestionID="+AddedQuestionID+ "&ScoreGrade="+fen;
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
			}
		});
		
		
		
		
		//设置seekBar的监听器
		holder.seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				int s = seekBar1.getProgress();
				if(s==0){
					score1.setText("A");
				}if(s==1){
					score1.setText("B");
				}if(s==2){
					score1.setText("C");
				}if(s==3){
					score1.setText("D");
				}if(s==4){
					score1.setText("E");
				}
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
		TextView question_count;
		EditText score;
		Button stuBtn;
		SeekBar seekBar;
	}	
}
