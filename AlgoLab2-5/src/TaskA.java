import java.util.*;
import java.io.*;
import java.lang.*;


public class TaskA {
    FastScanner in;
    PrintWriter out;
    List<ArrayList<Edge>> edges = new ArrayList<>();
    Edge[] path;
    long [] d;
    int[] id;
    final static long INFINITY = Long.MAX_VALUE - 1;
    int n, m;
    int s, t;
    long minCost = 0;
    long maxFlow = 0;

    class Edge {
        int from, to;
        long capacity, cost;
        long flow;
        int revEdge = -1;

        public Edge(int from, int to, long capacity, long cost, long flow) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.cost = cost;
            this.flow = flow;
        }
    }

    void addEdge(int from, int to, long capacity, long cost) {
        edges.get(from).add(new Edge(from, to, capacity, cost, 0));
        edges.get(to).add(new Edge(to, from, 0, -cost, 0));

        edges.get(from).get(edges.get(from).size() - 1).revEdge = edges.get(to).size() - 1;
        edges.get(to).get(edges.get(to).size() - 1).revEdge = edges.get(from).size() - 1;
    }

    public void solve() {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }
        s = 0;
        t = n - 1;
        for (int i = 0; i < m; i++) {
            int from = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            long capacity = in.nextLong();
            long cost = in.nextLong();
            addEdge(from, to, capacity, cost);
        }

        while (true) {
            bfsSearchLevit();
            if (d[t] == INFINITY) {
                break;
            }
            long delta = INFINITY;
            for (int u = t; u != s; u = path[u].from) {
                Edge edge = path[u];
                delta = Math.min(edge.capacity - edge.flow, delta);
            }
            for (int u = t; u != s; u = path[u].from) {
                Edge edge = path[u];
                Edge reversed = edges.get(edge.to).get(edge.revEdge);
                edge.flow = edge.flow + delta;
                reversed.flow = reversed.flow - delta;
                minCost = minCost + edge.cost * delta;
            }
            maxFlow = maxFlow + delta;
        }
        out.println(minCost);
    }

    void bfsSearchLevit() {
        id = new int[n];
        d = new long[n];
        path = new Edge[n];
        Arrays.fill(path, null);
        Deque<Integer> deque = new ArrayDeque<>();
        Arrays.fill(d, INFINITY);

        d[s] = 0;
        deque.addLast(s);

        while (!deque.isEmpty()) {
            int v = deque.poll();
            id[v] = 2;

            for (Edge e : edges.get(v)) {
                if (e.flow < e.capacity && d[e.to] > d[e.from] + e.cost) {
                    d[e.to] = d[e.from] + e.cost;
                    if (id[e.to] == 0) {
                        deque.addLast(e.to);
                    } else if (id[e.to] == 2) {
                        deque.addFirst(e.to);
                    }
                    path[e.to] = e;
                    id[e.to] = 1;
                }
            }
        }
    }

    public void run() {
        try {
            in = new FastScanner(new File("mincost.in"));
            out = new PrintWriter(new File("mincost.out"));
            solve();

            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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

