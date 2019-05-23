package com.iu.upload;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iu.util.DBConnector;
import com.sun.crypto.provider.RSACipher;

public class UploadDAO {
	
	//select, insert, update, delete
	
	
	//select
	public UploadDTO selectOne(int num) throws Exception {
		
		UploadDTO uploadDTO = null;
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select * from upload where num=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, num);

		ResultSet rs = st.executeQuery();
		
		if(rs.next()) {
			
			uploadDTO = new UploadDTO();
			
			uploadDTO.setPnum(rs.getInt("pnum"));
			uploadDTO.setNum(rs.getInt("num"));
			uploadDTO.setFname(rs.getString("fname"));
			uploadDTO.setOname(rs.getString("Oname"));
			
		}
		
		DBConnector.disConnect(con, st, rs);
		
		return uploadDTO;
	}
	
	
	
	//insert
	public int insert(UploadDTO uploadDTO, Connection con) throws Exception {
		
		int result = 0;
		
		
		String sql = "insert into upload values(point_seq.nextval, ?, ?, ?)";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, uploadDTO.getNum());
		st.setString(2, uploadDTO.getOname());
		st.setString(3, uploadDTO.getFname());
		
		result = st.executeUpdate();
		
		DBConnector.disConnect(con, st);
		
		return result;
	}

}
