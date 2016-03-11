package com.sanjin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sanjin.dao.ReviewArticleDao;
import com.sanjin.form.ArticleForm;
import com.sanjin.form.MasterForm;
import com.sanjin.form.ReviewFrom;

/**
 * Servlet implementation class AddArticleReview
 */
@WebServlet("/AddArticleReview")
public class AddArticleReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddArticleReview() {
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
		String id="";
		id=request.getParameter("id");
		request.setCharacterEncoding("UTF-8");
		System.out.println("id是"+id);
		HttpSession session=request.getSession();
		MasterForm masterForm=new MasterForm();
		masterForm=(MasterForm) session.getAttribute("Master");
		System.out.println("masterForm是"+masterForm);
		
		ReviewFrom reviewFrom=new ReviewFrom();
		reviewFrom.setArticle_typeID(id);
		reviewFrom.setReview_author(masterForm.getMaster_loginname());
		reviewFrom.setReview_content(request.getParameter("review_content"));
		ReviewArticleDao reviewArticleDao =new ReviewArticleDao();
		number=reviewArticleDao.AddArticleReview(reviewFrom);
		if(number!=0)
		{
			request.setAttribute("back", "评论添加成功！");
			request.getRequestDispatcher("detail.jsp").forward(request, response);
		}
		else if(number==0)
		{
			request.setAttribute("back", "评论添加失败！");
			request.getRequestDispatcher("detail.jsp").forward(request, response);
		}
	}

}
