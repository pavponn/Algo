import java.util.*;
import java.io.*;

import static java.lang.Math.*;


public class TaskF {
    FastScanner in;
    int[] level, deletedVertexes;
    boolean[] used;
    int n, m, t, s;
    long INFINITY = 10000000;
    double EPS = 0.000001;
    int[] matching;
    List<List<Integer>> edges = new ArrayList<>();
    List<Pair> rightSide = new ArrayList<>();
    List<Thing> leftSide = new ArrayList<>();

    class Thing {
        double x;
        double y;
        double speed;

        Thing(double x, double y, double speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
        }
    }

    class Pair {
        double x;
        double y;

        Pair(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }


    public void solve() {
        n = in.nextInt();
        matching = new int[n];
        for (int i = 0; i < n; ++i) {
            double x = in.nextDouble();
            double y = in.nextDouble();
            double v = in.nextDouble();
            leftSide.add(new Thing(x, y, v));
        }
        for (int i = 0; i < n; ++i) {
            double x = in.nextDouble();
            double y = in.nextDouble();
            rightSide.add(new Pair(x, y));
        }

        double l = 0;
        double r = INFINITY;
        while (r - l > EPS) {
            double middle =  (r + l) / 2;
            if (tryTime(middle)) {
                r = middle;
            } else {
                l = middle;
            }
        }

        System.out.println(r);

    }

    double dist(Thing t, Pair p) {
        return sqrt((t.x - p.x) * (t.x - p.x) + (t.y - p.y) * (t.y - p.y));
    }

    double calcTime(Thing t, Pair p) {
        return (dist(t, p) / t.speed);
    }

    boolean tryTime(double time) {
        long size = 0;
        edges = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                double t = calcTime(leftSide.get(i), rightSide.get(j));
                if (t <= time) {
                    edges.get(i).add(j);
                }
            }
        }
        Arrays.fill(matching, -1);
        for (int i = 0; i < n; ++i) {
            used = new boolean[n];
            kuhn(i);
        }
        size = Arrays.stream(matching).parallel().filter(x -> x != -1).count();

        return size == n;
    }

    boolean kuhn(int v) {
        if (used[v]) {
            return false;
        }
        used[v] = true;
        for (int to : edges.get(v)) {
            if (matching[to] == -1 || kuhn(matching[to])) {
                matching[to] = v;
                return true;
            }
        }
        return false;
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

        double nextDouble() {
            return Double.parseDouble(next());
        }

    }


    public static void main(String[] args) {
        new TaskF().run();
    }
}

