package com.iu.notice;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.Action;
import com.iu.action.ActionFoward;
import com.iu.page.SearchMakePage;
import com.iu.page.SearchPager;
import com.iu.page.SearchRow;
import com.iu.upload.UploadDAO;
import com.iu.upload.UploadDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public abstract class NoticeService implements Action{ //추상클래스 Action 인터페이스 상속
	
	//서비스안에 있는 리턴은 전부 ActionFoward로
	
	private NoticeDAO noticeDAO;
	private UploadDAO uploadDAO;
	
	public NoticeService() {
		noticeDAO = new NoticeDAO();
		uploadDAO = new UploadDAO();
	}
	
	
	@Override
	public ActionFoward list(HttpServletRequest request, HttpServletResponse response) {
		
		ActionFoward actionFoward = new ActionFoward();
		
		int curPage = 1; //현재페이지
		
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		} catch (Exception e) {
			
		}
		
		String kind = request.getParameter("kind"); //검색하기
		String search = request.getParameter("search");
		//c,w,t
		
		SearchMakePage s = new SearchMakePage(curPage, kind, search);
		
		//1.row
		SearchRow searchRow = s.makeRow();
		ArrayList<NoticeDTO> ar = new ArrayList<NoticeDTO>();
		
		try {

			ar = noticeDAO.selectList(searchRow);
			
						
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		//2.page
		
		try {
			int totalCount = noticeDAO.getTotalCount(searchRow);
			SearchPager searchPager = s.makePage(totalCount);
			
			request.setAttribute("pager", searchPager);
			request.setAttribute("list", ar);
			
			actionFoward.setCheck(true); //포워드로 보내겠다
			actionFoward.setPath("../WEB-INF/views/notice/noticeList.jsp");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			request.setAttribute("message", "Server Error");
			request.setAttribute("path", "../index.do");
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/views/common/result.jsp");
		}
				
		return actionFoward;
	}
	
	// -------------------------------------------- SELECT START

	@Override
	public ActionFoward select(HttpServletRequest request, HttpServletResponse response) {
		
		ActionFoward actionFoward = new ActionFoward();
		
		//글이 있으면 출력
		//글이 없으면 result.jsp로 보내서 메세지 (삭제되었거나, 없는 글 입니다) 알럿창 띄우고 /리스트로 다시 돌아감
		
		NoticeDTO noticeDTO = null;
		UploadDTO uploadDTO = null;
		
		int num = 0;
		
		try {
			
			num = Integer.parseInt(request.getParameter("num")); //여기서 문제 발생하면 catch로 넘어감
			noticeDTO = noticeDAO.selectOne(num);
			uploadDTO = uploadDAO.selectOne(num);
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		String path="";
		
		if (noticeDTO != null) {
			
			request.setAttribute("select", noticeDTO);
			request.setAttribute("upload", uploadDTO);
			path="../WEB-INF/views/notice/noticeSelect.jsp";
			
		} else {
			
			request.setAttribute("message", "삭제되었거나, 없는 글입니다");
			request.setAttribute("path", "./noticeList");
			path="../WEB-INF/views/common/result.jsp";
			
		}
		
		actionFoward.setCheck(true);
		actionFoward.setPath(path);
		
		return actionFoward;
	}

	@Override
	public ActionFoward insert(HttpServletRequest request, HttpServletResponse response) {
		
		ActionFoward actionFoward = new ActionFoward();
		
		String method = request.getMethod(); //GET, POST
		boolean check = true;
		String path = "../WEB-INF/views/notice/noticeWrite.jsp";
		
		if(method.equals("POST")) {
			
			NoticeDTO noticeDTO = new NoticeDTO();
			
			//리퀘스트를 하나로 합치는 작업
			String saveDirectory = request.getServletContext().getRealPath("upload");
			System.out.println(saveDirectory); 
			//가상의 경로가 나옴 , 실제로는 upload 폴더를 가리킴 , 배포하면 실제로 들어감, 저장할 파일의 경로
			
			int maxPostSize = 1024*1024*10; //단위는 바이트 단위 , 최대 크기를 설정
			String encoding = "UTF-8";
			
			MultipartRequest multi = null;
			try {
				multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
				//객체가 만들어짐과 동시에 파일이 saveDirectory에 저장이 됨 , 파일명은 올릴때 저장이 됨
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//서버에 저장된 이름
			String fileName = multi.getFilesystemName("f1"); //파일의 파라미터이름
			//클라이언트가 업로드할때의 파일 이름
			String oName = multi.getOriginalFileName("f1"); //파일의 파라미터이름
			
			
			UploadDTO uploadDTO = new UploadDTO();
			uploadDTO.setFname(fileName);
			uploadDTO.setOname(oName);

			
			noticeDTO.setTitle(multi.getParameter("title"));
			noticeDTO.setName(multi.getParameter("name"));
			noticeDTO.setContents(multi.getParameter("contents"));
			int result = 0;
			
			try {
				

				int num = noticeDAO.getNum(); //DAO의 MAX 메소드
				noticeDTO.setNum(num);
				result = noticeDAO.insert(noticeDTO);
				uploadDTO.setNum(num);
				result = uploadDAO.insert(uploadDTO);
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			if(result > 0) {
				
				check = false;
				path = "./noticeList";
		
			} else {
			
				request.setAttribute("message", "등록 실패");
				request.setAttribute("path", "./noticeList");
				check = true;
				path = "../WEB-INF/views/common/result.jsp";
				
			} //POST 끝
		
		} 
		
		actionFoward.setCheck(check);
		actionFoward.setPath(path);
		
		
		return actionFoward;
	}

	@Override
	public ActionFoward update(HttpServletRequest request, HttpServletResponse response) {
		
		ActionFoward actionFoward = new ActionFoward();
		
		String method = request.getMethod();
		boolean check = true;
		String path = "../WEB-INF/views/notice/noticeUpdate.jsp";
		int num = Integer.parseInt(request.getParameter("num"));
		System.out.println(num);
		
		int result = 0;
		
		
		if(method.equals("POST")) {
			
			NoticeDTO noticeDTO = new NoticeDTO();
			
			String saveDirectory = request.getServletContext().getRealPath("upload");
			int maxPostSize = 1024*1024*10;
			String encoding = "UTF-8";
			MultipartRequest multi = null;
			
			try {
				
				multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			
			String filename = multi.getFilesystemName("f1");
			String oName = multi.getOriginalFileName("f1");
			
			noticeDTO.setNum(Integer.parseInt(multi.getParameter("num")));
			noticeDTO.setTitle(multi.getParameter("title"));
			noticeDTO.setName(multi.getParameter("name"));
			noticeDTO.setContents(multi.getParameter("contents"));
			

			try {
				
				result = noticeDAO.update(noticeDTO);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			
			if(result > 0) {
				
				check = false;
				path = "./noticeSelect?num="+num;
				
				
			} else {
				
				request.setAttribute("message", "수정 실패");
				request.setAttribute("path", "./noticeWrite");
				check = true;
				path = "../WEB-INF/views/common/result.jsp";
				
			}
			
			
		} //POST끝
		
		actionFoward.setCheck(check);
		actionFoward.setPath(path);

		
		return actionFoward;
	}

	@Override
	public ActionFoward delete(HttpServletRequest request, HttpServletResponse response) {
		
		ActionFoward actionFoward = new ActionFoward();
		
		int result = 0;
		int num = Integer.parseInt(request.getParameter("num"));
		boolean check = true;
		String path = "../WEB-INF/views/notice/noticeList";
		
		try {
			
			result = noticeDAO.delete(num);
			
			if(result > 0) {
				
				check = false;
				path = "./noticeList";
				
				
			} else {
				
				request.setAttribute("message", "삭제실패");
				request.setAttribute("path", "./noticeList");
				check = true;
				path = "../WEB-INF/views/common/result.jsp";
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		actionFoward.setCheck(check);
		actionFoward.setPath(path);
		
		
		
		return actionFoward;
	}

	
	

}
