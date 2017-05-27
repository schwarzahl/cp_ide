package agc015;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveC();
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

	private void solveB() {
		Scanner sc = new Scanner(System.in);
		String S = sc.nextLine();
		int N = S.length();
		long sum = 0L;
		for (int i = 0; i < N; i++) {
			if (S.charAt(i) == 'U') {
				sum += 2 * i + 1 * (N - 1 - i);
			} else {
				sum += 1 * i + 2 * (N - 1 - i);
			}
		}
		System.out.println(sum);
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int Q = sc.nextInt();
		sc.nextLine();
		boolean[][] map = new boolean[N][];
		for (int i = 0; i < N; i++) {
			String line = sc.nextLine();
			map[i] = new boolean[M];
			for (int j = 0; j < M; j++) {
				map[i][j] = line.charAt(j) == '1';
			}
		}

		List<List<Range>> rowBlueRangeList = new ArrayList<List<Range>>();
		List<List<Range>> colBlueRangeList = new ArrayList<List<Range>>();
		for (int i = 1; i <= N; i++) {
			boolean isInRange = false;
			List<Range> list = new ArrayList<Range>();
			Range nowRange = null;
			for (int j = 1; j <= M; j++) {
				if (!isInRange && map[i - 1][j - 1]) {
					nowRange = new Range();
					nowRange.start = j;
					isInRange = true;
				}
				if (isInRange && !map[i - 1][j - 1]) {
					nowRange.end = j - 1;
					list.add(nowRange);
					isInRange = false;
				}
			}
			if (isInRange) {
				nowRange.end = M;
				list.add(nowRange);
			}
			rowBlueRangeList.add(list);
		}
		for (int j = 1; j <= M; j++) {
			boolean isInRange = false;
			List<Range> list = new ArrayList<Range>();
			Range nowRange = null;
			for (int i = 1; i <= N; i++) {
				if (!isInRange && map[i - 1][j - 1]) {
					nowRange = new Range();
					nowRange.start = i;
					isInRange = true;
				}
				if (isInRange && !map[i - 1][j - 1]) {
					nowRange.end = i - 1;
					list.add(nowRange);
					isInRange = false;
				}
			}
			if (isInRange) {
				nowRange.end = N;
				list.add(nowRange);
			}
			colBlueRangeList.add(list);
		}
		for (int k = 0; k < Q; k++) {
			int x_1 = sc.nextInt();
			int y_1 = sc.nextInt();
			int x_2 = sc.nextInt();
			int y_2 = sc.nextInt();
			long sum = 0L;
			for (int r = x_1; r <= x_2; r++) {
				sum += countRowBlues(r, y_1, y_2, rowBlueRangeList);
			}
			for (int c = y_1; c <= y_2; c++) {
				sum -= countColBlues(c, x_1, x_2, colBlueRangeList);
			}
			System.out.println(sum);
		}
	}

	class Range {
		public int start;
		public int end;
	}

	/**
	 * @return r行目のc1列～c2列の間の青い区間の数
	 */
	int countRowBlues(int r, int c1, int c2, List<List<Range>> list) {
		int count = 0;
		for (Range range : list.get(r - 1)) {
			if ((c1 <= range.start && range.start <= c2) || (c1 <= range.end && range.end <= c2)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * @return c行目のr1列～r2列の間の青い区間の長さ - 1の和
	 */
	int countColBlues(int c, int r1, int r2, List<List<Range>> list) {
		int length = 0;
		for (Range range : list.get(c - 1)) {
			if ((r1 <= range.start && range.start <= r2) || (r1 <= range.end && range.end <= r2)) {
				int max_start = r1 <= range.start ? range.start : r1;
				int min_end = r2 <= range.end ? r2 : range.end;
				length += min_end - max_start;
			}
		}
		return length;
	}
}