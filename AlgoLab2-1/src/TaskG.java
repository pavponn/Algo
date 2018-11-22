import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
import java.lang.*;

public class TaskG {
    FastScanner in;
    int n;
    int m;
    List<List<Integer>> graph;
    List<List<Integer>> graphTr;
    List<Integer> color;
    List<Boolean> used;
    List<Integer> order = new ArrayList<>();
    int[] answer;
    List<String> ansNames = new ArrayList<String>();

    public void solve() {

        n = in.nextInt();
        m = in.nextInt();
        answer = new int[n];
        graph = new ArrayList<List<Integer>>(2 * n);
        graphTr = new ArrayList<List<Integer>>(2 * n);
        color = new ArrayList<Integer>(2 * n);
        used = new ArrayList<Boolean>(2 * n);
        for (int i = 0; i <= 2 * n; i++) {
            graph.add(new ArrayList<Integer>());
            graphTr.add(new ArrayList<Integer>());
            color.add(i, -1);
            used.add(i, false);
        }
        String[] names = new String[2 * n];

        Map<String, Integer> friends = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String name = in.next();
            friends.put(name, i + 1);
            names[i] = name;
        }

        for (int i = 0; i < m; i++) {
            boolean pos1;
            boolean pos2;
            String name1 = in.next();
            pos1 = !name1.startsWith("-");
            name1 = name1.substring(1);

            in.next();

            String name2 = in.next();
            pos2 = !name2.startsWith("-");
            name2 = name2.substring(1);

            int a = friends.get(name1);
            int b = friends.get(name2);
            if (!pos1) {
                a = -a;
            }
            if (!pos2) {
                b = -b;
            }
            a = conv(a);
            b = conv(b);

            graph.get(a).add(b);
            graph.get(b^1).add(a^1);

            graphTr.get(b).add(a);
            graphTr.get(a ^ 1).add(b ^ 1);
        }

        for (int i = 0; i < 2 * n; i++) {
            if (!used.get(i)) {
                dfsFirst(i);
            }
        }
        int c = 0;
        for (int i = 0; i < 2 * n; i++) {
            int vertex = order.get(2 * n - i - 1);
            if (color.get(vertex) == -1) {
                dfsSecond(vertex, c++);
            }
        }

        for (int i = 0; i < n; i++) {
            int index = 2 * i;
            int c1 = color.get(index);
            int c2 = color.get(index ^ 1);
            if (c1 == c2) {
                System.out.println(-1);
                return;
            }
        }

        for (int i = 0; i < n; i++) {
            int index = i * 2;
            int c1 = color.get(index);
            int c2 = color.get(index ^ 1);
            answer[i] = c1 > c2 ? 1 : 0;
            if (answer[i] == 1) {
                ansNames.add(names[i]);
            }
        }
        System.out.println(ansNames.size());
        for (String name : ansNames) {
            System.out.println(name);
        }


    }

    private int conv(int i) {
        if (i >= 0) {
            return 2 * i - 2;
        } else {
            return 2*(-i) - 1;
        }
    }

    private void dfsFirst(int v) {
        used.set(v, true);
        for (int u : graph.get(v)) {
            if (!used.get(u)) {
                dfsFirst(u);
            }
        }
        order.add(v);
    }

    private void dfsSecond(int v, int currentColor) {
        color.set(v, currentColor);
        for (int u : graphTr.get(v)) {
            if (color.get(u) == -1) {
                dfsSecond(u, currentColor);
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
        new TaskG().run();
    }
}

