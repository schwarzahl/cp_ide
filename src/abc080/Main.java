package abc080;

import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveA();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		System.out.println(N * 2 + 5);
	}
}