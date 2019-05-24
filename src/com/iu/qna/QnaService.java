package com.iu.qna;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.Action;
import com.iu.action.ActionFoward;
import com.iu.page.SearchMakePage;
import com.iu.page.SearchPager;
import com.iu.page.SearchRow;
import com.iu.upload.UploadDAO;
import com.iu.upload.UploadDTO;
import com.iu.util.DBConnector;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public abstract class QnaService implements Action{
	
	private QnaDAO qnaDAO;
	private UploadDAO uploadDAO;
	
	public QnaService() {
		qnaDAO = new QnaDAO();
		uploadDAO = new UploadDAO();
		
	}

	@Override
	public ActionFoward list(HttpServletRequest request, HttpServletResponse response) {
		ActionFoward actionFoward = new ActionFoward();
		
		boolean check = true;
		String path = "../WEB-INF/views/qna/qnaList.jsp";
		
		//페이징처리
		int curPage = 1; //현재 페이지
		
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		} catch (Exception e) {
			
		}
		
		String kind = request.getParameter("kind"); //c, t, w
		String search = request.getParameter("search"); //검색하기
		
		
		SearchMakePage s = new SearchMakePage(curPage, kind, search);
		
		//1.row
		SearchRow searchRow = s.makeRow();
		int totalCount = 0;
		
		try {
			
			ArrayList<QnaDTO> ar = qnaDAO.list(searchRow);
			
			//2.page
			totalCount = qnaDAO.getTotalCount(searchRow);
			request.setAttribute("list", ar);
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			request.setAttribute("message", "Server Error");
			request.setAttribute("path", "../index.do");
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/views/common/result.jsp");
			
		}
		
		SearchPager searchPager = s.makePage(totalCount);
		request.setAttribute("pager", searchPager);
		
		actionFoward.setCheck(true);
		actionFoward.setPath(path);
		
		return actionFoward;
	}

	@Override
	public ActionFoward select(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@Override
	public ActionFoward insert(HttpServletRequest request, HttpServletResponse response) {
		
		ActionFoward actionFoward = new ActionFoward();
		String method = request.getMethod();
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/views/qna/qnaWrite.jsp");
		
		if(method.equals("POST")) {
			
			String saveDirectory = request.getServletContext().getRealPath("/upload");
			int maxPostSize = 1024*1024*10;
			String encoding = "UTF-8";
			Connection con = null;
			
			try {
				
				MultipartRequest multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());

//				***************************** 파라미터 이름이 다 다를때				
				Enumeration<String> e= multi.getFileNames(); //파일의 파라미터 이름들 //파일 인풋 name이 몇개 올지 몰라서 생성
				ArrayList<UploadDTO> ar = new ArrayList<UploadDTO>();
				
				while(e.hasMoreElements()) {
					
					String s = e.nextElement();
					String fname = multi.getFilesystemName(s);
					String oname = multi.getOriginalFileName(s);
					
					UploadDTO uploadDTO = new UploadDTO();
					
					uploadDTO.setFname(fname);
					uploadDTO.setOname(oname);
					
					ar.add(uploadDTO);
					
				} 
				
				QnaDTO qnaDTO = new QnaDTO();
				qnaDTO.setTitle(multi.getParameter("title"));
				qnaDTO.setWriter(multi.getParameter("name"));
				qnaDTO.setContents(multi.getParameter("contents"));
				
				
				try {
					con = DBConnector.getConnect();
					con.setAutoCommit(false);
					
					//1. 시퀀스 번호
					int num = qnaDAO.getNum();
					qnaDTO.setNum(num);
					
					
					//2.qna insert
					num = qnaDAO.insert(qnaDTO, con);
					
					//3.upload insert
					for(UploadDTO uploadDTO : ar) {
						
						uploadDTO.setNum(qnaDTO.getNum());
						num = uploadDAO.insert(uploadDTO, con);
						
						if(num < 1) {
							
							throw new Exception();
						}
						
					}
					
					con.commit();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				
			} catch (IOException e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				
			} finally {
				
				try {
					con.setAutoCommit(true);
					con.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
			actionFoward.setCheck(false);
			actionFoward.setPath("./qnaList");
			
		} //POST끝
		
		
		return actionFoward;
	}

	@Override
	public ActionFoward update(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@Override
	public ActionFoward delete(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}
	
	

}
