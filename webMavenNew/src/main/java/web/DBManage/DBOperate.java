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
	public DB db;	
	private static String dbPath = "F:/WebMaven/webMavenNew/DBdata";
	public ArrayList getUserInfo(UserInfo loginUserInfo) 
	{
	//	System.out.println("loginUserInfo.userName = "+loginUserInfo.getUserName());
	//	System.out.println("loginUserInfo.loginTIme = "+loginUserInfo.getLoginTime());
		
		ArrayList<UserInfo> userList = new ArrayList<UserInfo>();			
			try {
				Options options = new Options();
				options.createIfMissing(true);
				db = factory.open(new File(dbPath), options);
			} catch (IOException e1) {
				System.out.println("db create failed");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			db.put(bytes(loginUserInfo.getLoginTime()), bytes(loginUserInfo.getUserName()) );
			DBIterator iterator = db.iterator(); 
	//		System.out.println("levelDB create successfully!");
			for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) 
			{
				String loginTime = asString(iterator.peekNext().getKey());
				String userName = asString(iterator.peekNext().getValue());
		//		System.out.println("dbUserName = "+userName+", dbLoginTime = "+loginTime);
				UserInfo userinfo = new UserInfo(userName, loginTime);
				userList.add(userinfo);
			}
		
		try {
			iterator.close();
			db.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("iterator close failed");
			e.printStackTrace();
		}
	//	System.out.println("levelDB finished");
		return userList;
	}
}
