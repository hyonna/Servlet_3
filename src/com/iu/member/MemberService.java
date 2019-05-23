package com.iu.member;

import java.sql.Connection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

import com.iu.action.Action;
import com.iu.action.ActionFoward;
import com.iu.profile.ProfileDAO;
import com.iu.profile.ProfileDTO;
import com.iu.upload.UploadDTO;
import com.iu.util.DBConnector;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public abstract class MemberService implements Action{
	
	private MemberDAO memberDAO;
	private ProfileDAO profileDAO;
	
	public MemberService() {
		
		memberDAO = new MemberDAO();
		profileDAO = new ProfileDAO();
	}

	@Override
	public ActionFoward list(HttpServletRequest request, HttpServletResponse response) {
		
		ActionFoward actionFoward = new ActionFoward();
		
		return actionFoward;
	}

	@Override
	public ActionFoward select(HttpServletRequest request, HttpServletResponse response) {
		//회원정보
		ActionFoward actionFoward = new ActionFoward();
		String method = request.getMethod();
		
		boolean check = true;
		String path = "../WEB-INF/views/member/memberLogin.jsp";
		HttpSession session = request.getSession();
		
		if(method.equals("POST")) {
			
			MemberDTO memberDTO = new MemberDTO();
			
			memberDTO.setId(request.getParameter("id"));
			memberDTO.setPw(request.getParameter("pw"));
			
			String id = request.getParameter("id");
			
			
			try {
				
				memberDTO = memberDAO.memberLogin(memberDTO);
				memberDTO = memberDAO.selectOne(id);
				
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			//체크값 확인
			//쿠키 생성
			String check2 = request.getParameter("check");
			System.out.println(check2);
			
			if(check2 != null) {
				
				Cookie c = new Cookie("id", memberDTO.getId());
				response.addCookie(c);
				
			} else {
				
				Cookie c = new Cookie("id", "");
				response.addCookie(c);
				
			}
			
			if(memberDTO != null) {
				
				check = false;
				path = "../index.do";
				session.setAttribute("session", memberDTO);
				request.setAttribute("select", memberDTO);
				
				
			} else {
				
				request.setAttribute("message", "Login Fail");
				request.setAttribute("path", "./memberLogin");
				check = true;
				path = "../WEB-INF/views/common/result.jsp";
				
			}
		} 
		
		actionFoward.setCheck(check);
		actionFoward.setPath(path);
		
		
		return actionFoward;
	}

	@Override
	public ActionFoward insert(HttpServletRequest request, HttpServletResponse response) {
		//회원가입
		ActionFoward actionFoward = new ActionFoward();
		String method = request.getMethod();
		boolean check = true;
		String path = "../WEB-INF/views/member/memberJoin.jsp";
		
		if(method.equals("POST")) {
			
			MemberDTO memberDTO = new MemberDTO();
			int result = 0;
			
			String saveDirectory = request.getServletContext().getRealPath("upload");
			String encoding = "UTF-8";
			int maxPostSize = 1024*1024*10;
			MultipartRequest multi =  null;
			
			try {
				
				multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			String fname = multi.getFilesystemName("profile");
			String oname = multi.getOriginalFileName("profile");
			
			
			ProfileDTO profileDTO = new ProfileDTO();
			profileDTO.setFname(fname);
			profileDTO.setOname(oname);
			
			memberDTO.setId(multi.getParameter("id"));
			memberDTO.setPw(multi.getParameter("pw"));
			memberDTO.setName(multi.getParameter("name"));
			memberDTO.setEmail(multi.getParameter("email"));
			memberDTO.setPhone(multi.getParameter("phone"));
			memberDTO.setAge(Integer.parseInt(multi.getParameter("age")));
			
			Connection con = null;
			
			try {
				
				con = DBConnector.getConnect();
				con.setAutoCommit(false);
				
				result = memberDAO.memberJoin(memberDTO, con);
				
				
				profileDTO.setId(multi.getParameter("id"));
				result = profileDAO.insert(profileDTO, con);
				
				if(result < 1) {
					
					throw new Exception();
					
				}
				
				con.commit();
				
			} catch (Exception e) {
				e.printStackTrace();
				result = 0;
				
				try {
					
					con.rollback();
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
				
			} finally {
				
				try {
				
				con.setAutoCommit(true);
				con.close();
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
			}
			
			if(result > 0) {
				
				check = false;
				path = "../index.do";
				
			} else {
				
				request.setAttribute("message", "Join Fail");
				request.setAttribute("path", "./memberJoin");
				check = true;
				path = "../WEB-INF/views/common/result.jsp";
				
			}
		
		}
		actionFoward.setCheck(check);
		actionFoward.setPath(path);
		
		return actionFoward;
	}

	@Override
	public ActionFoward update(HttpServletRequest request, HttpServletResponse response) {
		//회원수정
		ActionFoward actionFoward = new ActionFoward();
		
		return actionFoward;
		
	}

	@Override
	public ActionFoward delete(HttpServletRequest request, HttpServletResponse response) {
		//회원탈퇴
		ActionFoward actionFoward = new ActionFoward();
		
		return actionFoward;
	}
	
	

}
