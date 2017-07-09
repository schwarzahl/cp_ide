package agc017;

import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveB();
	}

	private void solveB() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		long A = sc.nextLong();
		long B = sc.nextLong();
		long C = sc.nextLong();
		long D = sc.nextLong();

		if (judge(N, A, B, C, D)) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}
	}

	private boolean judge(int N, long A, long B, long C, long D) {
		for (int minus = 0; minus < N; minus++) {
			long min = -D * minus + C * (N - 1 - minus);
			long max = -C * minus + D * (N - 1 - minus);
			if (min <= B - A && B - A <= max) {
				return true;
			}
		}
		return false;
	}
}