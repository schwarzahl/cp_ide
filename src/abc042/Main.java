package abc042;

import java.util.Scanner;
public class Main {
	static final long MOD = 1000000007L;

	public static void main(String[] args){
		Main main = new Main();
		main.solveD();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		int five = 0;
		int seven = 0;
		for (int i = 0; i < 3; i++) {
			switch (sc.nextInt()) {
			case 5:
				five++;
				break;
			case 7:
				seven++;
				break;
			default:
			}
		}
		if (five == 2 && seven == 1) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);
		long H = sc.nextLong();
		long W = sc.nextLong();
		long A = sc.nextLong();
		long B = sc.nextLong();

		long startRowIndex = H - A;
		long startColIndex = B + 1;
		long answer = 0L;
		for (long shiftIndex = 0; startRowIndex - shiftIndex > 0 && startColIndex + shiftIndex <= W; shiftIndex++) {
			answer += pointComb(H, W, H - A - shiftIndex, B + 1 + shiftIndex);
			answer %= MOD;
		}
		System.out.println(answer);
	}

	/**
	 * h行w列の格子について点(r, c)を通る組み合わせを求めます
	 * @param h 格子の行数
	 * @param w 格子の列数
	 * @param r 通る点の行index
	 * @param c 通る点の列index
	 * @return C(r + c - 2, c - 1) * C(h - r + w - c, w - c)
	 */
	private long pointComb(long h, long w, long r, long c) {
		return combination(r + c - 2, c - 1) * combination(h - r + w - c, w - c);
	}

	/**
	 * n個からm個選ぶ組み合わせの数を求めます
	 * @param n 全体の数
	 * @param m 選ぶ数
	 * @return C(n, m)
	 */
	private long combination(long n, long m) {
		if (n / 2 < m) {
			return combination(n , n - m);
		}
		if (m <= 0) {
			return 1;
		}
		return (combination(n, m - 1) * (n - m + 1) / m) % MOD;
	}
}