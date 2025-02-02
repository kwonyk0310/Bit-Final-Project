package controller.mypayments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import bean.Member;
import bean.Product;
import controller.common.SuperClass;
import dao.MallDao;
import dao.ProductDao;
import shopping.MyCartList;
import shopping.ShoppingInfo;

@Controller
public class MypaymentsController extends SuperClass {
	private final String command = "/payment.pm";
	private String redirect = "mypayments";
	private ModelAndView mav = null;

	@Autowired

	@Qualifier("malldao")
	private MallDao malldao ;//

	public MypaymentsController() {

		super("mypayments", null);
		this.mav = new ModelAndView();

	}

	@GetMapping(command)
	public ModelAndView doGet(
			HttpSession session){	
		System.out.println("장바구니 내역을 이용하여 계산을 합니다.");		
		MyCartList mycart = (MyCartList)session.getAttribute("mycart");
		
		if (mycart != null) {
			System.out.println("maplists : 내가 구매한 상품들의 번호와 수량에 대한 컬렉션");
			Map<Integer, Integer> maplists = mycart.GetAllOrderLists();

		//	int totalPoint = (Integer)session.getAttribute( "totalPoint" ) ; 
			
			// mem : 계산을 수행하는 당사자
			Member mem = (Member)session.getAttribute("loginfo");
			
			System.out.println("dao.Calculate 메소드를 호출합니다.");
			// Calculate() 메소드는 계산 로직을 수행해주는 메소드입니다.
		//	malldao.Calculate(mem, maplists, totalPoint);
					
			// 5. 세션 영역의 모든 정보를 지우도록 한다.
			session.removeAttribute("shoplists"); //쇼핑 정보 삭제
			session.removeAttribute("totalAmount");//금액 정보 삭제
			session.removeAttribute("totalPoint");//금액 정보 삭제
			session.removeAttribute("mycart"); //카트 반납하기		
			session.setAttribute("message", "결재를 완료했습니다.\n감사합니다.");
			this.mav.setViewName(this.redirect);
		}
		return this.mav ;
	}
}