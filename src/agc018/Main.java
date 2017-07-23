package agc018;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveA();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();
		Set<Integer> set = new HashSet<>();
		Set<Integer> modSet = new HashSet<>();
		for (int i = 0; i < N; i++) {
			int tmp = sc.nextInt();
			set.add(tmp);
			if (tmp >= K) {
				modSet.add(tmp % K);
			}
		}
		while () {

		}
		int[] A = new int[1000000001];
		System.out.println(N);
	}
}