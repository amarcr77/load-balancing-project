package com.loadbalancing;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gdata.data.spreadsheet.ListEntry;
import com.singleton.SpreadsheetReader;

public class loginValidate extends HttpServlet {
	private static final Logger log=Logger.getLogger(loginValidate.class.getName());
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		try
		{
			log.warning("usernamer:"+username+",password:"+password);
			
			SpreadsheetReader sr=new SpreadsheetReader();
			List<ListEntry> listfeed=sr.getListFeedWhere(config.LoadbalancingMasterSheet_id,"UserMaster",0,"username",username);
			ListEntry user_record=listfeed.get(0);
			String user_actual_password=user_record.getCustomElements().getValue("password");
			String accesstoken=user_record.getCustomElements().getValue("accesstoken");
			log.warning("user_actual_password:"+user_actual_password);
			if(password.equals(user_actual_password))
			{
				log.warning("login Successfull");
				
				//store details in session 
				 HttpSession session=req.getSession();
				 session.setAttribute("username",username);
				 session.setAttribute("accesstoken",accesstoken);
				
				//redirect user to appropriate site
				 List<ListEntry> cloudmaster_listfeed=sr.getListFeed(config.LoadbalancingMasterSheet_id,"CloudMaster", 0);
				 ListEntry cloudmaster_entry=cloudmaster_listfeed.get(0);
				 int cloud1=Integer.parseInt(cloudmaster_entry.getCustomElements().getValue("cloud1"));
				 int cloud2=Integer.parseInt(cloudmaster_entry.getCustomElements().getValue("cloud2"));
				 int cloud3=Integer.parseInt(cloudmaster_entry.getCustomElements().getValue("cloud3"));
				 
				 log.warning("cloud1:"+cloud1);
				 log.warning("cloud2:"+cloud2);
				 log.warning("cloud3:"+cloud3);
				 
				 String cloud_to_redirect=null;
				 
				 if(cloud1==cloud2 && cloud1==cloud3 && cloud1<3)
				 {
					 cloud_to_redirect="cloud1";
					 log.warning("cloud_to_redirect:"+cloud_to_redirect);
					 cloudmaster_entry.getCustomElements().setValueLocal("cloud1",Integer.toString(cloud1+1));
					 cloudmaster_entry.update();
					 resp.sendRedirect("/cloud1/home.jsp");
				 }
				 else if(cloud1<=cloud2 && cloud1<=cloud3 && cloud1<3)
				 {
					 cloud_to_redirect="cloud1";
					 log.warning("cloud_to_redirect:"+cloud_to_redirect);
					 cloudmaster_entry.getCustomElements().setValueLocal("cloud1",Integer.toString(cloud1+1));
					 cloudmaster_entry.update();
					 resp.sendRedirect("/cloud1/home.jsp");
				 }
				 else if(cloud2<cloud1 && cloud2<=cloud3 && cloud2<3)
				 {
					 cloud_to_redirect="cloud2";
					 log.warning("cloud_to_redirect:"+cloud_to_redirect);
					 cloudmaster_entry.getCustomElements().setValueLocal("cloud2",Integer.toString(cloud2+1));
					 cloudmaster_entry.update();
					 resp.sendRedirect("/cloud2/home.jsp");
				 }
				 else if(cloud3<cloud1 && cloud3<cloud2 && cloud3<3)
				 {
					 cloud_to_redirect="cloud3";
					 log.warning("cloud_to_redirect:"+cloud_to_redirect);
					 cloudmaster_entry.getCustomElements().setValueLocal("cloud3",Integer.toString(cloud3+1));
					 cloudmaster_entry.update();
					 resp.sendRedirect("/cloud3/home.jsp");
				 }
				 
				 //if all cloud filled with 3 i.e. limit exceeded then redirect user to ServiceUnavailable page
				 resp.sendRedirect("/ServiceUnavailable.html");
			}
			else
			{
				log.warning("login failed");
				resp.sendRedirect("/index.html");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
	}
}
