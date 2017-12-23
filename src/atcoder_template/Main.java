package atcoder_template;

import java.util.Optional;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.solveC();
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
		int N = sc.nextInt();
		System.out.println(N);
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
	 * IDDFS(反復深化深さ優先探索)による実装
	 * 終了条件は同じ節点を2度通らないDFS(深さ優先探索)で0が返ってきたとき
	 * ほぼDinic法なので計算量はO(E*V*V)のはず (E:辺の数, V:節点の数)
	 */
	class IddfsFlowResolver implements FlowResolver {
		private Graph graph;
		public IddfsFlowResolver(Graph graph) {
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
			int limitDepth = 0;
			while (isExistFlow(from, to)) {
				int currentFlow = flow(from, to,Integer.MAX_VALUE / 3, 0, limitDepth, new boolean[graph.getVertexNum()]);
				sum += currentFlow;
				if (currentFlow == 0) {
					limitDepth++;
				}
			}
			return sum;
		}

		/**
		 * フローの実行 グラフの更新も行う
		 * @param from 現在いる節点のID
		 * @param to 終点(target)のID
		 * @param current_flow ここまでの流量
		 * @param depth 探索(ネスト)の深さ
		 * @param limitDepth 深さ制限
		 * @param passed 既に通った節点か否かを格納した配列
		 * @return 終点(target)に流した流量/戻りのグラフの流量
		 */
		private int flow(int from, int to, int current_flow, int depth, int limitDepth, boolean[] passed) {
			passed[from] = true;
			if (from == to) {
				return current_flow;
			}
			if (depth >= limitDepth) {
				return 0;
			}
			for (int id = 0; id < graph.getVertexNum(); id++) {
				if (passed[id]) {
					continue;
				}
				Optional<Integer> cost = graph.getCost(from, id);
				if (cost.orElse(0) > 0) {
					int nextFlow = current_flow < cost.get() ? current_flow : cost.get();
					int returnFlow = flow(id, to, nextFlow, depth+1, limitDepth, passed);
					if (returnFlow > 0) {
						graph.link(from, id, cost.get() - returnFlow);
						graph.link(id, from, graph.getCost(id, from).orElse(0) + returnFlow);
						return returnFlow;
					}
				}
			}
			return 0;
		}

		/**
		 * fromからtoに0以上の流量を流せるか調べる
		 * @param from 始点(source)のID
		 * @param to 終点(target)のID
		 * @return 0以上流せればtrue
		 */
		private boolean isExistFlow(int from, int to) {
			boolean[] passed = new boolean[graph.getVertexNum()];
			return search(from, to, passed);
		}

		/**
		 * 今までに通ったことのない節点だけを調べるDFS(深さ優先探索)
		 * 計算量は高々O(V)のはず (V:節点の数)
		 * @param from 現在いる節点のID
		 * @param to 終点(target)のID
		 * @param passed 通過済みの節点IDにtrueが格納されている配列
		 * @return toに0以上流せればtrue
		 */
		private boolean search(int from, int to, boolean[] passed) {
			if (from == to) {
				return true;
			}
			passed[from] = true;
			for (int id = 0; id < graph.getVertexNum(); id++) {
				if (!passed[id] && graph.getCost(from, id).orElse(0) > 0 && search(id, to, passed)) {
					return true;
				}
			}
			return false;
		}
	}
}