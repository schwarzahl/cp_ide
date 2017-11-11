package arc085;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.solveD();
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int time = N * 100 + M * 1800;
		int pi = 1;
		for (int i = 0; i < M; i++) {
			pi *= 2;
		}
		double prev = -1.0;
		double current = 0.0;
		int k = 1;
		while (Math.ceil(prev) != Math.ceil(current)) {
			prev = current;
			double tmp = 1.0 * k * time / pi;
			for (int d = 1; d < k; d++) {
				tmp *= (1.0 - 1.0 / pi);
			}
			current += tmp;
			k++;
		}
		System.out.println(100*Math.round(Math.ceil(current/100)));
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int Z = sc.nextInt();
		int W = sc.nextInt();
		int[] a = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = sc.nextInt();
		}
		int ans1 = Math.abs(a[N-1] - W);
		int ans2 = ans1;
		if (N > 1) {
			ans2 = Math.abs(a[N - 1] - a[N - 2]);
		}
		System.out.println(ans1 > ans2 ? ans1 : ans2);
	}
}