import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class TaskB {
    FastScanner in;
    int n;
    int m;
    int timer = 0;

    List<List<Pair>> graph;
    List<Boolean> used;

    List<Integer> answer = new ArrayList<>();
    List<Integer> timeIn;
    List<Integer> d;


    class Pair {
        final int vertex;
        final int edge;

        public Pair(int vertex, int edge) {
            this.vertex = vertex;
            this.edge = edge;
        }
    }

    public void solve() {

        n = in.nextInt();
        m = in.nextInt();

        graph = new ArrayList<List<Pair>>(n + 1);
        used = new ArrayList<Boolean>(n + 1);
        timeIn = new ArrayList<Integer>(n + 1);
        d = new ArrayList<Integer>(n + 1);


        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<Pair>());
            used.add(i, false);
            timeIn.add(i, 0);
            d.add(i, Integer.MAX_VALUE);

        }

        for (int i = 1; i <= m; i++) {
            int v = in.nextInt();
            int u = in.nextInt();
            graph.get(v).add(new Pair(u, i));
            graph.get(u).add(new Pair(v, i));
        }

        bridgeSearch();
        Collections.sort(answer);
        System.out.println(answer.size());
        for (int i = 0; i < answer.size(); ++i) {
            if (i != n - 1) {
                System.out.print(answer.get(i) + " ");
            } else {
                System.out.println(answer.get(i));
            }

        }


    }

    public void bridgeSearch() {
        for (int i = 1; i <= n; ++i) {
            if (!used.get(i)) {
                dfs(i, -1);
            }
        }
    }

    public void dfs(int v, int p) {
        used.set(v, true);
        timeIn.set(v, timer);
        d.set(v, timer);
        ++timer;
        for (Pair pair : graph.get(v)) {
            int u = pair.vertex;
            int e = pair.edge;
            if (u == p) {
                continue;
            }
            if (used.get(u)) {
                d.set(v, Math.min(d.get(v), timeIn.get(u)));
            } else {
                dfs(u, v);
                d.set(v, Math.min(d.get(v), d.get(u)));
                if (d.get(u) > timeIn.get(v)) {
                    answer.add(e);
                }
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
        new TaskB().run();
    }
}


