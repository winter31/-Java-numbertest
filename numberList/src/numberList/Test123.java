package numberList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Test123 {

		static ArrayList<int[]> numberList = new ArrayList<>();
		static ArrayList<Integer> choosedList = new ArrayList<>();
		private Connection conn = null;
		private static int[] arr; // 기준 배열

		public static void main(String[] args) {
			int cou = 1;
			Test123 sm = new Test123();
			sm.makingNum(1, 10);
			sm.perm(arr, 0, 10, 6);
	/*		sm.countNumbers(1, 2, "sum");
			sm.DBcount();*/
			for (int[] b : numberList) {
				System.out.println(b[0] + "," + b[1] + "," + b[2]+ ","+b[3]+","+b[4]+","+b[5] + "---" + cou);
				cou++;
			}
		}

		public void makingNum(int first, int finalNo) {
			int indexArray = 0;
			arr = new int[finalNo];
			for (int a = first; a <= finalNo; a++) {
				arr[indexArray++] = a;
			}
		}

		public void perm(int[] arr, int depth, int n, int k) {
			// n = 배열에 들어 잇는 고정값
			// k = 몇개를 뽑아낼지 정하는 값;
			if (depth == k) { 
				print(arr, k);
				return;
			}
			for (int i = depth; i < n; i++) {
				swap(arr, i, depth);
				perm(arr, depth + 1, n, k); 
				swap(arr, i, depth);
			}
		} 

		public void swap(int[] arr, int i, int j) {
			// i = 포문 순서 몇번째인가
			// j = depth 값
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}

		public void print(int[] arr, int k) {
			int[] arrayNu = new int[k];
			for (int i = 0; i < k; i++) {
				if (i == k - 1) {
					arrayNu[i] = arr[i];
				} else {
					arrayNu[i] = arr[i];
				}
			}
			numberList.add(arrayNu);
		}

		// 지정수
		public void countNumbers(int selected, int sum, String dbCheck) {
			ArrayList<Integer> resultList = new ArrayList<>();
			int sumNumber = selected; // 지정수
			// sum = 더할 수
			//계산식
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
				
				for (int[] a : numberList) {
					for (int b = 0; b < a.length; b++) {
						for (int c = 0; c < resultList.size(); c++) {
							if (a[b] == resultList.get(c)) {
								a[b] = 0;
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("계산 할 수 없는 숫자입니다.");
			} 
		}

		// DB에 있는 숫자 제외
		public void DBcount() {
			conn = DBConnector.DBConnector1();
			ArrayList<Integer> resultList = new ArrayList<>();
			String sql = "select * from numbertest";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					resultList.add(rs.getInt(2));
					resultList.add(rs.getInt(3));
					resultList.add(rs.getInt(4));
				}
				for (int[] a : numberList) {
					for (int b = 0; b < a.length; b++) {
						for (int c : resultList) {
							if (a[b] == c) {
								a[b] = 0;
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBConnector.close();
			}
		}
	}
