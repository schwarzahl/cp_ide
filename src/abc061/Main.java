package abc061;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveD();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		int b = sc.nextInt();
		int c = sc.nextInt();
		if (a <= c && c <= b) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
	}

	private void solveB() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();

		int[] num = new int[N + 1];
		for (int i = 0; i < M; i++) {
			num[sc.nextInt()]++;
			num[sc.nextInt()]++;
		}
		for (int j = 1; j <= N; j++) {
			System.out.println(num[j]);
		}
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		long K = sc.nextLong();

		Map<Integer, Long> map = new HashMap<>();

		for (int i = 0; i < N; i++) {
			int key = sc.nextInt();
			long value = sc.nextLong();
			if (map.containsKey(key)) {
				value += map.get(key);
			}
			map.put(key, value);
		}
		long index = 0;
		for (Integer key : map.keySet().stream().sorted().toArray(Integer[]::new)) {
			index += map.get(key);
			if (K <= index) {
				System.out.println(key);
				break;
			}
		}
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();

		long[][] map = new long[N + 1][];
		boolean[][] bmap = new boolean[N + 1][];
		for (int j = 1; j <= N; j++) {
			map[j] = new long[N + 1];
			bmap[j] = new boolean[N + 1];
		}
		for (int i = 0; i < M; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			long c = sc.nextLong();
			map[a][b] = c;
			bmap[a][b] = true;
		}

		Map<Integer, Long> pointMap = new HashMap<>();
		Set<Integer> calcPointSet = new HashSet<>();
		calcPointSet.add(1);
		pointMap.put(1, 0L);
		for (int calcNum = 0; calcNum < N; calcNum++) {
			for (int src = 1; src <= N; src++) {
				for (int dest = 1; dest <= N; dest++) {
					if (bmap[src][dest] && pointMap.containsKey(src)) {
						long destValue = Long.MIN_VALUE / 3;
						if (pointMap.containsKey(dest)) {
							destValue = pointMap.get(dest);
						}
						long nextValue = pointMap.get(src) + map[src][dest];
						if (destValue < nextValue) {
							pointMap.put(dest, nextValue);
							if (calcNum == N - 1) {
								System.out.println("inf");
								return;
							}
						}
					}
				}
			}
		}
		System.out.println(pointMap.get(N));
	}
}