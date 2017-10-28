package abc076;

import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveC();
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

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		String S = sc.next();
		String T = sc.next();
		int SL = S.length();
		int TL = T.length();
		for (int s_ind = SL - TL; s_ind >= 0; s_ind--) {
			int t_ind = 0;
			for (; t_ind < TL; t_ind++) {
				if (S.charAt(s_ind + t_ind) != '?' && S.charAt(s_ind + t_ind) != T.charAt(t_ind)) {
					break;
				}
			}
			if (t_ind == TL) {
				for (int o_ind = 0; o_ind < SL; o_ind++) {
					if (s_ind <= o_ind && o_ind < s_ind + TL) {
						System.out.print(T.charAt(o_ind - s_ind));
					} else {
						if (S.charAt(o_ind) == '?') {
							System.out.print('a');
						} else {
							System.out.print(S.charAt(o_ind));
						}
					}
				}
				System.out.println();
				return;
			}
		}
		System.out.println("UNRESTORABLE");
	}
}