package shopping;

import java.util.HashMap;
import java.util.Map;

//나의 카트를 관리해주는 클래스(카트 관리자)
public class MyCartList {
	//orderlist: 카트에 담은 정보를 저장하고 있는 맵 컬랙션 
	//key는 상품 번호(기본 키), value는 구매 수량 
	public static int PCNT = 0;
	
	private Map<Integer, Integer> orderlists = null;
	
	public MyCartList() {
		this.orderlists= new HashMap<Integer, Integer>();
	}
	
	// 장바구니 내역을 모두 삭제합니다.
		// 주로 결재가 이루어질 때 사용이 됩니다.
		public void RemoveAllProductInfo() {
			this.orderlists.clear();
		}
		
		// 장바구니 내역 정보를 반환해줍니다.
		public Map<Integer, Integer> GetAllOrderLists(){
			return this.orderlists ;
		}
		
		// 장바구니 내역 정보를 수정합니다.
		public void EditOrder(int pno, int qty) {
			// pnum는 수정될 상품 번호, stock은 수정할 수량
			this.orderlists.put(pno, qty);
		}
		
		// 장바구니에 들어 있는 해당 상품을 삭제합니다.
		public void DeleteOrder(int pnum) {
			// pnum는 삭제될 상품 번호
			this.orderlists.remove(pnum) ;
			MyCartList.PCNT = orderlists.size() ;
		}


		public void AddOrder(int pno, int qty) {
			if (this.orderlists.containsKey(pno)) { // 동일 상품이 이미 있는 경우
				int newstock = this.orderlists.get(pno) + qty ; 
				this.orderlists.put(pno, newstock);
			} else {
				this.orderlists.put(pno, qty) ;
			}
			MyCartList.PCNT = orderlists.size() ;
		}
	


}
