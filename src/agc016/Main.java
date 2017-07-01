package agc016;

import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveB();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		String s = sc.next();

		int maxMin = s.length();
		for (char c = 'a'; c <= 'z'; c++) {
			int max = 0;
			int current = 0;
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == c) {
					if (max < current) {
						max = current;
					}
					current = 0;
				} else {
					current++;
				}
			}
			if (max < current) {
				max = current;
			}
			if (maxMin > max) {
				maxMin = max;
			}
		}
		System.out.println(maxMin);
	}

	private void solveB() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int minK = N;
		int minKnum = 0;
		int maxK = 0;
		int maxKnum = 0;
		for (int i = 0; i < N; i++) {
			int num = sc.nextInt();
			if (minK > num) {
				minK = num;
				minKnum = 0;
			}
			if (maxK < num) {
				maxK = num;
				maxKnum = 0;
			}
			if (minK == num) {
				minKnum++;
			}
			if (maxK == num) {
				maxKnum++;
			}
		}
		if (judge(minK, minKnum, maxK, maxKnum)) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
	}

	private boolean judge(int minK, int minKnum, int maxK, int maxKnum) {
		if (minK + 1 < maxK) {
			return false;
		}
		if (minK == maxK) {
			if (minK + 1 == minKnum) {
				return true;
			}
			return minK <= minKnum / 2;
		}
		if (maxKnum < 2) {
			return false;
		}
		return (minKnum < maxK) && (maxK <= minKnum + maxKnum / 2);
	}
}