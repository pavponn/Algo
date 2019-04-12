import java.util.*;
import java.io.*;
import java.lang.*;


public class TaskJ {
    FastScanner in;
    int n;
    long w;
    long INFINITY = Long.MAX_VALUE;

    class Barrier {
        long x1, y1, x2, y2;

        public Barrier(long x1, long y1, long x2, long y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    List<Barrier> barriers = new ArrayList<>();
    long[][] distances;
    long[] d;
    boolean[] used;

    public void solve() {
        n = in.nextInt();
        w = in.nextLong();

        distances = new long[n + 2][n + 2];
        d = new long[n + 2];
        used = new boolean[n + 2];
        for (int i = 0; i < n; ++i) {
            barriers.add(new Barrier(in.nextLong(), in.nextLong(), in.nextLong(), in.nextLong()));
        }

        distances[0][n + 1] = distances[n + 1][0] = w;
        for (int i = 0; i < n; ++i) {
            distances[0][i + 1] = distances[i + 1][0] = w - barriers.get(i).y2;
            distances[n + 1][i + 1] = distances[i + 1][n + 1] = barriers.get(i).y1;
            for (int j = 0; j < n; ++j) {
                if (i != j) {
                    distances[i + 1][j + 1] = distances[j + 1][i + 1] = calculateDistance(barriers.get(i), barriers.get(j));
                }

            }
        }
        dijkstra();
        if (d[n + 1] != INFINITY) {
            System.out.println(d[n + 1]);
        } else {
            System.out.println("0");
        }

    }

    long calculateDistance(Barrier b1, Barrier b2) {
        long dx = b1.x1 >= b2.x1 ? b1.x1 - b2.x2 : b2.x1 - b1.x2;
        long dy = b1.y1 >= b2.y1 ? b1.y1 - b2.y2 : b2.y1 - b1.y2;
        return Math.max(0, Math.max(dx, dy));

    }

    public void dijkstra() {
        Arrays.fill(d, INFINITY);
        d[0] = 0;
        int v = 0;
        for (int i = 0; i < n + 2; ++i) {
            v = -1;
            for (int j = 0; j < n + 2; ++j) {
                if (!used[j] && (v == -1 || d[j] < d[v])) {
                    v = j;
                }
            }
            if (d[v] == INFINITY) {
                break;
            }
            used[v] = true;
            for (int j = 0; j < n + 2; ++j) {
                if (v == j) {
                    continue;
                }
                if (d[v] + distances[v][j] < d[j]) {
                    d[j] = d[v] + distances[v][j];
                }
            }
        }
    }

    public void run() {

        in = new FastScanner();
        solve();

    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;
        String str;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {


                try {
                    str = br.readLine();
                    if (str == null) {
                        return "";
                    } else {
                        st = new StringTokenizer(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

    }


    public static void main(String[] args) {
        new TaskJ().run();
    }
}

