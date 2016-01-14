package web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
@author*
@paramater*
@return
*/
@Controller
public class LoginController {
	@RequestMapping(value="hello.do")
	public void index_jsp(ModelMap model)
	{
		System.out.println("Hello!");
		model.addAttribute("yang", "yang Hello!!");
		System.out.println("hello.jsp");
	}
	
	
	
	

	
}
