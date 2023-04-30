package com.singleton;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.logging.Logger;
import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

public class SpreadsheetWriter {
	public static Logger log = Logger.getLogger(SpreadsheetReader.class.getName());
	SpreadsheetService service = null;
	List<WorksheetEntry> worksheets = null;
	WorksheetEntry worksheet = null;
	ListQuery listQuery = null;
	ListFeed listFeed = null;
	List<ListEntry> listEntries = null;
	Singleton singleton=null; 
	public SpreadsheetWriter() {
		singleton = Singleton.getInstance();
		log.warning("singleton = "+(singleton==null));
	}
	//insert into spreadsheet
	public void insertRow(String spreadsheetNameOrId, String worksheetName,int flag,ListEntry row)
	{
		try 
		{
			if(service == null)
				service = singleton.getSpreadsheetservice();
			if(worksheets == null)
				worksheets = singleton.getWorksheets(spreadsheetNameOrId,flag);

			log.warning("service object = "+(service==null)+" worksheet object = "+(worksheet == null));
			worksheet = getWorksheetByName(worksheetName);
			URL listfeedurl=worksheet.getListFeedUrl();
			service.insert(listfeedurl, row);
		} 
		catch (GeneralSecurityException | IOException | ServiceException e) 
		{
			e.printStackTrace(System.err);
			singleton.resetService();
			service=null;
			worksheets=null;
			log.warning("Exception occured in insertRow() of SpreadsheetWriter class:"+e.toString());
			insertRow(spreadsheetNameOrId,worksheetName,flag,row);
		}
	}
	//insert into spreadsheet as well as in Cache
	public void insertRow(String spreadsheetNameOrId, String worksheetName,int flag,ListEntry row,String cacheKey)
	{
		try 
		{
			if(service == null)
				service = singleton.getSpreadsheetservice();
			if(worksheets == null)
				worksheets = singleton.getWorksheets(spreadsheetNameOrId,flag);

			log.warning("service object = "+(service==null)+" worksheet object = "+(worksheet == null));
			worksheet = getWorksheetByName(worksheetName);
			URL listfeedurl=worksheet.getListFeedUrl();
			service.insert(listfeedurl, row);
			addToCatch(cacheKey, row);
		} 
		catch (GeneralSecurityException | IOException | ServiceException e) 
		{
			e.printStackTrace(System.err);
			singleton.resetService();
			service=null;
			worksheets=null;
			log.warning("Exception occured in insertRow() of SpreadsheetWriter class:"+e.toString());
			insertRow(spreadsheetNameOrId,worksheetName,flag,row);
		}
	}
	//insert multiple rows into spreadsheet
	public void insertRows(String spreadsheetNameOrId, String worksheetName,int flag,List<ListEntry> listEntries)
	{
		try 
		{
			if(service == null)
				service = singleton.getSpreadsheetservice();
			if(worksheets == null)
				worksheets = singleton.getWorksheets(spreadsheetNameOrId,flag);
			log.warning("service object = "+(service==null)+" worksheet object = "+(worksheet == null));
			worksheet = getWorksheetByName(worksheetName);
			URL listfeedurl=worksheet.getListFeedUrl();
			for(ListEntry listEntry:listEntries)
			{
				service.insert(listfeedurl, listEntry);
			}
		} 
		catch (GeneralSecurityException | IOException | ServiceException e) 
		{
			e.printStackTrace(System.err);
			singleton.resetService();
			service=null;
			worksheets=null;
			insertRows(spreadsheetNameOrId, worksheetName,flag, listEntries);
		}
	}
	//insert multiple rows into spreadsheet and add to Cache
	public void insertRows(String spreadsheetNameOrId, String worksheetName,int flag, List<ListEntry> listEntries, String CacheKeyColumnName)
	{
		try 
		{
			if(service == null)
				service = singleton.getSpreadsheetservice();
			if(worksheets == null)
				worksheets = singleton.getWorksheets(spreadsheetNameOrId,flag);
			log.warning("service object = "+(service==null)+" worksheet object = "+(worksheet == null));
			worksheet = getWorksheetByName(worksheetName);
			URL listfeedurl=worksheet.getListFeedUrl();
			for(ListEntry listEntry:listEntries)
			{
				service.insert(listfeedurl, listEntry);
				addToCatch(listEntry.getCustomElements().getValue(CacheKeyColumnName), listEntry);
			}
		} 
		catch (GeneralSecurityException | IOException | ServiceException e) 
		{
			e.printStackTrace(System.err);
			singleton.resetService();
			service=null;
			worksheets=null;
			insertRows(spreadsheetNameOrId, worksheetName,flag,listEntries);
		}
	}
	
	//updateRow
	public boolean updateRow(ListEntry listEntry)
	{
		try
		{
			listEntry.update();
		}
		catch (IOException | ServiceException e) 
		{
			e.printStackTrace(System.err);
			singleton.resetService();
			try 
			{
				singleton.getSpreadsheetservice();
				updateRow(listEntry);
			} 
			catch (GeneralSecurityException | IOException | ServiceException e1) 
			{
				e1.printStackTrace();
			}
		}
		return false;
	}
	
	
	private WorksheetEntry getWorksheetByName(String worksheetName)
	{
		for(WorksheetEntry worksheet: worksheets)
		{
			if(worksheet.getTitle().getPlainText().equals(worksheetName))
			{
				return worksheet;
			}
		}
		return null;
	}//end getWorksheetByName()
	private void addToCatch(String key,ListEntry listEntry)
	{
	}
}
