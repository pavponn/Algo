import java.util.*;
import java.io.*;
import java.lang.*;

public class TaskA {
    FastScanner in;
    int n;
    int m;
    final int GREY = 0;
    final int BLACK = -1;
    final int WHITE = 1;
    boolean hasCycle = false;
    List<List<Integer>> graph;
    List<Integer> color;
    List<Boolean> used;
    List<Integer> answer = new ArrayList<>();


    public void solve() {

        n = in.nextInt();
        m = in.nextInt();
        graph = new ArrayList<List<Integer>>(n + 1);
        color = new ArrayList<Integer>(n + 1);
        used = new ArrayList<Boolean>(n + 1);
        Collections.fill(graph, new ArrayList<Integer>());
        Collections.fill(color, WHITE);
        Collections.fill(used, false);

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<Integer>());
            color.add(i, WHITE);
            used.add(i, false);
        }

        for (int i = 0; i < m; i++) {
            int v = in.nextInt();
            int u = in.nextInt();
            graph.get(v).add(u);
        }

        for (int i = 1; i < n + 1; i++) {
            if (color.get(i) != BLACK) {
                dfsCycle(i);
            }
        }
        if (hasCycle) {
            System.out.println(-1);
            return;
        }
        topSort();
        Collections.reverse(answer);
        for (int i = 0; i < answer.size(); i++) {
            if (i != answer.size() - 1) {
                System.out.print(answer.get(i) + " ");
            } else
                System.out.println(answer.get(i));

        }


    }


    public void dfsTopSort(int v) {
        used.set(v, true);
        for (int u: graph.get(v)) {
            if (!used.get(u)) {
                dfsTopSort(u);
            }
        }
        answer.add(v);
    }
    public void topSort() {
        for (int i = 1; i < n + 1; i++) {
            if (!used.get(i)) {
                dfsTopSort(i);
            }
        }
    }
    public void dfsCycle(int v) {
        color.set(v, GREY);
        for (int u : graph.get(v)) {
            if (color.get(u) == WHITE) {
                dfsCycle(u);
            } else if (color.get(u) == GREY) {
                hasCycle = true;
                return;
            }
        }
        color.set(v, BLACK);
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
        new TaskA().run();
    }
}

