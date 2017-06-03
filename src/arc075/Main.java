package arc075;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveD();
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int sum = 0;
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < N; i++) {
			int val = sc.nextInt();
			sum += val;
			list.add(val);
		}
		if (sum % 10 != 0) {
			System.out.println(sum);
		} else {
			Collections.sort(list);
			for (int val : list) {
				if (val % 10 != 0) {
					System.out.println(sum - val);
					return;
				}
			}
			System.out.println(0);
		}
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		long A = sc.nextLong();
		long B = sc.nextLong();
		List<Integer> list = new ArrayList<Integer>();
		int max = 0;
		for (int i = 0; i < N; i++) {
			int val = sc.nextInt();
			list.add(val);
			if (max < val) {
				max = val;
			}
		}
		long minNum = (max + A - 1) / A;
		long maxNum = (max + B - 1) / B;
		do {
			long count = (minNum + maxNum) / 2;
			if (isWin(count, A, B, list)) {
				maxNum = count;
			} else {
				minNum = count + 1;
			}
		} while (minNum < maxNum);
		System.out.println(minNum);
	}

	private boolean isWin(long count, long A, long B, List<Integer> hps) {
		long sum = 0;
		for (int hp : hps) {
			sum += (hp - count * B + A - B - 1) / (A - B);
		}
		return sum <= count;
	}

	private void solveE() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		long K = sc.nextLong();
		long[] sumArr = new long[N + 1];
		sumArr[0] = 0L;
		for (int i = 1; i <= N; i++) {
			sumArr[i] = sumArr[i - 1] + sc.nextLong();
		}
		long ans = 0L;
		for (int start = 0; start < N; start++) {
			for (int end = start + 1; end <= N; end++) {
				if ((sumArr[end] - sumArr[start]) / (end - start) >= K) {
					ans++;
				}
			}
		}
		System.out.println(ans);
	}
}