package agc012;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class Main {
	static final long MOD = 1000000007L;

	public static void main(String[] args){
		Main main = new Main();
		main.solveA();
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

	private void solveB() {
		Scanner sc = new Scanner(System.in);
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);
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
		if (r < 1 || c < 1 || r > h || c > w) return 1;
		return combination(r + c - 2L, c - 1L) * combination(h - r + w - c, w - c);
	}

	/**
	 * n個からm個選ぶ組み合わせの数を求めます
	 * @param n 全体の数
	 * @param m 選ぶ数
	 * @return C(n, m)
	 */
	private long combination(long n, long m) {
		if (n / 2L < m) {
			return combination(n , n - m);
		}
		if (n <= 0 || m <= 0) {
			return 1L;
		}
		return (combination(n, m - 1) * (n - m + 1) / m) % MOD;
	}
}