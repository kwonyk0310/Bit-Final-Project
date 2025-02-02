package controller.mall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.Product;
import controller.common.SuperClass;
import dao.ProductDao;
import shopping.MyCartList;
import shopping.ShoppingInfo;

@Controller
public class MallListController extends SuperClass{
	private final String command = "/list.mall" ; 
	private ModelAndView mav = null ;
	public final static double DISCOUNT = 0.15 ;// 할인율 지정
	
	@Autowired
	@Qualifier("pdao")
	private ProductDao pdao ;
	
	public MallListController() {
		super("malllist", null);
		this.mav = new ModelAndView();
	}
	
	@GetMapping(command)
	public ModelAndView doGet(
			HttpSession session){	
//		if (session.getAttribute("loginfo") == null) {			
//			this.mav.setViewName("redirect:/login.me"); 
//		} else {
			MyCartList mycart = (MyCartList)session.getAttribute("mycart") ;
			if (mycart==null) {
				String errmsg = "쇼핑 내역이 없어서 상품 목록으로 이동합니다." ;
				//this.mav.addObject("errmsg", errmsg );
				this.mav.setViewName("redirect:/plist.pr");
			}else {
				Map<Integer, Integer> maplists =  mycart.GetAllOrderLists() ;
				
				// keylist : 구매하고자 하는 상품 번호를 저장하고 있는 Set 자료 구조
				Set<Integer> keylist = maplists.keySet() ;
				
				// ShoppingInfo : 상품 1건에 대한 정보를 저장하고 있는 Bean 객체
				// shoplists : ShoppingInfo 객체들을 저장하고 있는 컬렉션 객체
				List<ShoppingInfo> shoplists = new ArrayList<ShoppingInfo>();
				
				int totalAmount = 0 ; // 총 판매 금액
				int discountPrice = 0 ; // 할인 후 가격
				int disTotalPrice = 0 ; // 할인 후 총 금액
				
				for(Integer pno :  keylist){  // pno : 상품 번호
					Integer qty = maplists.get(pno) ;// 구매 수량
					
					// 상품 번호 pnum에 대한 Bean 정보				 				
					Product bean = pdao.SelectDataByPk(pno) ;
					
					ShoppingInfo shopinfo = new ShoppingInfo() ;
					
					//int discount = bean.getPoint() ;
					int price = bean.getPrice() ;
					
					totalAmount += qty * price ;
					discountPrice = (int)(price * (1-DISCOUNT));
					disTotalPrice += qty * discountPrice;
					
					shopinfo.setProductname(bean.getProductname());
					shopinfo.setQty(qty);
					shopinfo.setDiscount(this.DISCOUNT);
					shopinfo.setMid(session.getId());
					shopinfo.setPimg(bean.getPimg1());
					shopinfo.setPrice(price);
					shopinfo.setProductcode(pno);
					
					shoplists.add(shopinfo) ;
				}
				
				session.setAttribute("totalAmount", totalAmount) ;
				session.setAttribute("disTotalPrice", disTotalPrice) ;
				
				// 이번에 구매할 총 목록
				session.setAttribute("shoplists", shoplists) ;
				
				this.mav.setViewName(super.getpage);
			//}
		}	
		return this.mav ;
	}
}