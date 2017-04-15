package agc013;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveC();
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int L = sc.nextInt();
		int T = sc.nextInt();
		int[] array = new int[N];
		Set<Integer> set = new HashSet<Integer>();
		int prev_d = 1;
		for (int i = 0; i < N; i++) {
			int x = sc.nextInt();
			int d = sc.nextInt();
			array[i] = x + T * (3 - d * 2);
			if (prev_d != d) {
				set.add((i - 1 + N) % N);
			}
		}
		while (!set.isEmpty()) {
			Set<Integer> nextSet = new HashSet<Integer>();
			for (int i : set) {
				if (checkAndSwap(array, i, (i + 1) % N, N, L)) {
					nextSet.add((i - 1 + N) % N);
					nextSet.add((i + 1) % N);
				}
			}
			set = nextSet;
		}
		for (int x : array) {
			System.out.println((x + L) % L);
		}
	}

	private boolean checkAndSwap(int[] array, int a, int b, int N, int L) {
		int offset = 0;
		if (a == N - 1 && b == 0) {
			offset = L;
		}
		if (array[a] - offset > array[b]) {
			//System.err.println("swap " + a + " and " + b + " a.x = " + array[a] + " b.x = " + array[b]);
			int swap = array[b];
			array[b] = array[a] - offset;
			array[a] = swap + offset;
			return true;
		}
		return false;
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		if (N > 1) {
			int num = 1;
			int prev = sc.nextInt();
			Boolean isInc = null;
			for (int i = 1; i < N; i++) {
				int tmp = sc.nextInt();
				if (prev < tmp) {
					if (isInc == null) {
						isInc = true;
					} else if (!isInc) {
						num++;
						isInc = null;
					}
				}
				if (prev > tmp) {
					if (isInc == null) {
						isInc = false;
					} else if (isInc) {
						num++;
						isInc = null;
					}
				}
				prev = tmp;
			}
			System.out.println(num);
		} else {
			System.out.println(1);
		}
	}
}