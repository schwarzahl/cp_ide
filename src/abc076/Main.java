package abc076;

import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveB();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		int R = sc.nextInt();
		int G = sc.nextInt();
		System.out.println(2 * G - R);
	}

	private void solveB() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();
		int output = 1;
		for (int i = 0; i < N; i++) {
			if (output < K) {
				output *= 2;
			} else {
				output += K;
			}
		}
		System.out.println(output);
	}
}