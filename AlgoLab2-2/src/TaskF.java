import java.util.*;
import java.io.*;
import java.lang.*;

public class TaskF {
    FastScanner in;
    int n;
    int m;
    int a, b, c;
    List<List<Pair>> graph = new ArrayList<>();
    long d1[];
    long d2[];
    long d3[];

    class Pair implements Comparable<Pair> {
        long weight;
        int vertex;

        public Pair(long weight, int vertex) {
            this.weight = weight;
            this.vertex = vertex;
        }

        @Override
        public int compareTo(Pair o) {
            return Long.compare(this.weight, o.weight);
        }

    }

    final long INF = (long)1e18;

    public void solve() {
        n = in.nextInt();
        d1 = new long[n];
        d2 = new long[n];
        d3 = new long[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            d1[i] = INF;
            d2[i] = INF;
            d3[i] = INF;
        }
        m = in.nextInt();

        for (int i = 0; i < m; i++) {
            int firstV = in.nextInt();
            int secondV = in.nextInt();
            long weight = in.nextLong();
            graph.get(firstV - 1).add(new Pair(weight, secondV - 1));
            graph.get(secondV - 1).add(new Pair(weight, firstV - 1));
        }

        a = in.nextInt() - 1;
        b = in.nextInt() - 1;
        c = in.nextInt() - 1;
        d1[a] = 0;
        d2[b] = 0;
        d3[c] = 0;

        dijkstra(a, d1);
        dijkstra(b, d2);
        dijkstra(c, d3);

        long answer = INF;
        int index = -1;
        for (int i = 0; i < n; i++) {
            long minn = Math.min(d1[i], Math.min(d2[i],d3[i]));
            long distance = minn + d1[i] + d2[i] + d3[i];
            if (distance < answer) {
                answer = distance;
            }
        }
       if (answer == INF) {
           System.out.println(-1);
       } else {
           System.out.println(answer);
       }

    }

    long[] dijkstra(int v, long[] d) {
        Queue<Pair> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new Pair(0, v));
        while (!priorityQueue.isEmpty()) {
            Pair curEdge = priorityQueue.poll();
            if (curEdge.weight > d[curEdge.vertex]) {
                continue;
            }
            for (Pair p : graph.get(curEdge.vertex)) {
                if (d[curEdge.vertex] + p.weight < d[p.vertex]) {
                    d[p.vertex] = d[curEdge.vertex] + p.weight;
                    priorityQueue.add(new Pair(d[p.vertex], p.vertex));
                }
            }
        }
        return d;
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
        new TaskF().run();
    }
}