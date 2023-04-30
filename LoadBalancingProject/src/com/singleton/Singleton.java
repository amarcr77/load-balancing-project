package com.singleton;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

public class Singleton 
{
	public static final Logger log = Logger.getLogger(Singleton.class.getName());
	private static Singleton instance = null;
	private SpreadsheetService service;
	private SpreadsheetFeed feed;
	List<WorksheetEntry> worksheets;
	List<SpreadsheetEntry> spreadsheets;
	private Singleton() 
	{
		// Exists only to defeat instantiation.
	}
	public static Singleton getInstance() 
	{
		if(instance == null) 
		{
			instance = new Singleton();
		}
		return instance;
	}
	public SpreadsheetService getSpreadsheetservice() throws GeneralSecurityException, IOException, ServiceException
	{
		log.warning("In get getSpreadsheetservice");
		if(service==null)
		{
			log.warning("service object is null");
			return setSpreadsheetservice();
		}
		else
		{
			log.warning("returning old service object");
			return service;
		}
	}
	public SpreadsheetService setSpreadsheetservice() throws GeneralSecurityException, IOException, ServiceException {
		//oauth procedure
		//Oauth code...
		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		HttpTransport httptransport=new NetHttpTransport();
		List<String> scope = new ArrayList<String>();
		scope.add("https://spreadsheets.google.com/feeds");
		GoogleCredential credential = new GoogleCredential.Builder()
		.setTransport(httptransport)
		.setJsonFactory(JSON_FACTORY)
		.setServiceAccountId(config.service_account)
		.setServiceAccountPrivateKeyFromP12File(new File("WEB-INF/"+config.p_12_key))
		.setServiceAccountScopes(scope)
		.build();
		credential.refreshToken();
		SpreadsheetService service=new SpreadsheetService("shopifyweb");
		service.setHeader("Authorization", "Bearer " + credential.getAccessToken());
		service.setConnectTimeout(120000);
		service.setReadTimeout(120000);
		URL metafeedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		//get all the spreadsheets associated to service account
		this.feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
		this.service = service;
		this.spreadsheets=feed.getEntries();
		//get first spreadsheet associated to service account
		/*SpreadsheetEntry sp=this.spreadsheets.get(0);
		//get all the worksheets available in spreadsheet
		WorksheetFeed worksheetFeed = service.getFeed(sp.getWorksheetFeedUrl(), WorksheetFeed.class);
		this.worksheets = worksheetFeed.getEntries();
		 */		return this.service;
	}
	public SpreadsheetFeed getFeed()/*Not Used*/
	{
		return this.feed;
	}
	public List<WorksheetEntry> getWorksheets(String spreadsheetNameOrId,int flag) throws GeneralSecurityException, IOException, ServiceException 
	{
		SpreadsheetEntry sp=null;
		//If service object is null then initialize service object
		if(service==null)
		{
			setSpreadsheetservice();
			if(flag==1)//get spreadsheetbyname flag=1
			{
			sp=getSpreadsheetByName(spreadsheetNameOrId);
			}
			else if(flag==0)//get spreadsheetbyname flag=0
			{
			sp=getSpreadsheetById(spreadsheetNameOrId);	
			}
			WorksheetFeed worksheetFeed = service.getFeed(sp.getWorksheetFeedUrl(), WorksheetFeed.class);
			this.worksheets = worksheetFeed.getEntries();
		}
		else
		{	
			//get all the worksheets available in spreadsheet
			try
			{
				if(flag==1)
				{
				sp=getSpreadsheetByName(spreadsheetNameOrId);
				}
				else if(flag==0)
				{
				sp=getSpreadsheetById(spreadsheetNameOrId);	
				}
				WorksheetFeed worksheetFeed = service.getFeed(sp.getWorksheetFeedUrl(), WorksheetFeed.class);
				this.worksheets = worksheetFeed.getEntries();
			}
			catch(IOException | ServiceException | IndexOutOfBoundsException e)
			{
				//If old service objects access token is expired then reinitialize the spreadsheet service object
				setSpreadsheetservice();
				if(flag==1)
				{
				sp=getSpreadsheetByName(spreadsheetNameOrId);
				}
				else if(flag==0)
				{
				sp=getSpreadsheetById(spreadsheetNameOrId);	
				}
				WorksheetFeed worksheetFeed = service.getFeed(sp.getWorksheetFeedUrl(), WorksheetFeed.class);
				this.worksheets = worksheetFeed.getEntries();
				e.printStackTrace(System.err);
			}
		}
		return this.worksheets;
	}
	public SpreadsheetEntry getSpreadsheetByName(String customerSpreadsheetName)
	{
		String spreadsheetName="";
		for(SpreadsheetEntry spreadsheet:spreadsheets)
		{
			spreadsheetName = spreadsheet.getTitle().getPlainText();
			if(spreadsheetName.equals(customerSpreadsheetName))
			{
				return spreadsheet;
			}
		}
		return null;
	}
	
	public SpreadsheetEntry getSpreadsheetById(String customerSpreadsheetId)
	{
		String spreadsheetId="";
		for(SpreadsheetEntry spreadsheet:spreadsheets)
		{
			spreadsheetId = spreadsheet.getKey();
			log.warning("spreadsheetId:"+spreadsheetId);
			if(spreadsheetId.equals(customerSpreadsheetId))
			{
				return spreadsheet;
			}
		}
		return null;
	}
	
	public void resetService()
	{
		this.service = null;
	}
}