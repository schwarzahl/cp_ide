package am1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.solve();
	}

	private void solve() {
		// input
		Scanner sc = new Scanner(System.in);
		int V = sc.nextInt();
		int E = sc.nextInt();
		List<Edge> edges = new ArrayList<>();
		for (int i = 0; i < E; i++) {
			int u = sc.nextInt();
			int v = sc.nextInt();
			int w = sc.nextInt();
			edges.add(new Edge(u, v, w));
		}
		int Vemb = sc.nextInt();
		int Eemb = sc.nextInt();
		for (int i = 0; i < Eemb; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
		}
		edges.sort((e1, e2) -> e2.w - e1.w);

		// create embmap
		int N = (int)Math.round(Math.sqrt(Vemb));
		int[][] map = new int[N+2][N+2];
		for (int i = 0; i < N; i++) {
			map[i] = new int[N+2];
		}
		for (int i = 0; i < Vemb; i++) {
			map[(i/N)+1][0] = 0;
			map[(i/N)+1][(i%N)+1] = i+1;
			map[(i/N)+1][N+1] = 0;
		}
		for (int i = 0; i < N+2; i++) {
			map[0][i] = 0;
			map[N+1][i] = 0;
		}

		// run and create list
		List<Position> list = new ArrayList<>();
		{
			int px = 1;
			int py = 1;
			int pd = 0;
			int[] sx = {1, 0, -1, 0};
			int[] sy = {0, 1, 0, -1};
			int lx = 1;
			int rx = N;
			int uy = 1;
			int dy = N;
			while (list.size() < Vemb) {
				list.add(new Position(px, py));
				px += sx[pd];
				py += sy[pd];
				if (sx[pd] > 0 && px == rx) {
					uy++;
					pd = (pd + 1) % 4;
				} else if (sy[pd] > 0 && py == dy) {
					rx--;
					pd = (pd + 1) % 4;
				} else if (sx[pd] < 0 && px == lx) {
					dy--;
					pd = (pd + 1) % 4;
				} else if (sy[pd] < 0 && py == uy) {
					lx++;
					pd = (pd + 1) % 4;
				}
			}
			Collections.reverse(list);
		}

		// calc
		int[] ax = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
		int[] ay = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
		Map<Integer, Integer> vMap = new HashMap<>();
		for (Edge edge : edges) {
			boolean isExistU = vMap.containsKey(edge.u);
			boolean isExistV = vMap.containsKey(edge.v);

			if (!isExistU && !isExistV) {
				for (Position pos : list) {
					int id = map[pos.y][pos.x];
					if (!vMap.containsValue(id)) {
						vMap.put(edge.u, id);
						isExistU = true;
						break;
					}
				}
			}
			Integer origin = null;
			Integer add = null;
			if (!isExistU && isExistV) {
				origin = edge.v;
				add = edge.u;
			}
			if (isExistU && !isExistV) {
				origin = edge.u;
				add = edge.v;
			}
			if (origin != null && add != null) {
				for (int ad = 0; ad < 8; ad++) {
					int origin_id = vMap.get(origin);
					int tmp_x = ((origin_id - 1) % N) + 1 + ax[ad];
					int tmp_y = ((origin_id - 1) / N) + 1 + ay[ad];
					int add_id = map[tmp_y][tmp_x];
					if (add_id != 0 && !vMap.containsValue(add_id)) {
						vMap.put(add, add_id);
						break;
					}
				}
			}
		}
		for (int v_i = 1; v_i <= V; v_i++) {
			if (!vMap.containsKey(v_i)) {
				for (Position pos : list) {
					int id = map[pos.y][pos.x];
					if (!vMap.containsValue(id)) {
						vMap.put(v_i, id);
						break;
					}
				}
			}
		}

		// output
		for (Map.Entry<Integer, Integer> entry : vMap.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}

	class Position {
		public int x;
		public int y;
		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	class Edge {
		public int u;
		public int v;
		public int w;
		public Edge(int u, int v, int w) {
			this.u = u;
			this.v = v;
			this.w = w;
		}

	}
}