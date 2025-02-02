package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bean.Product;
import bean.Productreviews;

@Component("pdao")
public class ProductDao {
	private final String namespace = "MapperProduct." ;	
	
	@Autowired
	SqlSessionTemplate abcd;
	
	public ProductDao() { }
	
	
	public List<Product> SelectDataList1(String mode1) { // 카테고리
		//System.out.println("mode1 : " + mode1);
		return this.abcd.selectList(namespace + "SelectDataList1", mode1);
	}
	
	
	public int InsertData(Product bean) {	
		System.out.println(this.getClass() + " : 상품을 등록합니다." ); 
		return this.abcd.insert(namespace + "InsertData", bean);
	}	
	
	public int SelectTotalCount(String mode, String keyword) {
		// 해당 검색 모드(상품명, 제조 회사, 카테고리)에 충족하는 항목들의 갯수를 구해줍니다.
		Map<String, String> map = new HashMap<String, String>() ;
		map.put("mode", mode) ;
		map.put("keyword", "%" + keyword + "%") ;
		return this.abcd.selectOne(namespace + "SelectTotalCount", map);
	}
	
	public List<Product> SelectDataList(int offset, int limit, String mode, String keyword) {
		// 페이징 처리와 필드 검색을 통한 상품 목록을 구해줍니다.
		RowBounds rowBounds = new RowBounds(offset, limit);
		Map<String, String> map = new HashMap<String, String>() ;
		map.put("mode", mode) ; 
		map.put("keyword", "%" + keyword + "%") ;	
		
		return this.abcd.selectList(namespace + "SelectDataList", map, rowBounds);
	}	
//	public List<Product> CategoryDataList(String string) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	

	//int = num 을 int = productcode 로 바꿔줌 
	public Product SelectDataByPk(int pno) {
		//System.out.println("번호확인 : " + pno);
		return this.abcd.selectOne(namespace + "SelectDataByPk", pno);
	}
	
	public int UpdateData(Product bean) {
		System.out.println(bean.toString());
		return this.abcd.update(namespace + "UpdateData", bean);
	}	

	public int DeleteData(int num, String remark) {
		// 해당 상품 번호에 대한 orderdetails.remark 컬럼을 수정합니다.		
		Map<String, Object> map = new HashMap<String, Object>() ;
		map.put("num", num);
		map.put("num", remark);
		this.abcd.update(namespace + "UpdateRemark", map);
		
			
		// 해당 상품을 삭제합니다.		
		return this.abcd.delete(namespace + "DeleteData", num);
	}
	public List<Productreviews> SelectDataList(int pno) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pno", pno);
		return this.abcd.selectList(namespace+"Selectprrlist",map);
	}

	


}