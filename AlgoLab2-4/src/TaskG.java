import java.util.*;
import java.io.*;
import java.lang.*;


public class TaskG {
    FastScanner in;
    int[] level, deletedVertexes;
    boolean[] used;
    int n, m, t, s;
    int INFINITY = Integer.MAX_VALUE;

    class Edge {
        int from, to;
        long flow;
        long capacity;
        Edge back;
        boolean hasBack = true;
        boolean dir = true;
        int pos;
        int cordX = -1, cordY = -1;


        Edge(int from, int to, long capacity, int pos) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.pos = pos;
        }

        Edge (int from, int to, long capacity, int pos, boolean dir) {
            this.from = to;
            this.to = from;
            this.capacity = capacity;
            this.pos = pos;
            dir = dir;
        }

        Edge(int from, int to, long capacity, long flow, int cordX, int cordY, boolean hasBack) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = flow;
            this.cordX = cordX;
            this.cordY = cordY;
            this.hasBack = hasBack;

        }
    }

    List<List<Edge>> edges = new ArrayList<>();


    void addEdge(int from, int to, int maxFlow, int pos) {
        Edge e1 = new Edge(from, to, maxFlow, 0);
        Edge e2 = new Edge(from, to, 0, 0, false);
        e1.back = e2;
        e2.back = e1;
        edges.get(from).add(e1);
        edges.get(to).add(e2);
    }

    void addEdge(int from, int to, int capacity, int x, int y, boolean hasBack) {
        edges.get(from).add(new Edge(from, to, capacity, 0, x, y, hasBack));
    }


    public void solve() {
        level = new int[200000];
        deletedVertexes = new int [200000];
        for (int i = 0; i < 200000; ++i) {
            edges.add(new ArrayList<>());

        }
        String cur;
        n = in.nextInt();
        m = in.nextInt();
        char[][] map = new char[n][m];
        for (int i = 0; i < n; ++i) {
            cur = in.next();
            for (int j = 0; j < m; ++j) {
                switch (cur.charAt(j)) {
                    case 'A': {
                        s = secondIndex(i, j);
                        map[i][j] = 'A';
                        break;
                    }
                    case 'B': {
                        t = firstIndex(i, j);
                        map[i][j] = 'B';
                        break;
                    }
                    case '#': {
                        addEdge(firstIndex(i, j), secondIndex(i, j), 0, i, j, false);
                        map[i][j] = '#';
                        break;
                    }
                    case '.': {
                        addEdge(firstIndex(i, j), secondIndex(i, j), 1, i, j, false);
                        map[i][j] = '.';
                        break;
                    }
                    case '-': {
                        map[i][j] = '-';
                        addEdge(firstIndex(i, j), secondIndex(i, j), INFINITY, i, j, false);
                        break;
                    }
                }

            }
        }
        for (int i = 0; i < m - 1; ++i) {
            addEdge(secondIndex(n - 1, i + 1), firstIndex(n - 1, i), INFINITY, 0);
            addEdge(secondIndex(n - 1, i), firstIndex(n - 1, i + 1), INFINITY, 0);
        }

        for (int i = 0; i < n - 1; ++i) {
            addEdge(secondIndex(i, m - 1), firstIndex(i + 1, m - 1), INFINITY, 0);
            addEdge(secondIndex(i + 1, m - 1), firstIndex(i, m - 1), INFINITY, 0);
        }

        // square paths
        for (int i = 0; i < n - 1; ++i) {
            for (int j = 0; j < m - 1; ++j) {
                addEdge(secondIndex(i, j), firstIndex(i + 1, j), INFINITY, 0);
                addEdge(secondIndex(i + 1, j), firstIndex(i, j), INFINITY, 0);
                addEdge(secondIndex(i, j + 1), firstIndex(i, j), INFINITY, 0);
                addEdge(secondIndex(i, j), firstIndex(i, j + 1), INFINITY, 0);
            }
        }
        long flow = dinicAlgorithm();
        if (flow >= INFINITY) {
            System.out.println("-1");
            return;
        } else {
            System.out.println(flow);
        }
        used = new boolean[200000];
        List<Integer> partS = new ArrayList<>();
        markDfs(s, partS);
        for (int v : partS) {
            for (Edge edge : edges.get(v)) {
                if (edge.flow == 1 && !used[edge.to]) {
                    map[edge.cordX][edge.cordY] = '+';
                    break;
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            System.out.println(new String(map[i]));
        }

    }

    int firstIndex(int i, int j) {
        return i * m + j;
    }

    int secondIndex(int i, int j) {
        return n * m + firstIndex(i, j);
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

        for (int v = deletedVertexes[u]; v < edges.get(u).size(); ++v) {
            Edge curEdge = edges.get(u).get(v);
            if (level[curEdge.to] == level[u] + 1 && curEdge.flow < curEdge.capacity) {
                long newDelta = dfs(curEdge.to, Math.min(delta, curEdge.capacity - curEdge.flow));
                if (newDelta != 0) {
                    curEdge.flow += newDelta;
                    if (curEdge.hasBack) {
                        curEdge.back.flow -= newDelta;
                    }
                    return newDelta;
                }
            }
            ++deletedVertexes[u];
        }
        return 0;
    }

    boolean bfs() {
        Arrays.fill(level, INFINITY);
        level[s] = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(s);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (Edge e : edges.get(cur)) {
                if (e.flow < e.capacity && level[e.to] == INFINITY) {
                    level[e.to] = level[cur] + 1;
                    queue.add(e.to);
                }
            }
        }
        return level[t] != INFINITY;
    }

    void markDfs(int v, List<Integer> partS) {
        used[v] = true;
        partS.add(v);
        for (Edge edge : edges.get(v)) {
            if (edge.flow < edge.capacity && !used[edge.to]) {
                markDfs(edge.to, partS);
            }
        }
    }


    public void run() {
        in = new FastScanner();
        solve();
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

