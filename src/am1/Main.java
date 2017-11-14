package am1;

import java.util.ArrayList;
import java.util.List;
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
		for (int i = 0; i < E; i++) {
			int u = sc.nextInt();
			int v = sc.nextInt();
			int w = sc.nextInt();
		}
		int Vemb = sc.nextInt();
		int Eemb = sc.nextInt();
		for (int i = 0; i < Eemb; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
		}

		// create embmap
		int N = (int)Math.round(Math.sqrt(Vemb));
		int[][] map = new int[N][N];
		for (int i = 0; i < N; i++) {
			map[i] = new int[N];
		}
		for (int i = 0; i < Vemb; i++) {
			map[i/N][i%N] = i+1;
		}

		// run and create list
		List<Position> list = new ArrayList<>();
		int px = 0;
		int py = 0;
		int pd = 0;
		int[] sx = {1, 0, -1, 0};
		int[] sy = {0, 1, 0, -1};
		int lx = 0;
		int rx = N - 1;
		int uy = 0;
		int dy = N - 1;
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

		// output
		for (int v_ind = 1; v_ind <= V; v_ind++) {
			Position pos = list.get(Vemb - v_ind);
			System.out.println(v_ind + " " + map[pos.y][pos.x]);
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
}