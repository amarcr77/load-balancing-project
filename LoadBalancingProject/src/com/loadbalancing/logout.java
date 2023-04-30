package com.loadbalancing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.util.ServiceException;
import com.singleton.SpreadsheetReader;

public class logout extends HttpServlet {
	private static final Logger log=Logger.getLogger(logout.class.getName());
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try 
		{
			String cloud_logout_req=req.getParameter("cloud");
			HttpSession session=req.getSession();
			
			//clear session data of user
			String username=(String) session.getAttribute("username");
			changeAccessToken(username);
			resp.getWriter().println(username);
			session.removeAttribute("accesstoken");
			session.removeAttribute("username");
			
			//change status of respective cloud 
			SpreadsheetReader sr=new SpreadsheetReader();
			List<ListEntry> cloudmaster_listfeed=sr.getListFeed(config.LoadbalancingMasterSheet_id,"CloudMaster", 0);
			 ListEntry cloudmaster_entry=cloudmaster_listfeed.get(0);
			 int cloud_status=Integer.parseInt(cloudmaster_entry.getCustomElements().getValue("cloud"+cloud_logout_req));
			 cloud_status=cloud_status-1;
			 cloudmaster_entry.getCustomElements().setValueLocal("cloud"+cloud_logout_req,Integer.toString(cloud_status));
			 
				cloudmaster_entry.update();
			 
			resp.sendRedirect("http://loadbalancingproject.appspot.com/");
		} 
		catch (ServiceException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void changeAccessToken(String username)
	{
		  
        try
        {
    		String urlString="https://script.google.com/macros/s/AKfycbzbKcTbbcMyJ_zWf6n3acsm1PW6XhKNbHTDi97wUCByhPd2cozH/exec?username="+username;
    		    		
	        URL url = new URL(urlString.toString());
	        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
	        urlConn.setRequestMethod("GET");
	        urlConn.setDoInput(true);
	        urlConn.setDoOutput(true);              
	        
	        String xml="";	       
	        
	        if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK)
	      	{
	          	BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
	          	String decodedString;
	          	while ((decodedString = in.readLine()) != null) 
	          	{
	          		xml=xml+decodedString;                		
	          	}
	          	in.close();
	          	log.warning(xml);
	      	} 		        
	      	else 
	      	{
	      		log.warning("invalid response from provider error code:"+urlConn.getResponseCode());
	      		BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
	      		String decodedString;
	      		while ((decodedString = in.readLine()) != null) 
	      			{
	      				log.warning(decodedString);
	      			}
	      		in.close();
	        }
        }//try end
        catch(Exception e)
        {
        	e.printStackTrace(System.err);
        }
	}
}
