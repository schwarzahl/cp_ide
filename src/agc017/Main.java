package agc017;

import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveA();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int P = sc.nextInt();
		int zeroNum = 0;
		int oneNum = 0;
		long ans = 0L;
		for (int i = 0; i < N; i++) {
			if (sc.nextInt() % 2 == 0) {
				zeroNum++;
			} else {
				oneNum++;
			}
		}
		if (P == 0) {
			ans++;
		}
		for (int i = 1; i <= N; i++) {
			for (int z = 0; z <= zeroNum && z <= i; z++) {
				int o = i - z;
				if (o <= oneNum && o % 2 == P) {
					ans += comb(zeroNum, z) * comb(oneNum, o);
				}
			}
		}
		System.out.println(ans);
	}

	private long comb(long A, long B) {
		if (A < B) {
			return 0L;
		}
		if (B == 0) {
			return 1L;
		}
		if (A / 2 < B) {
			return comb(A, A - B);
		}
		long ret = 1L;
		for (long i = A; i > A - B; i--) {
			ret *= i;
		}
		for (long i = B; i > 0; i--) {
			ret /= i;
		}
		return ret;
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