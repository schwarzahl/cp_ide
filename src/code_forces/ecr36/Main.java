package code_forces.ecr36;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
		main.solveC();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();
		int min = Integer.MAX_VALUE / 3;
		for (int i = 0; i < N; i++) {
			int bucket = sc.nextInt();
			if (K % bucket == 0) {
				if (min > K / bucket) {
					min = K / bucket;
				}
			}
		}
		System.out.println(min);
	}

	private void solveB() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int pos = sc.nextInt();
		int l = sc.nextInt();
		int r = sc.nextInt();
		int ans = 0;
		boolean left = 1 < l;
		boolean right = r < N;
		int lp = Math.abs(l - pos);
		int pr = Math.abs(r - pos);
		boolean leftFirst;
		if (lp == pr) {
			leftFirst = left;
		} else {
			leftFirst = lp < pr;
		}
		if (leftFirst) {
			if (left) {
				ans += lp + 1;
				pos = l;
			}
			if (right) {
				ans += r - pos + 1;
			}
		} else {
			if (right) {
				ans += pr + 1;
				pos = r;
			}
			if (left) {
				ans += pos - l + 1;
			}
		}
		System.out.println(ans);
	}

	private void solveC() {
		Scanner sc = new Scanner(System.in);
		String a = sc.next();
		String b = sc.next();
		int[] num = new int[10];
		for (char c : a.toCharArray()) {
			num[c - '0']++;
		}
		long ans = 0L;
		if (a.length() < b.length()) {
			for (int j = 9; j >= 0; j--) {
				for (int c = 0; c < num[j]; c++) {
					ans = ans * 10L + j;
				}
			}
		} else {
			ans = search(num, 0L, b);
		}
		System.out.println(ans);
	}

	private Long search(int[] num, long current, String b) {
		int keta = current == 0L ? 0 : ("" + current).length();
		if (b.length() <= keta) {
			if (Long.parseLong(b) > current) {
				return current;
			} else {
				return null;
			}
		}
		int degit = b.charAt(keta) - '0';
		if (num[degit] > 0) {
			int[] tmp = Arrays.copyOf(num, num.length);
			tmp[degit]--;
			Long ans = search(tmp, current * 10L + degit, b);
			if (ans != null) {
				return ans;
			}
		}
		for (int i = degit - 1; i >= 0; i--) {
			if (num[i] > 0) {
				int[] tmp = Arrays.copyOf(num, num.length);
				tmp[i]--;
				current = current * 10L + i;
				for (int j = 9; j >= 0; j--) {
					for (int c = 0; c < tmp[j]; c++) {
						current = current * 10L + j;
					}
				}
				return current;
			}
		}
		return null;
	}

	private void solveD() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int[] edge = new int[M];
		boolean[][] graph = new boolean[N + 1][];
		int[][] edgeId = new int[N + 1][];
		int[] roopCount = new int[M];
		for (int i = 0; i <= N; i++) {
			graph[i] = new boolean[N + 1];
			edgeId[i] = new int[N + 1];
		}
		for (int i = 0; i < M; i++) {
			int u = sc.nextInt();
			int v = sc.nextInt();
			graph[u][v] = true;
			edgeId[u][v] = i;
			edge[i] = u * (N + 1) + v;
		}
		int max = 0;
		for (int start = 1; start <= N; start++) {
			int[] count = new int[1];
			search(start, graph, new boolean[N + 1], start, roopCount, edgeId, N, count);
			if (max < count[0]) {
				max = count[0];
			}
		}
		for (int i = 0; i < M; i++) {
			if (max == roopCount[i]) {
				System.out.println("YES");
				return;
			}
		}
		System.out.println("NO");
	}

	private boolean search(int current, boolean[][] graph, boolean[] passed, int start, int[] roopCount, int[][] edgeId, int N, int[] count) {
		if (passed[current]) {
			if (current == start) {
				count[0]++;
				return true;
			}
			return false;
		}
		passed[current] = true;
		for (int dest = 1; dest <= N; dest++) {
			if (graph[current][dest]) {
				if (search(dest, graph, Arrays.copyOf(passed, passed.length), start, roopCount, edgeId, N, count)) {
					roopCount[edgeId[current][dest]]++;
				}
			}
		}
		return false;
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