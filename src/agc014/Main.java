package agc014;

import java.util.Scanner;
public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveA();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		long a = sc.nextLong();
		long b = sc.nextLong();
		long c = sc.nextLong();
		int n = 0;
		if (a % 2 == 1 || b % 2 == 1 || c % 2 == 1) {
			System.out.println("1");
			return;
		}
		while (true) {
			if (a == b && b == c) {
				System.out.println("-1");
				return;
			}
			if (a % 2 == 1 || b % 2 == 1 || c % 2 == 1) {
				System.out.println(n);
				return;
			}
			n++;
			long nextA = (b + c) / 2;
			long nextB = (a + c) / 2;
			long nextC = (a + b) / 2;
			a = nextA;
			b = nextB;
			c = nextC;
		}
	}
}