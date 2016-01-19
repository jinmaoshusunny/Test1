package web.Controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import web.DBManage.DBOperate;
import web.Entity.UserInfo;

/**
@author*
@paramater*
@return
*/
@Controller
public class resultController {
	@RequestMapping(value="message.do",method=RequestMethod.POST)
	public ModelAndView login_jsp(HttpServletRequest request) throws IOException 
	{
		UserInfo userinfo = new UserInfo();		
		String userName = request.getParameter("username");	
		System.out.println(userName);
		Map<String, Object> model = new HashMap<String, Object>();		
		if(userName!=null)
		{			
			DBOperate dboperate = new DBOperate();
			userinfo.setUserName(userName);
			userinfo.setLoginTime();		
			ArrayList<UserInfo> allInfo = dboperate.getUserInfo(userinfo);
			int number = allInfo.size();	
			if(number<=0)
				number = 1;
			model.put("user", userinfo);
			model.put("number", number);
			model.put("allInfo", allInfo);		
			ModelAndView mav = new ModelAndView("message");
			mav.addAllObjects(model);
			
			return mav;
		}
		else
		{
			ModelAndView mav = new ModelAndView("login");
			mav.addObject("login", "your name is null, please input name!");
			return mav;
		}		
	}	
}
