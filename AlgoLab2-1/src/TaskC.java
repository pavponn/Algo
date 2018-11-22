import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class TaskC {
    FastScanner in;
    int n;
    int m;
    int timer = 0;

    List<List<Integer>> graph;
    List<Boolean> used;

    Set<Integer> answer = new HashSet<>();
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

        graph = new ArrayList<List<Integer>>(n + 1);
        used = new ArrayList<Boolean>(n + 1);
        timeIn = new ArrayList<Integer>(n + 1);
        d = new ArrayList<Integer>(n + 1);


        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<Integer>());
            used.add(i, false);
            timeIn.add(i, 0);
            d.add(i, Integer.MAX_VALUE);

        }

        for (int i = 1; i <= m; i++) {
            int v = in.nextInt();
            int u = in.nextInt();
            graph.get(v).add(u);
            graph.get(u).add(v);
        }

        cutPointSearch();

        Integer [] res = answer.toArray(new Integer[answer.size()]);
        Arrays.sort(res);

        System.out.println(res.length);

        for (int i = 0; i < answer.size(); ++i) {
            if (i != n - 1) {
                System.out.print(res[i] + " ");
            } else {
                System.out.println(res[i]);
            }
        }


    }

    public void cutPointSearch() {
        for (int i = 1; i <= n; ++i) {
            if (!used.get(i)) {
                timer = 0;
                dfs(i, -1);
            }
        }
    }

    public void dfs(int v, int p) {
        used.set(v, true);
        timeIn.set(v, timer);
        d.set(v, timer);
        ++timer;
        int children = 0;
        for (int u : graph.get(v)) {
            if (u == p) {
                continue;
            }
            if (used.get(u)) {
                d.set(v, Math.min(d.get(v), timeIn.get(u)));
            } else {
                dfs(u, v);
                d.set(v, Math.min(d.get(v), d.get(u)));
                if ((d.get(u) >= timeIn.get(v)) && (p != -1)) {
                    answer.add(v);
                }
                ++children;
            }
        }
        if ((p == -1) && (children > 1)) {
            answer.add(v);
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


