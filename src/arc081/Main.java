package arc081;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.solveC();
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();

		int[] a = new int[100000001];
		for (int i = 0; i < N; i++) {
			a[sc.nextInt()]++;
		}
		long max = 0;
		long secMax = 0;
		for (int i = 100000000; i >= 0 && secMax == 0; i--) {
			if (max == 0) {
				if (a[i] > 3) {
					max = i;
					secMax = i;
				} else if (a[i] > 1) {
					max = i;
				}
			} else {
				if (a[i] > 1) {
					secMax = i;
				}
			}
		}
		System.out.println(max * secMax);
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();

		System.out.println(N);
	}

	private void solveE() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();

		System.out.println(N);
	}
}