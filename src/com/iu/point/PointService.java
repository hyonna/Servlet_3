package com.iu.point;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.ActionFoward;

public class PointService {
	
	
	private PointDAO pointDAO;
	
		public PointService() {
		
			pointDAO = new PointDAO();
		}

		
		
	public ActionFoward selectList(HttpServletRequest request, HttpServletResponse repResponse) {
			
		ActionFoward actionFoward = new ActionFoward();
			
		return actionFoward;
	}
		
	
	
	
	public ActionFoward selectOne(HttpServletRequest request, HttpServletResponse repResponse) {
		
		ActionFoward actionFoward = new ActionFoward();
		
		return actionFoward;
	}

}
