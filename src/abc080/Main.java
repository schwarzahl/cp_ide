package abc080;

import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveD();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int A = sc.nextInt();
		int B = sc.nextInt();
		System.out.println(N * A < B ? N * A : B);
	}

	private void solveB() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int tmp = N;
		int sum = 0;
		while (tmp > 0) {
			sum += tmp % 10;
			tmp /= 10;
		}
		if (N % sum == 0) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int C = sc.nextInt();
		int[][] M = new int[C + 1][];
		for (int j = 1; j <= C; j++) {
			M[j] = new int[100001];
		}
		for (int i = 1; i <= N; i++) {
			int s = sc.nextInt();
			int t = sc.nextInt();
			int c = sc.nextInt();
			for (int j = s; j <= t; j++) {
				M[c][j] = 1;
			}
		}
		int max = 0;
		for (int i = 1; i <= 100000; i++) {
			int sum = 0;
			for (int j = 1; j <= C; j++) {
				if (M[j][i] == 1) {
					sum++;
				}
			}
			if (max < sum) {
				max = sum;
			}
		}
		System.out.println(max);
	}
}