
import java.util.*;
import java.io.*;
import java.lang.*;

public class TaskB {
    FastScanner in;
    int n;
    int m;
    List<List<Pair>> graph = new ArrayList<>();
    long d[];

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

    public void solve() {
        n = in.nextInt();
        d = new long[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            d[i] = Integer.MAX_VALUE;
        }
        d[0] = 0;
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int firstV = in.nextInt();
            int secondV = in.nextInt();
            int weight = in.nextInt();
            graph.get(firstV - 1).add(new Pair(weight, secondV - 1));
            graph.get(secondV - 1).add(new Pair(weight, firstV - 1));
        }

        Queue<Pair> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new Pair(0, 0));

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
        for (long d_ : d) {
            System.out.print(d_ + " ");
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

