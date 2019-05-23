package com.iu.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.iu.util.DBConnector;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class MemberDAO {
	
	
	
	
	
	public int idCheck(String id) throws Exception {
		
		int result = 0; //0이면 사용가능, 1이면 사용 불가능
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select id from member where id=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, id);
		
		ResultSet rs = st.executeQuery();
		
		if(rs.next()) {
				
				result = 1; 
				
			}
			
		DBConnector.disConnect(con, st, rs);
		
		return result;
		
	}
	
	
	
	public MemberDTO selectOne(String id) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select * from member where id=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, id);
		
		ResultSet rs = st.executeQuery();
		
		MemberDTO memberDTO = null;
		
		if(rs.next()) {
			
			memberDTO = new MemberDTO();
			
			memberDTO.setId(rs.getString("id"));
			memberDTO.setPw(rs.getString("Pw"));
			memberDTO.setName(rs.getString("name"));
			memberDTO.setPhone(rs.getString("phone"));
			memberDTO.setEmail(rs.getString("Email"));
			memberDTO.setAge(rs.getInt("age"));
			
			
		}
		
		DBConnector.disConnect(con, st, rs);
		
		return memberDTO;
		
	}
	
	
	/* 회원정보수정 */
	
	public int memberUpdate(MemberDTO memberDTO) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "update member set pw=?, name=?, phone=?, email=?, age=? where id=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, memberDTO.getPw());
		st.setString(2, memberDTO.getName());
		st.setString(3, memberDTO.getPhone());
		st.setString(4, memberDTO.getEmail());
		st.setInt(5, memberDTO.getAge());
		st.setString(6, memberDTO.getId());
		
		int result = st.executeUpdate();
		
		DBConnector.disConnect(con, st);
		
		return result;
		
	}
	
	/* 로그인 */
	
	public MemberDTO memberLogin(MemberDTO memberDTO) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select * from member where id=? and pw=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, memberDTO.getId());
		st.setString(2, memberDTO.getPw());
		
		ResultSet rs = st.executeQuery();
		
		MemberDTO m = null;
		
		if(rs.next()) {
			
			m = new MemberDTO();
			
			m.setId(rs.getString("id"));
			m.setPw(rs.getString("pw"));
			m.setName(rs.getString("name"));
			m.setPhone(rs.getString("phone"));
			m.setEmail(rs.getString("email"));
			m.setAge(rs.getInt("age"));
			
		}
		
		DBConnector.disConnect(con, st, rs);
		
		return m;
		
	}
	
	
	/* 회원탈퇴 */
	
	public int memberDelete(String id) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "delete member where id=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, id);
		
		int result = st.executeUpdate();
		
		DBConnector.disConnect(con, st);
		
		return result;
		
	}
	
	
	/* 회원가입 */
	
	public int memberJoin(MemberDTO memberDTO, Connection con) throws Exception {
		
		
		String sql = "insert into member values(?, ?, ?, ?, ?, ?)"; //id, pw, name, phone, email, age
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, memberDTO.getId());
		st.setString(2, memberDTO.getPw());
		st.setString(3, memberDTO.getName());
		st.setString(4, memberDTO.getPhone());
		st.setString(5, memberDTO.getEmail());
		st.setInt(6, memberDTO.getAge());
		
		int result = st.executeUpdate();
		
		st.close();
		
		return result;
		
		
	}

}
