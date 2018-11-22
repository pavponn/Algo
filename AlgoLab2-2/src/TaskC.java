import java.util.*;
import java.io.*;
import java.lang.*;

public class TaskC {
    FastScanner in;
    int n;
    int m;
    List<List<Edge>> graph = new ArrayList<>();
    long d[];
    int parents[];
    final int INFINITE = 1000000000;

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

    public void solve() {
        int n = in.nextInt();
        d = new long[n];
        parents = new int[n];
        Arrays.fill(parents, -1);
        Arrays.fill(d, 0);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int w = in.nextInt();
                if (w != 100000) {
                    edges.add(new Edge(i, j, w));
                }
            }
        }
        int finalPoint = -1;
        for (int i = 0; i < n; i++) {
            finalPoint = -1;
            for (Edge edge : edges) {
                if (d[edge.v2] > d[edge.v1] + edge.weight) {
                    d[edge.v2] = Math.max(-INFINITE, d[edge.v1] + edge.weight);
                    parents[edge.v2] = edge.v1;
                    finalPoint = edge.v2;
                }
            }
        }

        if (finalPoint == -1) {
            System.out.println("NO");
            return;
        }
        System.out.println("YES");
        int startPoint = finalPoint;
        List<Integer> cycle = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            startPoint = parents[startPoint];
        }
        int cur = startPoint;
        while (true) {
            cycle.add(cur);
            if (cur == startPoint && cycle.size() > 1) {
                break;
            }
            cur = parents[cur];
        }
        System.out.println(cycle.size() - 1);
        for (int i = cycle.size() - 1; i > 0; i--) {
            System.out.print(cycle.get(i) + 1 + " ");
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
        new TaskC().run();
    }
}