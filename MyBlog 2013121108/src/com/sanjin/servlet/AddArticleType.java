package com.sanjin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanjin.dao.ArticleDao;
import com.sanjin.dao.ArticleTypeDao;
import com.sanjin.form.ArticleTypeForm;

/**
 * Servlet implementation class AddArticleType
 */
@WebServlet("/AddArticleType")
public class AddArticleType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddArticleType() {
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
		request.setCharacterEncoding("UTF-8");
		int number=0;
		ArticleTypeDao articleTypeDao=new ArticleTypeDao();
		ArticleTypeForm articleTypeForm=new ArticleTypeForm();
		articleTypeForm.setArticleType_name(request.getParameter("articleType_name").toString());
		articleTypeForm.setArticleType_info(request.getParameter("articleType_info").toString());
		number=articleTypeDao.AddArticleType(articleTypeForm);
		if(number==0)
		{
			request.setAttribute("check", "添加失败!");
			request.getRequestDispatcher("articleType.jsp").forward(request, response);
		}
		else if(number==1)
		{
			request.setAttribute("check","添加成功!");
			request.getRequestDispatcher("articleType.jsp").forward(request, response);
		}
	}

}
