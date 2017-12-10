package arc086;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.solveD();
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();
		int A[] = new int[200001];

		for (int i = 0; i < N; i++) {
			A[sc.nextInt()]++;
		}
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i <= 200000; i++) {
			if (A[i] > 0) {
				list.add(A[i]);
			}
		}
		Collections.sort(list);
		int erase = list.size() - K;
		if (erase > 0) {
			System.out.println(list.subList(0, erase).stream().mapToInt(a -> a).sum());
		} else {
			System.out.println(0);
		}
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int a[] = new int[N+1];
		int max = 0;
		int max_id = -1;
		int min = 0;
		int min_id = -1;
		for (int i = 1; i <= N; i++) {
			a[i] = sc.nextInt();
			if (max < a[i]) {
				max = a[i];
				max_id = i;
			}
			if (min > a[i]) {
				min = a[i];
				min_id = i;
			}
		}
		if (max >= -min && max_id != -1) {
			System.out.println(2 * N - 2);
			for (int i = 1; i <= N; i++) {
				if (i == max_id) {
					continue;
				}
				System.out.println(max_id + " " + i);
			}
			for (int i = 1; i <= N - 1; i++) {
				System.out.println(i + " " + (i+1));
			}
		} else if (max <= -min && min_id != -1) {
			System.out.println(2 * N - 2);
			for (int i = 1; i <= N; i++) {
				if (i == min_id) {
					continue;
				}
				System.out.println(min_id + " " + i);
			}
			for (int i = N; i > 1; i--) {
				System.out.println(i + " " + (i-1));
			}
		} else {
			System.out.println(0);
		}
	}
}