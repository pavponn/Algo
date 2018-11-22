import java.io.*;
import java.util.*;

public class TaskI {
    FastScanner in;
    int n;
    double answer;

    class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    double dist(Point first, Point second) {
        double sq = (first.x - second.x) * (first.x - second.x) + (first.y - second.y) * (first.y - second.y);
        return Math.sqrt(sq);
    }

    public void solve() {
        n = in.nextInt();
        Point[] vertexes = new Point[n];
        double[] minEdge = new double[n];
        boolean[] used = new boolean[n];
        Arrays.fill(minEdge, Double.MAX_VALUE);
        Arrays.fill(used, false);
        minEdge[0] = 0;
        for (int i = 0; i < n; i++) {
            vertexes[i] = new Point(in.nextInt(), in.nextInt());
        }

        for (int i = 0; i < n; i++) {
            int v = -1;
            for (int j = 0; j < n; j++) {
                if (!used[j] && (v == -1 || minEdge[j] < minEdge[v])) {
                    v = j;
                }
            }
            used[v] = true;
            answer += minEdge[v];
            for (int to = 0; to < n; to++) {
                if (to!= v) {
                    double dist = dist(vertexes[v], vertexes[to]);
                    if (dist < minEdge[to]) {
                        minEdge[to] = dist;
                    }
                }


            }
        }
//
//        for (double a : minEdge) {
//            answer += a;
//        }
        System.out.println(answer);
    }


    public void run() {
//          try {

        in = new FastScanner();
//      in = new FastScanner(new File("input.txt"));
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
        new TaskI().run();
    }
}


