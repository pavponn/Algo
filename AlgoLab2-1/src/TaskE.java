import java.io.*;
import java.util.*;

public class TaskE {
    FastScanner in;
    int n;
    int m;
    int timer = 0;

    List<List<Pair>> graph;
    List<Boolean> used;

    Set<Integer> answer = new HashSet<>();
    List<Integer> timeIn;
    List<Integer> d;
    int maxColor = 0;
    int[] colors;

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
        colors = new int[m + 1];

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

        cutPointSearch();

        for (int i = 0; i < n + 1; i++) {
            used.set(i, false);
        }
        for (int i = 1; i < n + 1; i++) {
            if (!used.get(i)) {
                paint(i, maxColor, -1, -1);
            }
        }
        System.out.println(maxColor);
        for (int i = 1; i < m; i++) {
            System.out.print(colors[i] + " ");
        }
        System.out.println(colors[m]);

    }

    public void paint(int v, int color, int p, int pe) {
        used.set(v, true);
        for (Pair pair : graph.get(v)) {
            int u = pair.vertex;
            int e = pair.edge;
            if (p == -1) {
                if (!used.get(u)) {
                    ++maxColor;
                    colors[e] = maxColor;
                    paint(u, maxColor, v, e);
                }
            } else {
                if (u == p && e == pe) {
                    continue;
                }
                if (!used.get(u)) {
                    if (d.get(u) >= timeIn.get(v)) {
                        int newColor = ++maxColor;
                        colors[e] = newColor;
                        paint(u, newColor, v, e);
                    } else {
                        colors[e] = color;
                        paint(u, color, v, e);
                    }
                } else if (timeIn.get(u) < timeIn.get(v)) {
                    colors[e] = color;
                }
            }
        }
    }


    public void cutPointSearch() {
        for (int i = 1; i <= n; ++i) {
            if (!used.get(i)) {
                dfs(i, -1, -1);
            }
        }
    }

    public void dfs(int v, int p, int pe) {
        ++timer;
        used.set(v, true);
        timeIn.set(v, timer);
        d.set(v, timer);
        int children = 0;
        for (Pair pair : graph.get(v)) {
            int u = pair.vertex;
            int e = pair.edge;
            if (u == p && e == pe) {
                continue;
            }
            if (used.get(u)) {
                d.set(v, Math.min(d.get(v), timeIn.get(u)));
            } else {
                dfs(u, v, e);
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
        new TaskE().run();
    }
}


