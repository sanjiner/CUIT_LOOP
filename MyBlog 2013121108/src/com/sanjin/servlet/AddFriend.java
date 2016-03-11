package com.sanjin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.sanjin.dao.FriendDao;
import com.sanjin.form.MasterForm;

/**
 * Servlet implementation class AddFriend
 */
@WebServlet("/addfriend")
public class AddFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddFriend() {
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
		String master_loginName=request.getParameter("master_loginName");
		HttpSession session=request.getSession();
		MasterForm masterForm=(MasterForm) session.getAttribute("Master");
		String mylogName=masterForm.getMaster_loginname();
		FriendDao friendDao=new FriendDao();
		int addnumber;
		addnumber=friendDao.AddUser(master_loginName,mylogName);
		if(addnumber==0)
		{
			request.setAttribute("message","添加失败");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		else if(addnumber!=0)
		{
			request.setAttribute("message","添加成功");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		
	}

}
