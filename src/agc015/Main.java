package agc015;

import java.util.Scanner;
public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveA();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		long N = sc.nextLong();
		long A = sc.nextLong();
		long B = sc.nextLong();
		long min = A * (N - 1) + B;
		long max = B * (N - 1) + A;
		if (A > B || (N == 1 && A != B)) {
			System.out.println(0);
		} else {
			System.out.println(max - min + 1);
		}
	}
}