import java.util.*;
import java.io.*;
import java.lang.*;

public class TaskD {
    FastScanner in;
    int n;
    int m;
    int k;
    int s;
    List<List<Pair>> graph = new ArrayList<>();
    long d[][];

    class Edge {
        int v1;
        int v2;
        long weight;

        public Edge(int v1, int v2, long weight) {
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }
    }

    class Pair {
        int first;
        long second;

        public Pair(int first, long second) {
            this.first = first;
            this.second = second;
        }
    }

    List<Edge> edges = new ArrayList<>();
    boolean[] used;
    boolean[] isOk;
    long INF = (long) 1e14;

    public void solve() {
        n = in.nextInt();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        m = in.nextInt();
        k = in.nextInt();
        s = in.nextInt() - 1;
        for (int i = 0; i < m; i++) {
            int from = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            int weight = in.nextInt();
            graph.get(to).add(new Pair(from, weight));
        }
        d = new long[k + 1][n];
        for (int i = 0; i < n; i++) {
            d[0][i] = INF;
        }
        d[0][s] = 0;
        for (int i = 1; i <= k; i++) {
            for (int v = 0; v < n; v++) {
                d[i][v] = INF;
                for (Pair pair : graph.get(v)) {
                    if (d[i - 1][pair.first] != INF) {
                        d[i][v] = Math.min(d[i][v], d[i - 1][pair.first] + pair.second);
                    }

                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (d[k][i] == INF) {
                System.out.println(-1);
            } else {
                System.out.println(d[k][i]);
            }
        }
    }


    public void run() {
//          try {
        in = new FastScanner();
//        in = new FastScanner(new File("input.txt"));
        // out = new PrintWriter(new File("output.txt"));
        solve();

//            out.close();

//        } catch (IOException e) {
//              e.printStackTrace();
//          }
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
        new TaskD().run();
    }
}