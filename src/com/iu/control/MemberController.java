package com.iu.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iu.action.ActionFoward;
import com.iu.member.MemberDAO;
import com.iu.member.MemberService;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("/MemberController")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberController() {
        super();
        memberService = new MemberService() {
		};
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getPathInfo();
		
		ActionFoward actionFoward = null;
		
		if (command.equals("/memberCheck")) {
			
			actionFoward = new ActionFoward();
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/views/member/memberCheck.jsp");
			
		} else if (command.equals("/memberJoin")) {
			
			actionFoward = memberService.insert(request, response);
			
		}else if (command.equals("/memberLogin")) {
			
			actionFoward = new ActionFoward();
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/views/member/memberLogin.jsp");
			actionFoward = memberService.select(request, response);
			
		} else if (command.equals("/memberLogout")) {
			
			actionFoward = new ActionFoward();
			HttpSession session = request.getSession();
			session.invalidate();
			actionFoward.setCheck(false);
			actionFoward.setPath("../index.do");
			
			
		} else if (command.equals("/memberPage")) {
			
			actionFoward = new ActionFoward();
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/views/member/memberPage.jsp");
			//actionFoward = memberService.select(request, response);
			
		} else if (command.equals("/memberUpdate")) {
			
			actionFoward = memberService.update(request, response);
			
		} else if (command.equals("/memberDelete")) {
			
			actionFoward = memberService.delete(request, response);
			
		}
		
		if(actionFoward.isCheck()) {
			
			RequestDispatcher view = request.getRequestDispatcher(actionFoward.getPath());
			view.forward(request, response);
			
		} else {
			
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
