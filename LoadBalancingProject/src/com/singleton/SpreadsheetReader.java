package com.singleton;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.memcache.BaseMemcacheService;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

public class SpreadsheetReader {
	public static Logger log = Logger.getLogger(SpreadsheetReader.class.getName());
	SpreadsheetService service = null;
	List<WorksheetEntry> worksheets = null;
	WorksheetEntry worksheet = null;
	ListQuery listQuery = null;
	ListFeed listFeed = null;
	List<ListEntry> listEntries = null;
	Singleton singleton=null; 
	
	MemcacheService cacheData;
	
	public SpreadsheetReader() {
		singleton = Singleton.getInstance();
		cacheData = (MemcacheService) MemcacheServiceFactory.getMemcacheService();
		((BaseMemcacheService) cacheData).setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		log.warning("singleton = "+(singleton==null));
	}
	public List<ListEntry> getListFeed(String spreadsheetNameOrId, String worksheetName,int flag)
	{
		try 
		{
			worksheets=null;
			if(service == null)
				service = singleton.getSpreadsheetservice();
			if(worksheets == null)
				worksheets = singleton.getWorksheets(spreadsheetNameOrId,flag);

			log.warning("service object = "+(service==null)+" worksheet object = "+(worksheet == null));
			worksheet = getWorksheetByName(worksheetName);
			ListQuery listQuery = new ListQuery(worksheet.getListFeedUrl());
			ListFeed listFeed = service.query(listQuery, ListFeed.class);
			listEntries = listFeed.getEntries();
			if((listEntries.size() != 0))
			{
				return listEntries;
			}
		} 
		catch (GeneralSecurityException | IOException | ServiceException e) 
		{
			e.printStackTrace(System.err);
			singleton.resetService();
			log.warning("Exception occured in getListFeed() of SpreadsheetReader class:"+e.toString());
			service=null;
			worksheets=null;
			return getListFeed(spreadsheetNameOrId,worksheetName,flag);
		}
		return null;
	}//end getListFeed()

	public List<ListEntry> getListFeedWhere(String spreadsheetNameOrId, String worksheetName,int flag,String columnName,String Key)
	{		
		try 
		{
			worksheets=null;
			if(service == null)
				service = singleton.getSpreadsheetservice();
			if(worksheets == null)
				worksheets = singleton.getWorksheets(spreadsheetNameOrId,flag);
			worksheet = getWorksheetByName(worksheetName);
			ListQuery listQuery = new ListQuery(worksheet.getListFeedUrl());
			listQuery.setSpreadsheetQuery(columnName+"="+Key);
			ListFeed listFeed = service.query(listQuery, ListFeed.class);
			listEntries = listFeed.getEntries();
			if((listEntries.size() != 0))
			{
				return listEntries;
			}
		} 
		catch (GeneralSecurityException | IOException | ServiceException e) 
		{
			singleton.resetService();
			service=null;
			worksheets=null;
			log.warning("Exception occured in getListFeed() of SpreadsheetReader class:"+e.toString());
			return getListFeedWhere(spreadsheetNameOrId,worksheetName,flag,columnName,Key);
		}
		return null;
	}
	//Read From Spreadsheet by some query if data present in cache then return from cache
	private List<ListEntry> getListFeedWhere(String spreadsheetNameOrId, String worksheetName,int flag,String columnName,String Key,String cacheKey)
	{		
		try 
		{
			listEntries = null;
			//Check for Cache
			if(readFromCache(cacheKey)!=null)
			{
				//Cache Hit
				listEntries.add((ListEntry)readFromCache(cacheKey));
				return listEntries;
			}
			else
			{
				//Cache Miss
				if(service == null)
					service = singleton.getSpreadsheetservice();
				if(worksheets == null)
					worksheets = singleton.getWorksheets(spreadsheetNameOrId,flag);
				worksheet = getWorksheetByName(worksheetName);
				ListQuery listQuery = new ListQuery(worksheet.getListFeedUrl());
				listQuery.setSpreadsheetQuery(columnName+"="+Key);
				ListFeed listFeed = service.query(listQuery, ListFeed.class);
				listEntries = listFeed.getEntries();
				if((listEntries.size() != 0))
				{
					return listEntries;
				}
			}
		} 
		catch (GeneralSecurityException | IOException | ServiceException e) 
		{
			singleton.resetService();
			service=null;
			worksheets=null;
			log.warning("Exception occured in getListFeed() of SpreadsheetReader class:"+e.toString());
			return getListFeedWhere(spreadsheetNameOrId,worksheetName,flag,columnName,Key,cacheKey);
		}
		return null;
	}
	private WorksheetEntry getWorksheetByName(String worksheetName)
	{
		for(WorksheetEntry worksheet: worksheets)
		{
			log.warning("worksheetname:"+worksheet.getTitle().getPlainText());
			log.warning("worksheetName2:"+worksheetName);
			if(worksheet.getTitle().getPlainText().equals(worksheetName))
			{
				log.warning("worksheetname:"+worksheet.getTitle().getPlainText());
				return worksheet;
			}
		}
		return null;
	}//end getWorksheetByName()
	private Object readFromCache(String cacheKey)
	{
		return cacheData.get(cacheData);
	}
}//end SpreadsheetReader Class
