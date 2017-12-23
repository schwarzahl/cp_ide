package arc088;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.solveE();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		System.out.println(N);
	}

	private void solveB() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		System.out.println(N);
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		long X = sc.nextLong();
		long Y = sc.nextLong();
		int ans = 0;
		while (X <= Y) {
			X *= 2;
			ans++;
		}
		System.out.println(ans);
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);
		String S = sc.next();
		int N = S.length();
		int ans;
		Character prev = null;
		for (ans = N / 2 + 1; ans <= N; ans++) {
			char right = S.charAt(ans - 1);
			char left = S.charAt(N - ans);
			if (left != right || (prev != null && prev != right)) {
				break;
			}
			prev = right;
		}
		System.out.println(ans - 1);
	}

	private void solveE() {
		Scanner sc = new Scanner(System.in);
		String S = sc.next();
		int N = S.length();
		char[] array = S.toCharArray();
		Map<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < N; i++) {
			char asc = array[i];
			if (!map.containsKey(asc)) {
				map.put(asc, 0);
			}
			map.put(asc, map.get(asc) + 1);
		}
		Character odd = null;
		for (Map.Entry<Character, Integer> entry : map.entrySet()) {
			if (entry.getValue() % 2 == 1) {
				if (odd != null) {
					System.out.println("-1");
					return;
				}
				odd = entry.getKey();
			}
		}
		if (N % 2 == 1 && odd == null) {
			System.out.println("-1");
			return;
		}
		int ans = 0;
		if (odd != null) {
			if (N % 2 == 0) {
				System.out.println("-1");
				return;
			}
			int oddNum = map.get(odd);
			int count = 0;
			int oddIndex = -1;
			for (int index = 0; index < N; index++) {
				if (array[index] == odd) {
					if (++count > oddNum / 2) {
						oddIndex = index;
					}
				}
			}
			char[] newArray = new char[N - 1];
			for (int i = 0; i < N; i++) {
				if (oddIndex == i) {
					continue;
				}
				newArray[i - (oddIndex < i ? 1 : 0)] = array[i];
			}
			ans += N / 2 - oddIndex;
			array = newArray;
		}
		Set<Integer> rights = new HashSet<>();
		int oddShift = 0;
		for (int left = 0; left < N; left++) {
			char source = array[left];
			if (source == '_') {
				continue;
			}
			int right;
			for (right = N - 1; right > left; right--) {
				char target = array[right];
				if (source == target) {
					int shift = 0;
					for (int prev : rights) {
						shift += prev < right ? 1 : 0;
						shift += prev < left ? 1 : 0;
					}
					ans += N - right - left - 1 + shift - oddShift;
					array[left] = '_';
					array[right] = '_';
					rights.add(right);
					break;
				}
			}
			if (right == left) {
				int shift = 0;
				for (int prev : rights) {
					shift += prev < left ? 1 : 0;
				}
				ans += N / 2 - left + shift;
				rights.add(left);
				oddShift = 1;
			}
		}
		System.out.println(ans);
	}

	private void solveF() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		System.out.println(N);
	}

	interface Graph {
		void link(int from, int to, int cost);
		Optional<Integer> getCost(int from, int to);
		int getVertexNum();
	}

	interface FlowResolver {
		int maxFlow(int from, int to);
	}

	/**
	 * グラフの行列による実装
	 * 接点数の大きいグラフで使うとMLEで死にそう
	 */
	class ArrayGraph implements Graph {
		private Integer[][] costArray;
		private int vertexNum;

		public ArrayGraph(int n) {
			costArray = new Integer[n][];
			for (int i = 0; i < n; i++) {
				costArray[i] = new Integer[n];
			}
			vertexNum = n;
		}

		@Override
		public void link(int from, int to, int cost) {
			costArray[from][to] = new Integer(cost);
		}

		@Override
		public Optional<Integer> getCost(int from, int to) {
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
		public int maxFlow(int from, int to) {
			int sum = 0;
			int currentFlow;
			do {
				currentFlow = flow(from, to,Integer.MAX_VALUE / 3, new boolean[graph.getVertexNum()]);
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
		private int flow(int from, int to, int current_flow, boolean[] passed) {
			passed[from] = true;
			if (from == to) {
				return current_flow;
			}
			for (int id = 0; id < graph.getVertexNum(); id++) {
				if (passed[id]) {
					continue;
				}
				Optional<Integer> cost = graph.getCost(from, id);
				if (cost.orElse(0) > 0) {
					int nextFlow = current_flow < cost.get() ? current_flow : cost.get();
					int returnFlow = flow(id, to, nextFlow, passed);
					if (returnFlow > 0) {
						graph.link(from, id, cost.get() - returnFlow);
						graph.link(id, from, graph.getCost(id, from).orElse(0) + returnFlow);
						return returnFlow;
					}
				}
			}
			return 0;
		}
	}
}