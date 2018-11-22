import java.util.*;
import java.io.*;
import java.lang.*;

public class TaskE {
    FastScanner in;
    int n;
    int m;
    List<List<Integer>> graph = new ArrayList<>();
    long d[];

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

    List<Edge> edges = new ArrayList<>();
    boolean[] used;
    boolean[] isOk;

    public void solve() {
        n = in.nextInt();
        used = new boolean[n];
        isOk = new boolean[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
//            isOk[i] = true;
        }


        m = in.nextInt();
        int s = in.nextInt() - 1;
        d = new long[n];
        final long INFINITE = (long) (Long.MAX_VALUE - 1e15);
        Arrays.fill(d, INFINITE);
        d[s] = 0;
        for (int i = 0; i < m; i++) {
            int v1 = in.nextInt() - 1;
            int v2 = in.nextInt() - 1;
            edges.add(new Edge(v1, v2, in.nextLong()));
            graph.get(v1).add(v2);
        }

        for (int i = 0; i < n; i++) {
            for (Edge edge : edges) {
                if (d[edge.v1] < INFINITE) {
                    if (d[edge.v2] > d[edge.v1] + edge.weight) {
                        d[edge.v2] = Math.max(-INFINITE, d[edge.v1] + edge.weight);
//                        if (i == n - 1) {
//                            used = new boolean[n];
//                            dfs(edge.v2);
//                        }

                    }
                }

            }
        }
        for (int i = 0; i < n; i++) {
            for (Edge edge : edges) {
                if (d[edge.v1] < INFINITE) {
                    if (d[edge.v2] > d[edge.v1] + edge.weight) {
                        d[edge.v2] = -INFINITE;

                    }
                }

            }
        }

//        long[] dcopy = new long[n];
//        System.arraycopy(used, 0, dcopy, 0, used.length);
//
//        for (Edge edge : edges) {
//            if (dcopy[edge.v1] < INFINITE) {
//                if (dcopy[edge.v2] > dcopy[edge.v1] + edge.weight) {
//                    dcopy[edge.v2] = Math.max(-INFINITE, dcopy[edge.v1] + edge.weight);
//                }
//            }
//
//        }
//        for (int i = 0; i < n; i++) {
//            used = new boolean[n];
//            if (dcopy[i] != used[i]) {
//                dfs(i);
//            }
//        }
        for (int i = 0; i < n; i++) {
            if (d[i] == INFINITE) {
                System.out.println("*");
            } else if (d[i] == -INFINITE) {
                System.out.println("-");
            } else {
                System.out.println(d[i]);
            }
        }

    }

    void dfs(int v) {
        used[v] = true;
        for (int vertex : graph.get(v)) {
            if (!used[v]) {
                dfs(vertex);
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
        new TaskE().run();
    }
}