package arc080;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.solveC();
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();

		int four = 0;
		int two = 0;
		for (int i = 0; i < N; i++) {
			int tmp = sc.nextInt();
			if (tmp % 4 == 0) {
				four++;
			} else if (tmp % 2 == 0) {
				two++;
			}
		}
		int num = 0;
		if (four > 0) {
			num += four * 2 + 1;
		}
		if (two > 1) {
			num += two - 1;
		}
		if (num < N) {
			System.out.println("No");
		} else {
			System.out.println("Yes");
		}
	}
}