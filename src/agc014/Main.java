package agc014;

import java.util.Scanner;
public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveB();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		long a = sc.nextLong();
		long b = sc.nextLong();
		long c = sc.nextLong();
		int n = 0;
		while (true) {
			if (a % 2 == 1 || b % 2 == 1 || c % 2 == 1) {
				System.out.println(n);
				return;
			}
			if (a == b && b == c) {
				System.out.println("-1");
				return;
				
			}
			n++;
			long nextA = (b + c) / 2;
			long nextB = (a + c) / 2;
			long nextC = (a + b) / 2;
			a = nextA;
			b = nextB;
			c = nextC;
		}
	}

	private void solveB() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int[] count = new int[N];
		for (int i = 0; i < M; i++) {
			count[sc.nextInt() - 1]++;
			count[sc.nextInt() - 1]++;
		}
		int oddNum = 0;
		for (int num : count) {
			if (num % 2 == 1) {
				oddNum++;
			}
		}
		if (oddNum > 1) {
			System.out.println("NO");
		} else {
			System.out.println("YES");
		}
	}
}