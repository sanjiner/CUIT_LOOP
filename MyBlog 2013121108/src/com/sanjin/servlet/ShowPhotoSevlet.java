package com.sanjin.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanjin.dao.PhotoDAO;
import com.sanjin.form.PhotoForm;

/**
 * Servlet implementation class ShowPhotoSevlet
 */
@WebServlet("/ShowPhotoSevlet")
public class ShowPhotoSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowPhotoSevlet() {
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
		String id=request.getParameter("id");
		System.out.println("dfdfdfdfdfadfdf"+id);
		int PhotoType_id=Integer.parseInt(request.getParameter("id"));
		System.out.println(PhotoType_id);
		String PhotoType_name=request.getParameter("PhotoType_name");
		List<PhotoForm> list=new ArrayList<>();
		PhotoForm photoForm=new PhotoForm();
		PhotoDAO photoDAO=new PhotoDAO();
		list=photoDAO.ShowPhotoByType(PhotoType_id);
		request.setAttribute("list", list);
		request.setAttribute("idm", PhotoType_id);
		request.getRequestDispatcher("ShowPhoto.jsp").forward(request, response);
		
	}

}
