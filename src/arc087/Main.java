package arc087;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.solveC();
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < N; i++) {
			int tmp = sc.nextInt();
			if (!map.containsKey(tmp)) {
				map.put(tmp, 0);
			}
			map.put(tmp, map.get(tmp)+1);
		}
		long sum = 0L;
		for (Map.Entry<Integer, Integer> e : map.entrySet()) {
			int key = e.getKey();
			int value = e.getValue();
			if (key <= value) {
				sum += value - key;
			} else {
				sum += value;
			}
		}
		System.out.println(sum);
	}
}