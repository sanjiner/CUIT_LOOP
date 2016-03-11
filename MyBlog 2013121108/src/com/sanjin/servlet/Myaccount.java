package com.sanjin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sanjin.dao.ConsumerDao;
import com.sanjin.form.MasterForm;

/**
 * Servlet implementation class Myaccount
 */
@WebServlet("/Myaccount")
public class Myaccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Myaccount() {
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
		HttpSession session=request.getSession();
		MasterForm masterFormd=new MasterForm();
		masterFormd=(MasterForm) session.getAttribute("Master");
		String master_loginName=masterFormd.getMaster_loginname();
		System.out.println("master_loginName="+master_loginName);
		String master_password=request.getParameter("master_password");
		String master_name=request.getParameter("master_name");
		String master_realname=request.getParameter("master_realname");
		String master_sex=request.getParameter("master_sex");
		String master_oicq=request.getParameter("master_oicq");
		int number=0;
		MasterForm masterForm=new MasterForm();
		ConsumerDao consumerDao=new ConsumerDao();
		if(master_loginName.equals(""))
		{
			masterForm.setMaster_loginname(null);
		}
		else {
			masterForm.setMaster_loginname(master_loginName);
		}
		masterForm.setMaster_password(master_password);
		masterForm.setMaster_name(master_name);
		masterForm.setMaster_realname(master_realname);
		masterForm.setMaster_sex(master_sex);
		masterForm.setMaster_oicq(master_oicq);
		number=consumerDao.changeuser(masterForm);
		
			if(number==0)
			{
				request.setAttribute("judge", "no");
				request.getRequestDispatcher("Myaccount.jsp").forward(request, response);
			}
			else {
				request.setAttribute("judge","yes");
				request.getRequestDispatcher("Myaccount.jsp").forward(request, response);
			}
		
		
		
	}


}
