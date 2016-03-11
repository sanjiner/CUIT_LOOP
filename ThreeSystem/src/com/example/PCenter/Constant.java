package com.example.PCenter;

public class Constant {
	public static final int IO_BUFFER_SIZE = 2*1024;
	/* 教辅系统服务器根路径 */
//	public static String INTERFACE_SITE = "http://222.18.158.198:8016/Authentication.svc/";
	public static String INTERFACE_SITE = "http://222.18.158.220/Authentication.svc/";
//	public static String URL ="http://222.18.158.158:8016";
	public static String URL ="http://222.18.158.220";
	
	/* 毕业论文服务器根路径 */
	public final static String THESIS_BASEURL = "http://222.18.158.198:8901/Interface.svc/";

//	public final static String THESIS_BASEURL = "http://222.18.158.220:8905/Interface.svc/";
	public static final int REQUEST_TIMEOUT = 10 * 1000; // 请求超时5秒
	public static final int SO_TIMEOUT = 8 * 1000; // 连接超时5秒
	
	
	/* 过程化服务器根路径 */
//	public static String PROCESS_BASUURL = "http://222.18.158.198:8017/Authentication.svc/";
	public static String PROCESS_BASUURL = "http://222.18.158.220:8017/Authentication.svc/";
	
	
	/* sharedPreference file name */
	public static String USERINFO_SP = "userInfoSp";
	/* 用户身份 */
	public static String[] USERIDENTITY = { "学生", "老师" };
	/* 系统 */
	public static String[] SYSTEM = {"教学辅助", "过程化", "毕业论文"};//
	
	/*sharedPreference 中字段名称*/
	public static class SPFIELD{
		/* 判断是否为到更多页面*/
		public static String ISTOMORE = "istomore";
		/* 班级以及对应的分数 */
		public static String CLASS_AND_SCORE = "class_and_score";
		/* 学生ID和点到ID */
		public static String STUID_AND_ATTENID = "stuId_and_attenId";
		/* 学生ID和点到ID */
		public static String CLASS_AND_COUNT = "class_and_count";
		/*当前学期*/
		public static String CURRENTTERM = "currentTerm";
		/*今天是否登录*/
		public static String ISAddedAttendanceToday = "isAddedAttendanceToday";
		
		/*用户登录名*/		
		public static String NAME = "name";
		/*用户登录密码*/
		public static String PASSWORD = "password";
		/*isMemory变量用来判断SharedPreferences有没有数据，包括上面的YES和NO*/
		public static String ISMEMORY = "isMemory";
		/*用户登录类型*/
		public static String  USERIDENTITY= "useridentity";
		/*当前学期*/
		public static String System = "currentSystem";
		/*用户真实姓名*/
		public static String  REALNAME= "realname";
		/*用户性别*/
		public static String  SEX= "sex";
		/*用户班级*/
		public static String  CLASSNAME= "classname";
		/*用户专业*/
		public static String  MAJORNAME= "majorname";
		/*用户学院*/
		public static String  DEPARTMENTNAME= "departmentname";
		/*教师id*/
		public final static String TEACHERID= "TeacherID";
		/*加载教辅系统图片*/
		public final static String UPloadURL="http://222.18.158.220/PhoneAccess/TeacherUploadPictures/List.aspx?";
	}
	
	
}
