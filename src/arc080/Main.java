package arc080;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.solveE();
	}

	private void solveE() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();

		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			list.add(sc.nextInt());
		}
		int[] answer = new int[N];
		int cp = N - 2;
		int size = N;
		while (!list.isEmpty()) {
			int max = 0;
			int secmax = 0;
			int maxi = 0;
			for (int i = 0; i < size - 1; i++) {
				int tmp = list.get(i);
				int tmp2 = list.get(i + 1);
				int tmpb = tmp < tmp2 ? tmp : tmp2;
				int tmps = tmp < tmp2 ? tmp2 : tmp;
				if (max < tmpb) {
					max = tmpb;
					secmax = tmps;
					maxi = i;
				} else if (max == tmpb && secmax < tmps) {
					secmax = tmps;
					maxi = i;
				}
			}
			answer[cp] = list.get(maxi);
			answer[cp + 1] = list.get(maxi + 1);
			size -= 2;
			cp -= 2;
			list.remove(maxi + 1);
			list.remove(maxi);
		}
		for (int i = 0; i < N - 1; i++) {
			System.out.print(answer[i] + " ");
		}
		System.out.println(answer[N - 1]);
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
		if (two > 1) {
			num += two - 1;
		}
		num += four * 2;
		if (num < N - 1) {
			System.out.println("No");
		} else {
			System.out.println("Yes");
		}
	}
}