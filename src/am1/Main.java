package am1;

import java.util.HashMap;
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
		int[][] edges = new int[V+1][];
		for (int i = 1; i <= V; i++) {
			edges[i] = new int[V+1];
		}
		for (int i = 0; i < E; i++) {
			int u = sc.nextInt();
			int v = sc.nextInt();
			int w = sc.nextInt();
			edges[u][v] = w;
			edges[v][u] = w;
		}
		int Vemb = sc.nextInt();
		int Eemb = sc.nextInt();
		for (int i = 0; i < Eemb; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
		}

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

		// calc
		// Key=emb_id, Value=g_id
		Map<Integer, Integer> vMap = new HashMap<>();
		{
			int max_v1 = 0;
			int max_v2 = 0;
			int max_v3 = 0;
			long max = 0L;
			for (int v1 = 1; v1 <= V; v1++) {
				for (int v2 = v1 + 1; v2 <= V; v2++) {
					for (int v3 = v2 + 1; v3 <= V; v3++) {
						long tmp = edges[v1][v2] + edges[v2][v3] + edges[v3][v1];
						if (max < tmp) {
							max = tmp;
							max_v1 = v1;
							max_v2 = v2;
							max_v3 = v3;
						}
					}
				}
			}
			vMap.put(map[N/2][N/2], max_v1);
			vMap.put(map[N/2+1][N/2], max_v2);
			vMap.put(map[N/2][N/2+1], max_v3);
		}
		int[] ax = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
		int[] ay = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
		int lx = N/2;
		int rx = N/2+1;
		int uy = N/2;
		int dy = N/2+1;
		while (vMap.size() < V) {
			long max = -1;
			int max_vid = 0;
			int max_x = -1;
			int max_y = -1;
			int max_eid = -1;
			for (int sx = lx - 1; sx <= rx + 1; sx++) {
				for (int sy = uy - 1; sy <= uy + 1; sy++) {
					int eid = map[sy][sx];
					if (eid != 0 && !vMap.containsKey(eid)) {
						for (int vid = 1; vid <= V; vid++) {
							if (!vMap.containsValue(vid)) {
								long sum = 0L;
								for (int ad = 0; ad < 8; ad++) {
									int tmp_x = sx + ax[ad];
									int tmp_y = sy + ay[ad];
									int add_id = map[tmp_y][tmp_x];
									if (vMap.containsKey(add_id)) {
										sum += edges[vid][vMap.get(add_id)];
									}
								}
								if (max < sum) {
									max = sum;
									max_vid = vid;
									max_eid = eid;
									max_x = sx;
									max_y = sy;
								}
							}
						}
					}
				}
			}
			vMap.put(max_eid, max_vid);
			if (lx > max_x) lx = max_x;
			if (rx < max_x) rx = max_x;
			if (uy > max_y) uy = max_y;
			if (dy < max_y) dy = max_y;
		}

		// output
		for (Map.Entry<Integer, Integer> entry : vMap.entrySet()) {
			System.out.println(entry.getValue() + " " + entry.getKey());
		}
	}
}