package com.iu.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import com.iu.page.SearchRow;
import com.iu.util.DBConnector;

public class NoticeDAO {
	
	
	public static void main(String[] args) {
		
		NoticeDAO noticeDAO = new NoticeDAO();
		Random random = new Random();
		
		for(int i = 0; i < 100; i++) {
			
			NoticeDTO noticeDTO = new NoticeDTO();
			noticeDTO.setTitle("title"+i);
			noticeDTO.setContents("contents"+random.nextInt(100));
			noticeDTO.setName("name"+i);
			
			try {
				noticeDAO.insert(noticeDTO);
				Thread.sleep(300);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
	}
	
	
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
	
	
	public int insert(NoticeDTO noticeDTO) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "insert into notice values(notice_seq.nextval, ?, ?, ?, sysdate, 0)";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, noticeDTO.getTitle());
		st.setString(2, noticeDTO.getContents());
		st.setString(3, noticeDTO.getName());
		
		
		int result = st.executeUpdate();
		
		DBConnector.disConnect(con, st);
		
		return result;
	}
	

}
