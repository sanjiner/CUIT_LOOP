package com.sanjin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanjin.dao.ReplyDao;
import com.sanjin.form.ReplyForm;

/**
 * Servlet implementation class AddNewReply
 */
@WebServlet("/AddNewReply")
public class AddNewReply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewReply() {
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
		int reviewID= Integer.parseInt(request.getParameter("id"));
		ReplyForm replyForm=new ReplyForm();
		replyForm.setReviewID(reviewID);
		replyForm.setReply_Content(request.getParameter("Reply_Content"));
		ReplyDao replyDao=new ReplyDao();
		number=replyDao.AddReply(replyForm);
		if(number==1)
		{
			request.setAttribute("back","回复添加成功");
			request.getRequestDispatcher("detail.jsp").forward(request, response);
		}
		
		else if(number==0)
		{
			request.setAttribute("back","回复添加失败!");
			request.getRequestDispatcher("detail.jsp").forward(request, response);
		}
	}

}
