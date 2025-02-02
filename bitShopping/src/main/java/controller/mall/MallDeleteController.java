package controller.mall;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controller.common.SuperClass;
import dao.CompositeDao;
import shopping.MyCartList;

@Controller
public class MallDeleteController extends SuperClass{
	private final String command = "/delete.mall" ; 
	private ModelAndView mav = null ;
	//private String redirect = "redirect:/delete.mall" ;
	
	@Autowired
	@Qualifier("cdao")
	private CompositeDao dao ;
	
	public MallDeleteController() {
		super(null, null);
		this.mav = new ModelAndView();
	}
	
	@GetMapping(command)
	public ModelAndView doGet(
			@RequestParam(value = "pno", required = true) int pno,
			HttpSession session){
		int pcnt = 0 ; // 장바구니에 담긴 상품 수량 체크
		
			MyCartList mycart = (MyCartList)session.getAttribute("mycart") ;
			
			mycart.DeleteOrder(pno); 
			
			pcnt = MyCartList.PCNT;
			session.setAttribute("pcnt", pcnt);
			
			session.setAttribute("mycart", mycart); 
			this.mav.setViewName("redirect:/list.mall");
		return this.mav ;
	}
	
	
	
	
	
	
}