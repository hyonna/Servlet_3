package com.iu.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.iu.page.SearchRow;
import com.iu.util.DBConnector;

public class QnaDAO {
	
	//시퀀스 num 가져오기 / insert 부분에 시퀀스 부분을 ? 로 처리 하고 getNum으로 처리
		public int getNum() throws Exception {
			
		int result = 0;
			
		Connection con = DBConnector.getConnect();
			
		String sql = "select qna_seq.nextval from dual"; //notice_seq.nextval 이라는 컬럼명이 없으니까 가상의 테이블명
			
		PreparedStatement st = con.prepareStatement(sql);
			
		ResultSet rs = st.executeQuery();
			
		rs.next();
			
		result = rs.getInt(1);
			
		DBConnector.disConnect(con, st, rs);
			
		return result;
		}
	
		
		
	public int insert(QnaDTO qnaDTO, Connection con) throws Exception {
		
		String sql = "insert into qna values(?, ?, ?, ?, sysdate, 0, ?, 0, 0)";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, qnaDTO.getNum());
		st.setString(2, qnaDTO.getTitle());
		st.setString(3, qnaDTO.getContents());
		st.setString(4, qnaDTO.getWriter());
		st.setInt(5, qnaDTO.getNum()); //ref는 원본글의 글번호
		
		int result = st.executeUpdate();
		
		st.close();
		
		return result;
	}
	
	//List, selectOne, write, update, delete, reply
	
	public int getTotalCount(SearchRow searchRow) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select count(num) from qna where "+searchRow.getSearch().getKind()+" like ?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		
		ResultSet rs = st.executeQuery();
		
		rs.next();
		
		int result = rs.getInt(1);
		
		DBConnector.disConnect(con, st, rs);
		
		return result;
	}
	
	
	public ArrayList<QnaDTO> list(SearchRow searchRow) throws Exception {
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select * from "
				+ "(select rownum R, Q.* from "
				+ "(select * from qna where " + searchRow.getSearch().getKind() + " like ? order by ref desc, step asc) Q) "
				+ "where R between ? and ?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		st.setInt(2, searchRow.getStartRow());
		st.setInt(3, searchRow.getLastRow());
		
		ResultSet rs = st.executeQuery();
		
		ArrayList<QnaDTO> ar = new ArrayList<QnaDTO>();
		
		while(rs.next()) {
			
			QnaDTO qnaDTO = new QnaDTO();
			
			qnaDTO.setNum(rs.getInt("num"));
			qnaDTO.setTitle(rs.getString("title"));
			qnaDTO.setContents(rs.getString("contents"));
			qnaDTO.setWriter(rs.getString("writer"));
			qnaDTO.setReg_date(rs.getDate("reg_date"));
			qnaDTO.setHit(rs.getInt("hit"));
			qnaDTO.setRef(rs.getInt("ref"));
			qnaDTO.setStep(rs.getInt("step"));
			qnaDTO.setDepth(rs.getInt("depth"));
			
			ar.add(qnaDTO);
			
		}
		
		DBConnector.disConnect(con, st, rs);
		
		return ar;
		
	}

}