package code_forces.good_bye_2017;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.StringJoiner;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.solveF();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		String str = sc.next();
		long answer = str.chars().filter(
				asc -> asc == 'a'
					|| asc == 'i'
					|| asc == 'u'
					|| asc == 'e'
					|| asc == 'o'
					|| asc == '1'
					|| asc == '3'
					|| asc == '5'
					|| asc == '7'
					|| asc == '9').count();
		System.out.println(answer);
	}

	private void solveB() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		sc.nextLine();
		char[][] map = new char[N + 2][M + 2];
		map[0] = new char[M + 2];
		map[N + 1] = new char[M + 2];
		int s_r = -1;
		int s_c = -1;
		for (int c = 0; c < M + 2; c++) {
			map[0][c] = '#';
			map[N + 1][c] = '#';
		}
		for (int r = 1; r <= N; r++) {
			map[r][0] = '#';
			String line = sc.nextLine();
			for (int c = 1; c <= M; c++) {
				map[r][c] = line.charAt(c - 1);
				if (map[r][c] == 'S') {
					s_r = r;
					s_c = c;
				}
			}
			map[r][M + 1] = '#';
		}
		String inst = sc.next();
		long ans = 0L;
		for (int left = 0; left < 4; left++) {
			for (int up = 0; up < 4; up++) {
				for (int right = 0; right < 4; right++) {
					for (int down = 0; down < 4; down++) {
						if (left == up || left == right || left == down || up == right || up == down || right == down) {
							continue;
						}
						int r_r = s_r;
						int r_c = s_c;
						for (int i = 0; i < inst.length(); i++) {
							char asc = inst.charAt(i);
							if (asc == '0' + left) {
								r_c--;
							}
							if (asc == '0' + up) {
								r_r--;
							}
							if (asc == '0' + right) {
								r_c++;
							}
							if (asc == '0' + down) {
								r_r++;
							}
							if (map[r_r][r_c] == '#') {
								break;
							}
							if (map[r_r][r_c] == 'E') {
								ans++;
								break;
							}
						}
					}
				}
			}
		}
		System.out.println(ans);
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		double R = 0.0 + sc.nextInt();
		double[] x = new double[N];
		double[] y = new double[N];
		for (int i = 0; i < N; i++) {
			x[i] = 0.0 + sc.nextInt();
			double max_y = R;
			for (int j = 0; j < i; j++) {
				double dy = 4 * R * R - (x[i] - x[j]) * (x[i] - x[j]);
				if (dy >= 0) {
					double tmp_y = y[j] + Math.sqrt(dy);
					if (max_y < tmp_y) {
						max_y = tmp_y;
					}
				}
			}
			y[i] = max_y;
		}
		StringJoiner sj = new StringJoiner(" ");
		for (int i = 0; i < N; i++) {
			sj.add(String.valueOf(y[i]));
		}
		System.out.println(sj.toString());
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		System.out.println(N);
	}

	private void solveE() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		System.out.println(N);
	}

	private void solveF() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		List<Long> redList = new ArrayList<>();
		List<Long> blueList = new ArrayList<>();
		Long prev_green_x = null;
		long ans = 0L;
		for (int i = 0; i < N; i++) {
			long x = sc.nextLong();
			String color = sc.next();
			if (color.equals("G")) {
				long red = calc(redList, prev_green_x, x);
				long blue = calc(blueList, prev_green_x, x);
				long green = 0L;
				if (prev_green_x != null) {
					green = x - prev_green_x;
				}
				if (green > 0L && red + blue > green) {
					ans += green * 2;
				} else {
					ans += green + red + blue;
				}
				prev_green_x = x;
				redList = new ArrayList<>();
				blueList = new ArrayList<>();
			}
			if (color.equals("R")) {
				redList.add(x);
			}
			if (color.equals("B")) {
				blueList.add(x);
			}
		}
		ans += calc(redList, prev_green_x, null);
		ans += calc(blueList, prev_green_x, null);
		System.out.println(ans);
	}

	private long calc(List<Long> list, Long left, Long right) {
		if (list.isEmpty()) {
			return 0L;
		}
		long sum = 0L;
		long max = 0L;
		Long prev = left;
		for (long x : list) {
			if (prev != null) {
				sum += x - prev;
				if (max < x - prev) {
					max = x - prev;
				}
			}
			prev = x;
		}
		if (right != null && prev != null) {
			sum += right - prev;
			if (max < right - prev) {
				max = right - prev;
			}
		}
		if (left != null && right != null) {
			return sum - max;
		}
		return sum;
	}

	interface Graph {
		void link(int from, int to, long cost);
		Optional<Long> getCost(int from, int to);
		int getVertexNum();
	}

	interface FlowResolver {
		long maxFlow(int from, int to);
	}

	/**
	 * グラフの行列による実装
	 * 接点数の大きいグラフで使うとMLEで死にそう
	 */
	class ArrayGraph implements Graph {
		private Long[][] costArray;
		private int vertexNum;

		public ArrayGraph(int n) {
			costArray = new Long[n][];
			for (int i = 0; i < n; i++) {
				costArray[i] = new Long[n];
			}
			vertexNum = n;
		}

		@Override
		public void link(int from, int to, long cost) {
			costArray[from][to] = new Long(cost);
		}

		@Override
		public Optional<Long> getCost(int from, int to) {
			return Optional.ofNullable(costArray[from][to]);
		}

		@Override
		public int getVertexNum() {
			return vertexNum;
		}
	}

	/**
	 * DFS(深さ優先探索)による実装
	 * 計算量はO(E*MaxFlow)のはず (E:辺の数, MaxFlow:最大フロー)
	 */
	class DfsFlowResolver implements FlowResolver {
		private Graph graph;
		public DfsFlowResolver(Graph graph) {
			this.graph = graph;
		}

		/**
		 * 最大フロー(最小カット)を求める
		 * @param from 始点(source)のID
		 * @param to 終点(target)のID
		 * @return 最大フロー(最小カット)
		 */
		public long maxFlow(int from, int to) {
			long sum = 0L;
			long currentFlow;
			do {
				currentFlow = flow(from, to, Long.MAX_VALUE / 3, new boolean[graph.getVertexNum()]);
				sum += currentFlow;
			} while (currentFlow > 0);
			return sum;
		}

		/**
		 * フローの実行 グラフの更新も行う
		 * @param from 現在いる節点のID
		 * @param to 終点(target)のID
		 * @param current_flow ここまでの流量
		 * @param passed 既に通った節点か否かを格納した配列
		 * @return 終点(target)に流した流量/戻りのグラフの流量
		 */
		private long flow(int from, int to, long current_flow, boolean[] passed) {
			passed[from] = true;
			if (from == to) {
				return current_flow;
			}
			for (int id = 0; id < graph.getVertexNum(); id++) {
				if (passed[id]) {
					continue;
				}
				Optional<Long> cost = graph.getCost(from, id);
				if (cost.orElse(0L) > 0) {
					long nextFlow = current_flow < cost.get() ? current_flow : cost.get();
					long returnFlow = flow(id, to, nextFlow, passed);
					if (returnFlow > 0) {
						graph.link(from, id, cost.get() - returnFlow);
						graph.link(id, from, graph.getCost(id, from).orElse(0L) + returnFlow);
						return returnFlow;
					}
				}
			}
			return 0L;
		}
	}

	/**
	 * 1-indexedのBIT配列
	 */
	class BinaryIndexedTree {
		private long[] array;

		public BinaryIndexedTree(int size) {
			this.array = new long[size + 1];
		}

		/**
		 * 指定した要素に値を加算する
		 * 計算量はO(logN)
		 * @param index 加算する要素の添字
		 * @param value 加算する量
		 */
		public void add(int index, long value) {
			for (int i = index; i < array.length; i += (i & -i)) {
				array[i] += value;
			}
		}

		/**
		 * 1〜指定した要素までの和を取得する
		 * 計算量はO(logN)
		 * @param index 和の終端
		 * @return 1〜indexまでの和
		 */
		public long getSum(int index) {
			long sum = 0L;
			for (int i = index; i > 0; i -= (i & -i)) {
				sum += array[i];
			}
			return sum;
		}
	}
}