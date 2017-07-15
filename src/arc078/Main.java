package arc078;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveD();
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);

		int N = sc.nextInt();
		boolean[][] matrix = new boolean[N + 1][];
		for (int i = 0; i <= N; i++) {
			matrix[i] = new boolean[N + 1];
		}
		for (int i = 0; i < N - 1; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			matrix[a][b] = true;
			matrix[b][a] = true;
		}
		int[] parentMap = new int[N + 1];
		int[] childNum = new int[N + 1];
		for (int i = 1; i<= N; i++) {
			parentMap[i] = 0;
			childNum[i] = 0;
		}
		List<Integer> list = new ArrayList<>();
		list.add(1);
		int depth = 1;
		int sDepth = 0;
		while (!list.isEmpty()) {
			List<Integer> nextList = new ArrayList<>();
			for (int parent : list) {
				for (int i = 1; i <= N; i++) {
					if (matrix[parent][i] && parentMap[parent] != i ) {
						parentMap[i] = parent;
						int node = parent;
						while(node != 0) {
							childNum[node]++;
							node = parentMap[node];
						}
						nextList.add(i);
						if (i == N) {
							sDepth = depth;
						}
					}
				}
			}
			list = nextList;
			depth++;
		}
		int jNode = N;
		for (int i = 0; i < (sDepth - 1) / 2; i++) {
			jNode = parentMap[jNode];
		}
		int fNum = childNum[1] - childNum[jNode] - 1;
		int sNum = childNum[jNode];
		if (fNum > sNum) {
			System.out.println("Fennec");
		} else {
			System.out.println("Snuke");
		}
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);

		int N = sc.nextInt();
		long[] a = new long[N];
		BigInteger sum = BigInteger.ZERO;
		for (int i = 0; i < N; i++) {
			a[i] = sc.nextLong();
			sum = sum.add(BigInteger.valueOf(a[i]));
		}
		sum = sum.subtract(BigInteger.valueOf(a[0]));
		BigInteger sum2 = BigInteger.valueOf(a[0]);
		BigInteger min = sum.subtract(sum2).abs();
		for (int i = 1; i < N - 1; i++) {
			BigInteger val = BigInteger.valueOf(a[i]);
			sum = sum.subtract(val);
			sum2 = sum2.add(val);
			BigInteger abs = sum.subtract(sum2).abs();
			if (abs.compareTo(min) < 0) {
				min = abs;
			}
		}
		System.out.println(min);
	}
}