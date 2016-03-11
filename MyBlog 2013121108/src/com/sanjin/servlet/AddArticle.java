package com.sanjin.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.sanjin.dao.ArticleDao;
import com.sanjin.form.ArticleForm;
import com.sanjin.form.MasterForm;

/**
 * Servlet implementation class AddArticle
 */
@WebServlet("/AddArticle")
public class AddArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddArticle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int number=0;
		request.setCharacterEncoding("UTF-8");
		HttpSession session=request.getSession();
		ArticleForm articleForm=new ArticleForm();
		ArticleDao articleDao=new ArticleDao();
		MasterForm masterForm=new MasterForm();
		masterForm=(MasterForm) session.getAttribute("Master");
		System.out.println("masterForm..."+masterForm);
		Date date=new Date();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time=simpleDateFormat.format(date);
		articleForm.setMaster_loginName(masterForm.getMaster_loginname());
		articleForm.setArticle_title(request.getParameter("article_title"));
		articleForm.setArticle_info(request.getParameter("article_info"));
		articleForm.setArticle_content(request.getParameter("article_content"));
		articleForm.setArticle_sdTime(time);
		articleForm.setArticleType_id(Integer.parseInt(request.getParameter("articleType_id")));
		System.out.println("选择框"+Integer.parseInt(request.getParameter("articleType_id")));
		articleForm.setArticle_create(masterForm.getMaster_realname());
		number=articleDao.addArticle(articleForm);
		if(number!=0)
		{
			request.setAttribute("check", "添加文章成功！");
			request.getRequestDispatcher("MyblogDetail.jsp").forward(request, response);
		}
		else if(number==0)
		{
			request.setAttribute("check","添加文章失败！");
			request.getRequestDispatcher("MyblogDetail.jsp").forward(request, response);
		}
	}

}
