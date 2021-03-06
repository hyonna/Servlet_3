package com.iu.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import com.iu.page.SearchRow;
import com.iu.util.DBConnector;

public class NoticeDAO {
	
	//num 가져오기
	public int getNum() throws Exception {
		
		int result = 0;
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select notice_seq.nextval from dual"; //notice_seq.nextval 이라는 컬럼명이 없으니까 가상의 테이블명
		
		PreparedStatement st = con.prepareStatement(sql);
		
		ResultSet rs = st.executeQuery();
		
		rs.next();
		
		result = rs.getInt(1);
		
		DBConnector.disConnect(con, st, rs);
		
		return result;
	}
	
	
	public int insert(NoticeDTO noticeDTO, Connection con) throws Exception {
		
		
		String sql = "insert into notice values(?, ?, ?, ?, sysdate, 0)";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, noticeDTO.getNum());
		st.setString(2, noticeDTO.getTitle());
		st.setString(3, noticeDTO.getContents());
		st.setString(4, noticeDTO.getName());
		
		
		int result = st.executeUpdate();
		
		st.close();
		
		return result;
	}
	
	
	
//	public static void main(String[] args) {
//		
//		NoticeDAO noticeDAO = new NoticeDAO();
//		Random random = new Random();
//		
//		for(int i = 0; i < 100; i++) {
//			
//			NoticeDTO noticeDTO = new NoticeDTO();
//			noticeDTO.setTitle("title"+i);
//			noticeDTO.setContents("contents"+random.nextInt(100));
//			noticeDTO.setName("name"+i);
//			
//			try {
//				noticeDAO.insert(noticeDTO, null);
//				Thread.sleep(300);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
//		}
//		
//		
//	}
	
	
	//getTotalCount 
	//총 글 갯수 
	
	public int getTotalCount(SearchRow searchRow) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select count(num) from notice where "+ searchRow.getSearch().getKind() +" like ?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		
		ResultSet rs = st.executeQuery();
		
		rs.next();
		
		int result = rs.getInt(1);
		
		DBConnector.disConnect(con, st, rs);
		
		return result;
		
	}
	
	
	public ArrayList<NoticeDTO> selectList(SearchRow searchRow) throws Exception {
		
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select * from " + 
				"(select rownum R, P.* from " + 
				"(select * from notice where "+ searchRow.getSearch().getKind() +" like ? order by num desc) P) " + 
				"where r between ? and ?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		st.setInt(2, searchRow.getStartRow());
		st.setInt(3, searchRow.getLastRow());
		
		ResultSet rs = st.executeQuery();
		
		ArrayList<NoticeDTO> ar = new ArrayList<NoticeDTO>();
		
		while(rs.next()) {
			
			NoticeDTO noticeDTO = new NoticeDTO();
			
			noticeDTO.setNum(rs.getInt("num"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setName(rs.getString("name"));
			noticeDTO.setReg_date(rs.getDate("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
			
			
			ar.add(noticeDTO);
			
			
		}
		
		DBConnector.disConnect(con, st, rs);
		
		return ar;
		
		
		
	}
	
	
	public NoticeDTO selectOne(int num) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select * from notice where num=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, num);
		
		ResultSet rs = st.executeQuery();
		
		NoticeDTO noticeDTO = null;
		
		if(rs.next()) {
			
			noticeDTO = new NoticeDTO();
			
			noticeDTO.setNum(rs.getInt("num"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setContents(rs.getString("contents"));
			noticeDTO.setName(rs.getString("name"));
			noticeDTO.setReg_date(rs.getDate("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
			
		}
		
		DBConnector.disConnect(con, st, rs);
		
		return noticeDTO;
		
	}
	
	
	
	public int delete(int num) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "DELETE notice WHERE num=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, num);
		
		int result = st.executeUpdate();
		
		DBConnector.disConnect(con, st);
		
		return result;
		
	}
	
	
	
	public int update(NoticeDTO noticeDTO) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "update notice set title=?, contents=?, name=? where num=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		
		st.setString(1, noticeDTO.getTitle());
		st.setString(2, noticeDTO.getContents());
		st.setString(3, noticeDTO.getName());
		st.setInt(4, noticeDTO.getNum());
		
		
		int result = st.executeUpdate();
		
		
		DBConnector.disConnect(con, st);
		
		return result;
	}
	
	
	

}
