package web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
@author*
@paramater*
@return
*/
@Controller
public class loginController {
	@RequestMapping(value="/login.do")
	public ModelAndView login_jsp()
	{
		return (new ModelAndView("login"));
	}

}
