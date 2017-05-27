package agc015;

import java.util.Scanner;
public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveB();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		long N = sc.nextLong();
		long A = sc.nextLong();
		long B = sc.nextLong();
		long min = A * (N - 1) + B;
		long max = B * (N - 1) + A;
		if (A > B || (N == 1 && A != B)) {
			System.out.println(0);
		} else {
			System.out.println(max - min + 1);
		}
	}

	private void solveB() {
		Scanner sc = new Scanner(System.in);
		String S = sc.nextLine();
		int N = S.length();
		long sum = 0L;
		for (int i = 0; i < N; i++) {
			if (S.charAt(i) == 'U') {
				sum += 2 * i + 1 * (N - 1 - i);
			} else {
				sum += 1 * i + 2 * (N - 1 - i);
			}
		}
		System.out.println(sum);
	}
}