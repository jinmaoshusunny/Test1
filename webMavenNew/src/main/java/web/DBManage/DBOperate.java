package web.DBManage;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;

import web.Entity.UserInfo;


/**
@author*
@paramater*
@return
*/
public class DBOperate {
	private String dbPath = "F:/WebMaven/webMavenNew/DBdata";
	public DB db;
	
/*	public void addUserInfo(UserInfo  userinfo) {
		boolean b = false;
		try {			
			Options options = new Options();
			options.createIfMissing(true);
			db = factory.open(new File(dbPath), options);			
		}		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
		try {
			db.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	*/
	public ArrayList getUserInfo(UserInfo loginUserInfo) 
	{
		ArrayList<UserInfo> userList = new ArrayList<UserInfo>();	
		DBIterator iterator = null;
		try {
			Options options = new Options();
			options.createIfMissing(true);
			db = factory.open(new File(dbPath), options);
			iterator = db.iterator();
			for (iterator.seekToFirst(); iterator.hasNext(); iterator.hasNext()) 
			{
				String userName = asString(iterator.peekNext().getKey());
				String loginTime = asString(iterator.peekNext().getValue());
				UserInfo userinfo = new UserInfo(userName, loginTime);
				userList.add(userinfo);
			}
			db.put(bytes(loginUserInfo.getUserName()), bytes(loginUserInfo.getLoginTime()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			iterator.close();
			db.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("iterator close failed");
			e.printStackTrace();
		}
		System.out.println("levelDB finished");
		return userList;
	}
}
