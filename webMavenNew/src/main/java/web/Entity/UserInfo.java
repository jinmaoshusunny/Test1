package web.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
@author*
@paramater*
@return
*/
public class UserInfo {

	private String userName;
	private String loginTime;
	
	public UserInfo()
	{}
	public UserInfo(String username, String logintime)
	{
		this.userName = username;
		this.loginTime = logintime;
	}	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public void setLoginTime(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		this.loginTime=dateFormat.format( new Date());
	}
}
