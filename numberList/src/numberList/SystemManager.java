package numberList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Stack;

public class SystemManager {
	static ArrayList<int[]> numberList = new ArrayList<>();
	private Connection conn = null;
	private static int[] arr; // 기준 배열
	public Stack<Integer> st = new Stack<Integer>();
	public int lengthArr = 0; // 몇개를 뽑을지

	//생성자
	public SystemManager(int lengthArr ) { 
		this.lengthArr=lengthArr; 
	}
	 
// TESTER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//	public static void main(String[] args) {
//		SystemManager sm = new SystemManager();
//		sm.makingNum(1, 10); // 범위를 어디서부터 할 것인지 정한다.
//		sm.pick(arr.length, st, 6); // 조합한다.
//		sm.countNumbers(2, 2, "sum"); // 수열에 관한 수 빼기
//		sm.DBcount(); // 데이타 베이스나 더하거나 뺀 수열에서 나온 수를 제거한다.
//		sm.printOut(6); // lengthArr 을 변수로 받아 넣는다.
//	}

	// 숫자 배열에 저장하기
	public void makingNum(int first, int finalNo) {
		int indexArray = 0;
		arr = new int[finalNo];
		for (int a = first; a <= finalNo; a++) {
			arr[indexArray++] = a;
		}
		pick(arr.length, st, lengthArr);
	}

	// 조합수열 만들기 (중복 제거 처리)
	public void pick(int n, Stack<Integer> st, int r) {
		// r == 개수
		// n == 배열 범위
		int[] list = new int[lengthArr];
		int listIndex = 0;
		if (r == 0) {
			for (int i : st) {
				list[listIndex++] = arr[i];
			}
			numberList.add(list);
		}
		int smallest = st.isEmpty() ? 0 : st.lastElement() + 1;
		for (int next = smallest; next < n; next++) {
			st.push(next);
			pick(n, st, r - 1);
			st.pop();
		}
	}

	// 지정수
	// 나중에 이터레이터 에러에 대해 고칠 수 있게 이해해야 할 듯. 지금은 어레이 리스트 하나를 더 만들어 불 필요한 방법을 한번 더 씀. 좋은 코드는 아님.
	public void countNumbers(int selected, int sum, String dbCheck) {
		ArrayList<Integer> resultList = new ArrayList<>();
		ArrayList<int[]> deleteList = new ArrayList<>();//이터레이터 에러때문에 하나 생성함.
		int sumNumber = selected; // 지정수
		int sumCount = 0; // 카운트
		// sum = 더할 수
		// 계산식
		try {
			for (int b = 0; b < 3; b++) {
				if ("sum".equals(dbCheck)) {
					sumNumber += sum;
					resultList.add(sumNumber);
				} else if ("notSum".equals(dbCheck)) {
					sumNumber -= sum;
					resultList.add(sumNumber);
				}
			}

			// 지우는 기능
			for (int[] a : numberList) {
				for (int b = 0; b < a.length; b++) {
					if (resultList.contains(a[b])) {
						sumCount += 1;
					}
				}
				if (sumCount == 3) {
					deleteList.add(a);
				}
				sumCount = 0;
			}
			for (int[] b : deleteList) {
				if (numberList.contains(b)) {
					numberList.remove(b);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("계산 할 수 없는 숫자입니다.");
		}
	}

	// DB에 있는 숫자 제외
	public void DBcount() {
		conn = DBConnector.DBConnector1(); //db연결 -- 디비 접속시 바꾸워줘야함.
		ArrayList<int[]> resultList = new ArrayList<>();
		String sql = "select * from numbertest"; // 쿼리문
		int colNo = 0; // 컬럼구할때 쓰는 카운터
		int sumCount = 0; // 2개의 2차원 배열을 구분해서 비교할 때 쓰는 카운터
		ArrayList<int[]> deleteList = new ArrayList<>(); // 지울때 사용하는 배열

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			// 자동 컬럼수 구하는 알고리즘 (해냈다!!!!!) 
			// select count(*) from user_table_columns where table_name ='numbertest';
			// 컬럼수 구하는 sql 쿼리지만... 먹히지가 않음.....
			// 쿼리 자동으로 만들어 주는 알고리즘을 API로 만들고, 그 후에 자동 컬럼 구해주는 알고리즘 추가해서 디비에 관한 API 도전!!
			if (rs.next()) {
				try {
					for (int a = 1; a < 100; a++) {
						if (rs.getInt(a) != 0) {
							colNo++;
						}
					}
				} catch (Exception e) {
					rs.close(); // 에러 터지는 즉시 컬럼 수 구하고 닫아버린다.
				}
			}
			rs = ps.executeQuery(); // 코드는 비 효율이지만 사용하기엔 효율이다.... rs를 초기화 시키고 커리 다시보낸다.
							
			// DB에 있는 데이타 담는 과정 
			while (rs.next()) {
				int[] DBresultArr = new int[colNo - 1];
				for (int dbindex = 0; dbindex < DBresultArr.length; dbindex++) {
					DBresultArr[dbindex] = rs.getInt(dbindex + 2);
				}
				resultList.add(DBresultArr);
			}

			// 지우는 기능 (4중포문.... 하 진짜 .... )
			for (int[] a : numberList) {
				for (int[] b : resultList) {
					for (int d = 0; d < b.length; d++) {
						for (int c = 0; c < a.length; c++) {
							if (a[c] == b[d]) {
								sumCount++;
							}
							if (sumCount == colNo - 1) {
								deleteList.add(a);
								sumCount = 0;
							}
						}
					}
					sumCount = 0;
				}
			}
			for (int[] b : deleteList) {
				if (numberList.contains(b)) {
					numberList.remove(b);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnector.close();
		}
	}

	// 프린트 하는 기능
	public void printOut(int lengthArr) {
		int cou = 1;
		for (int[] b : numberList) {
			for (int c = 0; c < lengthArr; c++) {
				System.out.print(b[c] + " ");
				if (c == lengthArr - 1) {
					System.out.println("----" + cou);
					cou++;
				}
			}
		}
	}
}
