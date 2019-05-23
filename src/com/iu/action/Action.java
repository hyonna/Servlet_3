package com.iu.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	
	//List
	public abstract ActionFoward list(HttpServletRequest request, HttpServletResponse response);

	//selectOne
	public abstract ActionFoward select(HttpServletRequest request, HttpServletResponse response);
	
	//insert
	public abstract ActionFoward insert(HttpServletRequest request, HttpServletResponse response);
	
	//update
	public abstract ActionFoward update(HttpServletRequest request, HttpServletResponse response);
	
	//delete
	public abstract ActionFoward delete(HttpServletRequest request, HttpServletResponse response);
	
}
