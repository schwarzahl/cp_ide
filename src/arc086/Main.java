package arc086;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.solveC();
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
}