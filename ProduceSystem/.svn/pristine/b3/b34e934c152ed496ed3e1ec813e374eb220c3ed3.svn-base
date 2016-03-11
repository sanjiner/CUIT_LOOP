package edu.cuit.module.label.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ProgressShow extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/"; 
		response.setContentType("text/html");  
        PrintWriter out = response.getWriter(); 
        out.println("<html>");  
        out.println("<head>");  
        out.println("<title></title>");
        out.println("<script type=\"text/javascript\" language=\"javascript\">");
        out.println("function back(pid) {"); 
        out.println("setTimeout(function(){goback(pid);},1000);");
        out.println("}");
        out.println("function goback(pid) {"); 
        out.println("window.location.href=\""+basePath+"label/manage/application/list?key=\"+pid;");
        out.println("}");
        out.println("function BeginTrans(msg) {");
        out.println("WriteText(\"Msg1\", msg);");
        out.println("}");
        out.println("function SetPorgressBar(msg, pos) {");
        out.println("ProgressBar.style.width = pos + \"%\";");
        out.println("WriteText(\"Msg1\", msg + \"已完成\" + pos + \"%\");");
        out.println("}");
        out.println("function EndTrans(msg) {");
        out.println("ProgressBar.style.width = \"100%\";");
        out.println("if (msg == \"\")");
        out.println("WriteText(\"Msg1\", \"完成。\");");
        out.println("else");
        out.println("WriteText(\"Msg1\", msg);");
        out.println("}");
        out.println("function SetTimeInfo(msg) {");
        out.println("WriteText(\"Msg2\", msg);");
        out.println("}");
        out.println("function WriteText(id, str) {");
        out.println("document.getElementById(id).innerHTML = str");
        out.println("}"); 
        out.println("</script>");
        out.println("</head>");  
        out.println("<body style=\"height: 191px; margin-top: 89px; margin-bottom: 15px;\">");
        out.println("<table align=\"center\" style=\"height:98%\">");
        out.println("<tr style=\"height:45%\"><td></td></tr>"); 
        out.println("<tr>");
        out.println("<td>");
        out.println("<div style=\"height:16px;\"><font face=\"Verdana, Arial, Helvetica\" size=\"2\" color=\"#ea9b02\"><b id=\"Msg1\">正在加载...</b></font></div>");
        out.println("<div id=\"ProgressBarSide\" style=\"width:300px; color:Silver;border-width:1px; border-style:Solid;\">");
        out.println("<div id=\"ProgressBar\" align=\"center\" style=\"height:20px; width:0%; background-color:#316AC5;\"></div>");
        out.println("</div>");
        out.println("<div style=\"height:16px;\"><font face=\"Verdana, Arial, Helvetica\" size=\"2\" color=\"#ea9b02\"><b id=\"Msg2\"></b></font></div>");
        out.println("</td>");
        out.println("</tr>");
        out.println("<tr style=\"height:50%\"><td></td></tr>");
        out.println("</table>");  
        out.println("</body>");  
        out.println("</html>");
        out.flush();
    }
	
	public void BeginTrans(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {
		response.setContentType("text/html");  
        PrintWriter out = response.getWriter(); 
        out.write("<script>BeginTrans('开始处理...');</script>"); 
        out.flush();
 	}
	
	public void SetPorgressBar(HttpServletRequest request, HttpServletResponse response, String s)  
            throws ServletException, IOException {
		response.setContentType("text/html");  
        PrintWriter out = response.getWriter(); 
        out.write("<script>SetPorgressBar('" + "标签" + "','" + s + "');</script>"); 
        out.flush();
 	}
	
	public void EndTrans(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {
		response.setContentType("text/html");  
        PrintWriter out = response.getWriter(); 
        out.write("<script>EndTrans('处理完成');</script>"); 
        out.flush();
 	}
	
	public void SetTimeInfo(HttpServletRequest request, HttpServletResponse response, String s)  
            throws ServletException, IOException {
		response.setContentType("text/html");  
        PrintWriter out = response.getWriter(); 
        out.write("<script>SetTimeInfo('用时" + s + "');</script>"); 
        out.flush();
 	}
	
	public void back(HttpServletRequest request, HttpServletResponse response, String pid)  
            throws ServletException, IOException {
		response.setContentType("text/html");  
        PrintWriter out = response.getWriter(); 
        out.write("<script>back('" + pid + "');</script>"); 
        out.close();
 	}
	
}
