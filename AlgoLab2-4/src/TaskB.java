import java.util.*;
import java.io.*;
import java.lang.*;


public class TaskB {
    FastScanner in;
    int[] level, deletedVertexes;
    boolean[] used;
    int n, m, t, s;
    long INFINITY = Long.MAX_VALUE;

    class Edge {
        int from, to;
        long flow = 0;
        long capacity;
        Edge back;
        boolean dir;
        int pos;

        Edge(int from, int to, long capacity, int num) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.pos = num;
            this.dir = true;
        }

        Edge(int from, int to, long capacity, int pos, boolean dir) {
            this.from = to;
            this.to = from;
            this.capacity = capacity;
            this.pos = pos;
            this.dir = dir;
        }
    }

    List<List<Edge>> edges = new ArrayList<>();
    List<Integer> partS = new ArrayList<>();
    List<Integer> partT = new ArrayList<>();
    int[][] graph;

    void addEdge(int from, int to, int maxFlow, int pos) {
        Edge e1 = new Edge(from, to, maxFlow, pos);
        Edge e2 = new Edge(from, to, maxFlow, pos, false);
        e1.back = e2;
        e2.back = e1;
        edges.get(from).add(e1);
        edges.get(to).add(e2);
    }


    public void solve() {
        n = in.nextInt();
        m = in.nextInt();
        used = new boolean[n];
        level = new int[n];
        deletedVertexes = new int[n];
        graph = new int[n][n];
        t = n - 1;
        s = 0;
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            addEdge(a - 1, b - 1, c, i);
            graph[a - 1][b - 1] = graph[b - 1][a - 1] = i + 1;

        }

        long result = dinicAlgorithm();
        markDfs(0);
        for (int i = 0; i < n; ++i) {
            if (used[i]) {
                partS.add(i);
            } else {
                partT.add(i);
            }
        }

        List<Integer> minCut = new ArrayList<>();
        for (int i = 0; i < partS.size(); ++i) {
            for (int j = 0; j < partT.size(); ++j) {
                if (graph[partS.get(i)][partT.get(j)] != 0) {
                    minCut.add(graph[partS.get(i)][partT.get(j)]);
                }
            }
        }
        Collections.sort(minCut);
        System.out.println(minCut.size() + " " + result);
        for (int i : minCut) {
            System.out.print(i + " ");
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
                long newDelta = dfs(curEdge.to, Math.min(delta, curEdge.capacity - curEdge.flow));
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
                if (e.flow < e.capacity && level[e.to] == Integer.MAX_VALUE) {
                    level[e.to] = level[cur] + 1;
                    queue.add(e.to);
                }
            }
        }
        return level[t] != Integer.MAX_VALUE;
    }

    void markDfs(int v) {
        used[v] = true;
        for (Edge edge : edges.get(v)) {
            if (edge.flow < edge.capacity && !used[edge.to]) {
                markDfs(edge.to);
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

