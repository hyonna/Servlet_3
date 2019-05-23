package com.iu.point;

public class PointDTO {
	
	// 데이터를 운반하는 클래스
	// 데이터를 담을 수 있는 변수 선언
	// 멤버 변수명 == 테이블 컬럼명
	// 파라미터 이름 == 테이블 컬럼명
	// 멤버변수의 접근지정자는 private = 해당 클래스 내에서만 사용 가능
	// getter, setter 생성
	
	private int num;
	private String name;
	private int kor;
	private int eng;
	private int math;
	private int total;
	private double avg;
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public int getKor() {
		return kor;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	
	
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	
	
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	
	
	
	
	
	

}
