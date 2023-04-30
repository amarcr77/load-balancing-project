package com.singleton;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class ReInitiateSpreadsheetService extends HttpServlet
{
	private static final Logger log = Logger.getLogger(ReInitiateSpreadsheetService.class.getName());
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		Singleton singleton = Singleton.getInstance();
		try 
		{
			singleton.setSpreadsheetservice();
		}
		catch (Exception e) 
		{
			e.printStackTrace(System.err);
		}
	}
}
