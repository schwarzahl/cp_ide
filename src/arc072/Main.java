package arc072;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveC();
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			list.add(sc.nextInt());
		}
		int start = list.get(0);
		int origin = innerSolveC(list);
		list.set(0, 1);
		int plus = innerSolveC(list) + Math.abs(start - 1);
		list.set(0, -1);
		int minus = innerSolveC(list) + Math.abs(start + 1);
		if (origin < plus && start != 0) {
			if (origin < minus) {
				System.out.println(origin);
			} else {
				if (plus < minus) {
					System.out.println(plus);
				} else {
					System.out.println(minus);
				}
			}
		} else {
			if (plus < minus) {
				System.out.println(plus);
			} else {
				System.out.println(minus);
			}
		}
	}

	private int innerSolveC(List<Integer> list) {
		int sum = 0;
		int ans = 0;
		for (int tmp : list) {
			if (sum == 0) {
				sum = tmp;
			} else if (sum < 0) {
				if (sum + tmp > 0) {
					sum += tmp;
				} else {
					ans += 1 - (sum + tmp);
					sum = 1;
				}
			} else {
				if (sum + tmp < 0) {
					sum += tmp;
				} else {
					ans += (sum + tmp) + 1;
					sum = -1;
				}
			}
		}
		return ans;
	}
}