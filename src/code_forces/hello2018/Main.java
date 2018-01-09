package code_forces.hello2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.solveE();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		if (N >= 27) {
			System.out.println(M);
		} else {
			int mod = 1;
			for (int i = 0; i < N; i++) {
				mod *= 2;
			}
			System.out.println(M % mod);
		}
	}

	private void solveB() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] parents = new int[N + 1];
		boolean[] isParent = new boolean[N + 1];
		for (int i = 2; i <= N; i++) {
			int parent = sc.nextInt();
			parents[i] = parent;
			isParent[parent] = true;
		}
		int[] childNum = new int[N + 1];
		for (int i = 2; i <= N; i++) {
			if (!isParent[i]) {
				childNum[parents[i]]++;
			}
		}
		System.out.println(judge(childNum, isParent) ? "Yes" : "No");
	}

	private boolean judge(int[] childNum, boolean[] isParent) {
		for (int i = 0; i < childNum.length; i++) {
			if (childNum[i] < 3 && isParent[i]) {
				return false;
			}
		}
		return true;
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int L = sc.nextInt();
		long[] costs = new long[32];
		{
			costs[0] = sc.nextLong();
			for (int i = 1; i < N; i++) {
				costs[i] = Math.min(costs[i - 1] * 2, sc.nextLong());
			}
			for (int i = N; i < 32; i++) {
				costs[i] = costs[i - 1] * 2;
			}
		}

		long ans = Long.MAX_VALUE / 3;
		long sum = 0L;
		for (int i = 31; i >= 0 && L > 0; i--) {
			if ((L >> i) > 0) {
				L -= 1 << i;
				sum += costs[i];
			} else {
				if (ans > costs[i] + sum) {
					ans = costs[i] + sum;
				}
			}
		}
		if (ans > sum) {
			ans = sum;
		}
		System.out.println(ans);
	}

	private void updateMap(long cost, long liter, Map<Long, Long> map) {
		long max = 0L;
		if (map.containsKey(cost)) {
			max = map.get(cost);
		}
		if (max < liter) {
			map.put(cost, liter);
		}
	}

	private class Bottle {
		long cost;
		long liter;

		public Bottle(long cost, long liter) {
			this.cost = cost;
			this.liter = liter;
		}

		public double getValue() {
			return 1.0 * liter / cost;
		}
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		System.out.println(N);
	}

	private void solveE() {
		Scanner sc = new Scanner(System.in);
		String[][] map = new String[256][];
		for (int i = 0; i < 256; i++) {
			map[i] = new String[3];
		}
		map[Integer.parseInt("00001111", 2)][0] = "x";
		map[Integer.parseInt("00110011", 2)][0] = "y";
		map[Integer.parseInt("01010101", 2)][0] = "z";
		for (int i = 0; i < 3; i++) {
			for (int key1 = 0; key1 < 256; key1++) {
				for (int level1 = 0; level1 < 3; level1++) {
					String str1 = map[key1][level1];
					if (str1 == null) {
						continue;
					}
					if (level1 > 0) {
						updateMap((~key1 & ~Integer.MIN_VALUE) % 256, "!(" + str1 + ")", 0, map);
					} else {
						updateMap((~key1 & ~Integer.MIN_VALUE) % 256, "!" + str1, 0, map);
					}
					for (int key2 = 0; key2 < 256; key2++) {
						for (int level2 = 0; level2 < 3; level2++) {
							String str2 = map[key2][level2];
							if (str2 == null) {
								continue;
							}
							{
								String str_a = str1;
								String str_b = str2;
								if (level1 > 1) {
									str_a = "(" + str_a + ")";
								}
								if (level2 > 1) {
									str_b = "(" + str_b + ")";
								}
								updateMap(key1 & key2, str_a + "&" + str_b, 1, map);
							}
							{
								String str_a = str1;
								String str_b = str2;
								if (level1 > 2) {
									str_a = "(" + str_a + ")";
								}
								if (level2 > 2) {
									str_b = "(" + str_b + ")";
								}
								updateMap(key1 | key2, str_a + "|" + str_b, 2, map);
							}
						}
					}
				}
			}
		}
		String[] best = new String[256];
		for (int i = 0; i < 256; i++) {
			best[i] = map[i][0];
			if (isShort(best[i], map[i][1])) {
				best[i] = map[i][1];
			}
			if (isShort(best[i], map[i][2])) {
				best[i] = map[i][2];
			}
		}
		int N = sc.nextInt();
		for (int i = 0; i < N; i++) {
			System.out.println(best[Integer.parseInt(sc.next(), 2)]);
		}
	}

	/**
	 * true : target is shorter than base
	 */
	private boolean isShort(String base, String target) {
		if (base == null) {
			return true;
		}
		return base.length() > target.length() || (base.length() == target.length() && base.compareTo(target) > 0);
	}

	private void updateMap(int key, String value, int level, String[][] map) {
		String prev = map[key][level];
		if (isShort(prev, value)) {
			map[key][level] = value;
		}
	}

	private void solveF() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		System.out.println(N);
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

	interface UnionFind {
		void union(int A, int B);
		boolean judge(int A, int B);
		Set<Integer> getSet(int id);
	}

	/**
	 * ArrayUnionFindの拡張
	 * MapSetで根の添字から根にぶら下がる頂点の集合が取得できるようにした
	 * getSetメソッドをO(logN * logN)に落とせているはず
	 * ただしunionメソッドは2倍の計算量になっているので注意(オーダーは変わらないはず)
	 */
	class SetUnionFind extends ArrayUnionFind {
		Map<Integer, Set<Integer>> map;
		public SetUnionFind(int size) {
			super(size);
			map = new HashMap<>();
			for (int i = 0; i < size; i++) {
				map.put(i, new HashSet<>());
				map.get(i).add(i);
			}
		}

		@Override
		protected void unionTo(int source, int dest) {
			super.unionTo(source, dest);
			map.get(dest).addAll(map.get(source));
		}

		@Override
		public Set<Integer> getSet(int id) {
			return map.get(root(id));
		}
	}

	/**
	 * 配列によるUnionFindの実装
	 * getSetメソッドはO(NlogN)なのでTLEに注意
	 */
	class ArrayUnionFind implements UnionFind {
		int[] parent;
		int[] rank;
		int size;
		public ArrayUnionFind(int size) {
			parent = new int[size];
			for (int i = 0; i < size; i++) {
				parent[i] = i;
			}
			rank = new int[size];
			this.size = size;
		}

		@Override
		public void union(int A, int B) {
			int rootA = root(A);
			int rootB = root(B);
			if (rootA != rootB) {
				if (rank[rootA] < rank[rootB]) {
					unionTo(rootA, rootB);
				} else {
					unionTo(rootB, rootA);
					if (rank[rootA] == rank[rootB]) {
						rank[rootA]++;
					}
				}
			}
		}

		protected void unionTo(int source, int dest) {
			parent[source] = dest;
		}

		@Override
		public boolean judge(int A, int B) {
			return root(A) == root(B);
		}

		@Override
		public Set<Integer> getSet(int id) {
			Set<Integer> set = new HashSet<>();
			IntStream.range(0, size).filter(i -> judge(i, id)).forEach(set::add);
			return set;
		}

		protected int root(int id) {
			if (parent[id] == id) {
				return id;
			}
			parent[id] = root(parent[id]);
			return parent[id];
		}
	}

	/**
	 * 素数のユーティリティ
	 */
	class PrimeNumberUtils {
		boolean[] isPrimeArray;
		List<Integer> primes;

		/**
		 * 素数判定の上限となる値を指定してユーティリティを初期化
		 * @param limit 素数判定の上限(この値以上が素数であるか判定しない)
		 */
		public PrimeNumberUtils(int limit) {
			if (limit > 10000000) {
				System.err.println("上限の値が高すぎるため素数ユーティリティの初期化でTLEする可能性が大変高いです");
			}
			primes = new ArrayList<>();
			isPrimeArray = new boolean[limit];
			if (limit > 2) {
				primes.add(2);
				isPrimeArray[2] = true;
			}

			for (int i = 3; i < limit; i += 2) {
				if (isPrime(i, primes)) {
					primes.add(i);
					isPrimeArray[i] = true;
				}
			}
		}

		public List<Integer> getPrimeNumberList() {
			return primes;
		}

		public boolean isPrime(int n) {
			return isPrimeArray[n];
		}

		private boolean isPrime(int n, List<Integer> primes) {
			for (int prime : primes) {
				if (n % prime == 0) {
					return false;
				}
				if (prime > Math.sqrt(n)) {
					break;
				}
			}
			return true;
		}
	}
}