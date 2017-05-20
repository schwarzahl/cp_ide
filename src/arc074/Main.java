package arc074;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveD();
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		long H = sc.nextLong();
		long W = sc.nextLong();
		long W_3 = W /3;
		long W_31 = W_3 + 1;
		long H_3 = H / 3;
		long H_31 = H_3 + 1;
		long W_2 = W / 2;
		long W_21 = W_2 + 1;
		long H_2 = H / 2;
		long H_21 = H_2 + 1;

		List<Long> areaList = new ArrayList<Long>();
		areaList.add(area(H * W_3, H * W_3, H * (W - 2 * W_3)));
		areaList.add(area(H * W_31, H * W_31, H * (W - 2 * W_31)));
		areaList.add(area(W * H_3, W * H_3, W * (H - 2 * H_3)));
		areaList.add(area(W * H_31, W * H_31, W * (H - 2 * H_31)));
		areaList.add(area(H * W_3, H_2 * (W - W_3), (H - H_2) * (W - W_3)));
		areaList.add(area(H * W_31, H_2 * (W - W_31), (H - H_2) * (W - W_31)));
		areaList.add(area(H * W_3, H_21 * (W - W_3), (H - H_21) * (W - W_3)));
		areaList.add(area(H * W_31, H_21 * (W - W_31), (H - H_21) * (W - W_31)));
		areaList.add(area(W * H_3, W_2 * (H - H_3), (W - W_2) * (H - H_3)));
		areaList.add(area(W * H_31, W_2 * (H - H_31), (W - W_2) * (H - H_31)));
		areaList.add(area(W * H_3, W_21 * (H - H_3), (W - W_21) * (H - H_3)));
		areaList.add(area(W * H_31, W_21 * (H - H_31), (W - W_21) * (H - H_31)));

		System.out.println(areaList.stream().min(Comparator.naturalOrder()).get());
	}

	private long area(long a, long b, long c) {
		if (a <= b && b <= c) {
			return c - a;
		}
		if (a <= c && c <= b) {
			return b - a;
		}
		if (b <= a && a <= c) {
			return c - b;
		}
		if (b <= c && c <= a) {
			return a - b;
		}
		if (c <= a && a <= b) {
			return b - c;
		}
		if (c <= b && b <= a) {
			return a - c;
		}
		return 0;
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();

		List<Long> list = new ArrayList<Long>();
		for (int i = 0; i < 3 * N; i++) {
			list.add(sc.nextLong());
		}
		List<BigInteger> scoreList = new ArrayList<>();
		for (int splitIndex = 0; splitIndex <= N; splitIndex++) {
			List<Long> pre = list.subList(0, N + splitIndex);
			List<Long> suf = list.subList(N + splitIndex, 3 * N);

			BigInteger score = BigInteger.ZERO;
			for (long l : pre.stream().mapToLong(i -> i).sorted().skip(splitIndex).toArray()) {
				score = score.add(BigInteger.valueOf(l));
			}
			for (long l : suf.stream().mapToLong(i -> i).sorted().limit(N).toArray()) {
				score = score.subtract(BigInteger.valueOf(l));
			}
			scoreList.add(score);
		}
		System.out.println(scoreList.stream().max((o1, o2) -> o1.compareTo(o2)).get());
	}
}