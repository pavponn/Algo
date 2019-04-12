import java.util.*;
import java.io.*;
import java.lang.*;


public class TaskA {
    FastScanner in;
    int[] level, deletedVertexes;
    int n, m, t, s;
    int INFINITY = Integer.MAX_VALUE;

    class Edge {
        int from, to;
        long flow = 0;
        long maxFlow;
        Edge back;
        boolean dir;
        int pos;

        Edge(int from, int to, long maxFlow, int num) {
            this.from = from;
            this.to = to;
            this.maxFlow = maxFlow;
            this.pos = num;
            this.dir = true;
        }

        Edge(int from, int to, long maxFlow, int pos, boolean dir) {
            this.from = to;
            this.to = from;
            this.maxFlow = maxFlow;
            this.pos = pos;
            this.dir = dir;
        }
    }

    List<List<Edge>> edges = new ArrayList<>();

    void addEdge(int from, int to, int capacity, int pos) {
        Edge e1 = new Edge(from, to, capacity, pos);
        Edge e2 = new Edge(from, to, capacity, pos, false);
        e1.back = e2;
        e2.back = e1;
        edges.get(from).add(e1);
        edges.get(to).add(e2);
    }


    public void solve() {
        n = in.nextInt();
        m = in.nextInt();
        level = new int[n];
        deletedVertexes = new int[n];
        long[] flowsOfEdges = new long[m];
        t = n - 1; s = 0;
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            addEdge(a - 1, b - 1, c, i);
        }

        System.out.println(dinicAlgorithm());


        for (int i = 0; i < n; i++) {
            for (Edge e : edges.get(i)) {
                if (e.dir) {
                    flowsOfEdges[e.pos] = e.flow;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            System.out.println(flowsOfEdges[i]);
        }
    }


    public long dinicAlgorithm() {
        long maxFlow = 0;
        while (bfs()) {
            Arrays.fill(deletedVertexes, 0);
            long flow = dfs(0, INFINITY);
            while (flow != 0) {
                maxFlow += flow;
                flow = dfs(0, INFINITY);
            }
        }
        return maxFlow;
    }

    long dfs(int u, long delta) {
        if (u == t || delta == 0) {
            return delta;
        }

        for (int v = deletedVertexes[u]; v < edges.get(u).size(); v++) {
            Edge curEdge = edges.get(u).get(v);
            if (level[curEdge.to] == level[u] + 1) {
                long newDelta = dfs(curEdge.to, Math.min(delta, curEdge.maxFlow - curEdge.flow));
                if (newDelta != 0) {
                    curEdge.flow += newDelta;
                    curEdge.back.flow -= newDelta;
                    return newDelta;
                }
            }
            ++deletedVertexes[u];
        }
        return 0;
    }

    boolean bfs() {
        Arrays.fill(level, Integer.MAX_VALUE);
        level[0] = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (Edge e : edges.get(cur)) {
                if (e.flow < e.maxFlow && level[e.to] == INFINITY) {
                    level[e.to] = level[cur] + 1;
                    queue.add(e.to);
                }
            }
        }
        return level[t] != INFINITY;
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

