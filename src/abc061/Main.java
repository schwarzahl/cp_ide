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
		calcPointSet.add(N);
		pointMap.put(N, 0L);
		int calcNum = 0;
		while (!calcPointSet.isEmpty()) {
			Set<Integer> nextCalcPointSet = new HashSet<>();
			for (Integer point : calcPointSet) {
				for (int i = 1; i <= N; i++) {
					if (bmap[i][point]) {
						long now = pointMap.get(point);
						long path = map[i][point];
						if (!pointMap.containsKey(i)) {
							pointMap.put(i, now + path);
							nextCalcPointSet.add(i);
						} else {
							long target = pointMap.get(i);
							if (target < now + path) {
								pointMap.put(i, now + path);
								nextCalcPointSet.add(i);
							}
						}
					}
				}
			}
			calcPointSet = nextCalcPointSet;
			if (++calcNum > (N + 1)) {
				System.out.println("inf");
				return;
			}
		}
		System.out.println(pointMap.get(1));
	}
}