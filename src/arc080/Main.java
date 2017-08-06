package arc080;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.solveD();
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);
		int H = sc.nextInt();
		int W = sc.nextInt();
		int N = sc.nextInt();

		int[][] map = new int[H][];
		for (int h = 0; h < H; h++) {
			map[h] = new int[W];
		}
		int vx = 1;
		int ch = 0;
		int cw = 0;
		for (int c = 1; c <= N; c++) {
			int a = sc.nextInt();
			for (int j = 0; j < a; j++) {
				map[ch][cw] = c;
				cw += vx;
				if (cw == W) {
					ch++;
					cw = W - 1;
					vx = -1;
				}
				if (cw == -1) {
					ch++;
					cw = 0;
					vx = 1;
				}
			}
		}
		for (int h = 0; h < H; h++) {
			System.out.print(map[h][0]);
			for (int w = 1; w < W; w++) {
				System.out.print(" " + map[h][w]);
			}
			System.out.println();
		}
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