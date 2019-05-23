package com.iu.profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iu.util.DBConnector;

public class ProfileDAO {
	
	//delete
	
	
	//update

	
	
	//select
	public ProfileDTO selectOne(String id) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select * from profile where id=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, id);
		
		ResultSet rs = st.executeQuery();
		
		ProfileDTO profileDTO = null;
		
		if(rs.next()) {
			
			profileDTO = new ProfileDTO();
			
			profileDTO.setPnum(rs.getInt("pnum"));
			profileDTO.setId(rs.getString("id"));
			profileDTO.setFname(rs.getString("fname"));
			profileDTO.setOname(rs.getString("oname"));
			
		}
		
		DBConnector.disConnect(con, st, rs);
		
		return profileDTO;
		
	}
	
	//insert
	public int insert(ProfileDTO profileDTO, Connection con) throws Exception {
		
		
		String sql = "insert into profile values(point_seq.nextval, ?, ?, ?)";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, profileDTO.getId());
		st.setString(2, profileDTO.getOname());
		st.setString(3, profileDTO.getFname());
		
		int result = st.executeUpdate();
		
		
		return result;
		
		
	}

}
