package com.iu.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.ActionFoward;
import com.iu.notice.NoticeDAO;
import com.iu.notice.NoticeService;

/**
 * Servlet implementation class NoticeController
 */
@WebServlet("/NoticeController")
public class NoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeService noticeService;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeController() {
        super();
        noticeService = new NoticeService() {
        	
		};
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String command = request.getPathInfo();
		ActionFoward actionFoward = null;
		
		if(command.equals("/noticeList")) {
			
			actionFoward = noticeService.list(request, response);
			
		} else if(command.equals("/noticeSelect")) {
			
			actionFoward = noticeService.select(request, response);
			
		} else if(command.equals("/noticeUpdate")) {
			
			actionFoward = noticeService.select(request, response);
			actionFoward = noticeService.update(request, response);
			
		} else if(command.equals("/noticeWrite")) {
			
			actionFoward = noticeService.insert(request, response);
			
		} else if(command.equals("/noticeDelete")) {
			
			actionFoward = noticeService.delete(request, response);
			
		} else { //이상한 주소가 들어오면 다른 곳으로 보내버릴때
			
			actionFoward = new ActionFoward();
			
			
		}
		
		
		
		
		if(actionFoward.isCheck()) {
			
			//포워드
			RequestDispatcher view = request.getRequestDispatcher(actionFoward.getPath());
			view.forward(request, response);
			
		} else {
			
			//리다이렉트
			response.sendRedirect(actionFoward.getPath());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
