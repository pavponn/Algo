import java.util.*;
import java.io.*;
import java.lang.*;


public class TaskC {
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
        boolean dir, visited = false;
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

    void addEdge(int from, int to, int maxFlow, int num) {
        Edge e1 = new Edge(from, to, maxFlow, num);
        Edge e2 = new Edge(from, to, 0, num, false);
        e1.back = e2;
        e2.back = e1;
        edges.get(from).add(e1);
        edges.get(to).add(e2);
    }


    public void solve() {
        n = in.nextInt();
        m = in.nextInt();
        s = in.nextInt() - 1;
        t = in.nextInt() - 1;
        used = new boolean[m];
        level = new int[n];
        deletedVertexes = new int[n];

        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            if (a != b) {
                addEdge(a - 1, b - 1, 1, i);
            }

        }

        long result = dinicAlgorithm();
        if (result < 2) {
            System.out.println("NO");
            return;
        }
        System.out.println("YES");
        List<Integer> mashaPath = new ArrayList<>();
        List<Integer> petyaPath = new ArrayList<>();

        markDfs(s,mashaPath);
        markDfs(s, petyaPath);
        for (int v : mashaPath) {
            System.out.print(v + 1 + " ");
        }
        System.out.println();
        for (int v : petyaPath) {
            System.out.print(v + 1 + " ");
        }
    }


    public long dinicAlgorithm() {
        long maxFlow = 0;
        while (bfs()) {
            Arrays.fill(deletedVertexes, 0);
            long flow = dfs(s, INFINITY);
            while (flow != 0) {
                maxFlow += flow;
                flow = dfs(s, INFINITY);
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
        level[s] = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(s);
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

    void markDfs(int v, List<Integer> result) {
        result.add(v);
        if (v == t) {
            return;
        }

        for (Edge e: edges.get(v)) {
            if (!e.visited && e.flow == 1) {
                e.visited = true;
                markDfs(e.to, result);
                break;
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
        new TaskC().run();
    }
}

