/*package web.DBManage;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

*//**
@author*
@paramater*
@return
*//*
public class myServletContextListener implements ServletContextListener{

	private static DBOperate db;
	public void contextDestroyed(ServletContextEvent sce)
	{   		  
		try {
			db.closeDB();
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("db connection close");
    }
	public void contextInitialized(ServletContextEvent sce) 
	{   
		db = new DBOperate();
		try 
		{
			db.createDB();
		} 
		catch (Exception e)
		{
			System.out.println("db initialization failed");
			// TODO Auto-generated catch block		
			e.printStackTrace();
		}
		 System.out.println("db create successfully");
	}
	public static DBOperate getLdbInstance(){
		return db;
	}

}
*/