package com.iu.point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import com.iu.util.DBConnector;

public class PointDAO {
	
	
	public static void main(String[] args) {
		
		PointDAO pointDAO = new PointDAO();
		Random random = new Random();
		
		for (int i = 0; i < 100; i++) {
			
			PointDTO pointDTO = new PointDTO();
			pointDTO.setName("name : " + i);
			pointDTO.setKor(random.nextInt(101));
			pointDTO.setEng(random.nextInt(101));
			pointDTO.setMath(random.nextInt(101));
			pointDTO.setTotal(pointDTO.getKor() + pointDTO.getEng() + pointDTO.getMath());
			pointDTO.setAvg(pointDTO.getTotal() / 3.0);
			
			try {
				pointDAO.insert(pointDTO);
				Thread.sleep(300);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		
	}
	
	
	//getTotalCount 
	//전체 글 갯수 받아오는거
	
	public int getTotalCount(String kind, String search) throws Exception{
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select count(num) from point where "+ kind +" like ?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, "%"+search+"%");
		
		ResultSet rs = st.executeQuery();
		
		rs.next();
		
		int result = rs.getInt(1);
		
		DBConnector.disConnect(con, st, rs);
		
		return result;
	}
	
	
	
	
	//메서드명 selectList
	//매개변수 없음
	//리턴 ArrayList<PointDTO>
	//예외던지기
	
	public ArrayList<PointDTO> selectList(String kind, String search, int startRow, int lastRow) throws Exception {
		
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select * from "
				+ "(select rownum R, P.* from "
				+ "(select * from point where "+ kind +" like ? order by num desc) P) "
				+ "where r between ? and ?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, "%"+search+"%");
		st.setInt(2, startRow);
		st.setInt(3, lastRow);
		
		ResultSet rs = st.executeQuery();
		
		ArrayList<PointDTO> ar = new ArrayList<PointDTO>();
		
		while(rs.next()) {
			
			PointDTO pointDTO = new PointDTO();
			
			pointDTO.setNum(rs.getInt("num"));
			pointDTO.setName(rs.getString("name"));
			pointDTO.setKor(rs.getInt("kor"));
			pointDTO.setEng(rs.getInt("eng"));
			pointDTO.setMath(rs.getInt("math"));
			pointDTO.setTotal(rs.getInt("total"));
			pointDTO.setAvg(rs.getDouble("avg"));
			
			ar.add(pointDTO);
			
			
			
		}
		
		DBConnector.disConnect(con, st, rs);
		
		return ar;
		
	}
	
	
	//메서드명 selectOne
	//매개변수 int
	//리턴 pointDTO
	//예외 던지기
	
	public PointDTO selectOne(int num) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select * from point where num=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, num);
		
		ResultSet rs = st.executeQuery();
		
		PointDTO pointDTO = null;
		
		if(rs.next()) {
			
			pointDTO = new PointDTO();
			
			pointDTO.setNum(rs.getInt("num"));
			pointDTO.setName(rs.getString("name"));
			pointDTO.setKor(rs.getInt("kor"));
			pointDTO.setEng(rs.getInt("eng"));
			pointDTO.setMath(rs.getInt("math"));
			pointDTO.setTotal(rs.getInt("total"));
			pointDTO.setAvg(rs.getDouble("avg"));
			
			
		} 
		
		DBConnector.disConnect(con, st, rs);
		
		return pointDTO;
	}
	
	
	//메서드명 delete,
	//리턴은 int
	//매개변수
	//매개변수 int 예외는 던지기
	
	public int delete(int num) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "DELETE point WHERE num=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, num);
		
		int result = st.executeUpdate();
				
		DBConnector.disConnect(con, st);
		
		return result;
		
		
	}
	
	
	
	//메서드명 update
	//리턴은 int
	//매개변수 PointDTO 예외는 던지기
	
	
	public int update(PointDTO pointDTO) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "update point set name=?, kor=?, eng=?, math=?, "
				+ "total=?, avg=? where num=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, pointDTO.getName());
		st.setInt(2, pointDTO.getKor());
		st.setInt(3, pointDTO.getEng());
		st.setInt(4, pointDTO.getMath());
		st.setInt(5, pointDTO.getTotal());
		st.setDouble(6, pointDTO.getAvg());
		st.setInt(7, pointDTO.getNum());
		
		int result = st.executeUpdate();
		
		DBConnector.disConnect(con, st);
		
		return result;
		
	}
	
	
	//메서드명 insert
	//리턴은 int
	//매개변수 pointDTO
	
	public int insert(PointDTO pointDTO) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "insert into point values(point_seq.nextval, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, pointDTO.getName());
		st.setInt(2, pointDTO.getKor());
		st.setInt(3, pointDTO.getEng());
		st.setInt(4, pointDTO.getMath());
		st.setInt(5, pointDTO.getTotal());
		st.setDouble(6, pointDTO.getAvg());
		
		int result = st.executeUpdate();
		
		DBConnector.disConnect(con, st);
		
		return result;
	}
	
	
	

}
