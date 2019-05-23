package com.iu.page;

public class SearchMakePage {
	
	//페이징 계산을 담당하는 클래스
	
	private int perPage;
	private int curPage;
	private Search search; //kind, search
	
	
	
	public SearchMakePage(int curPage, String kind, String search) { //파라미터로 3개 넘어오는 것들
		
//		this.perPage = 10;
//		this.curPage = curPage;
//		
//		this.search = new Search();
//		this.search.setKind(kind);
//		this.search.setSearch(search);
		
		this(10, curPage, kind, search);
		
		
	}
	
	public SearchMakePage(int perPage, int curPage, String kind, String search) { 
		
		this.perPage = perPage;
		this.curPage = curPage;
		
		this.search = new Search();
		this.search.setKind(kind);
		this.search.setSearch(search);
	}

	//row를 계산하는(startRow, lastRow)
	public SearchRow makeRow() {
		
		int startRow = (curPage-1)*perPage+1;
		int lastRow = curPage * perPage; //searchRow에 집어넣을것들
		
		SearchRow searchRow = new SearchRow();
		searchRow.setStartRow(startRow); //계산한 startRow
		searchRow.setLastRow(lastRow); //계산한 lastRow
		searchRow.setSearch(search); //위에서 계산한 search
		
		return searchRow; //DAO에 보낼 리턴값
		
	}
	
	//page를 계산하는
	public SearchPager makePage(int totalCount) { //페이징 처리하는 순서대로
		
		//DAO계산은 Service에서 계산하게끔
		
		//2.totalPage
		int totalPage = totalCount / perPage;
		
		if(totalCount % perPage != 0) {
			
			totalPage++;
		}
		
		//3.totalBloack (block당 숫자의 갯수)

		int perBlock = 5;
		int totalBlock = totalPage / perBlock;
		
		if(totalPage%perBlock != 0) {
			
			totalBlock++;
		}
		
		//4.curBlock (현재 Block)
		int curBlock = curPage / perBlock;
		
		if(curPage % perBlock != 0) {
			
			curBlock++;
		}
		
		
		//5. curBlock에서 startNum , lastNum
		
		int startNum = (curBlock-1)*perBlock+1;
		int lastNum = curBlock*perBlock;
		
		
		//6. curBlock이 마지막 block일때 lastNum 다시 대입 = totalPage

		if(curBlock == totalBlock) {
			
			lastNum = totalPage;
		}
		
		
		SearchPager searchPager = new SearchPager();
		searchPager.setCurPage(curPage);
		searchPager.setCurBlock(curBlock);
		searchPager.setLastNum(lastNum);
		searchPager.setSearch(search);
		searchPager.setStartNum(startNum);
		searchPager.setTotalBlock(totalBlock);
		
		return searchPager;//JSP에 보내줄거
	}

}
