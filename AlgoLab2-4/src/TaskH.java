import java.util.*;
import java.io.*;


public class TaskH {
    FastScanner in;
    PrintWriter out;
    int n, m, k, s, t;
    int computersDelivered = 0;
    int[] begin;
    int[] end;
    int days = 1;
    long maxFlow = 0;
    long INFINITY = Integer.MAX_VALUE;
    boolean[] used = new boolean[1000000 + 5];

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

    void addEdge(int from, int to, long capacity, int pos) {
        Edge e1 = new Edge(from, to, capacity, pos);
        Edge e2 = new Edge(from, to, 0, pos, false);
        e1.back = e2;
        e2.back = e1;
        edges.get(from).add(e1);
        edges.get(to).add(e2);
    }

    List<List<Edge>> edges = new ArrayList<>();

    int getEdgeNumber(int day, int v) {
        return v + n * day;
    }

    public void solve() {
        for (int i = 0; i < 1000000; ++i) {
            edges.add(new ArrayList<Edge>());
        }
        n = in.nextInt();
        m = in.nextInt();
        k = in.nextInt();
        s = in.nextInt() - 1;
        t = in.nextInt() - 1;
        long[] answer = new long[k + 7];
        Arrays.fill(answer, s);
        begin = new int[m];
        end = new int[m];
        for (int i = 0; i < m; ++i) {
            int v1 = in.nextInt() - 1;
            int v2 = in.nextInt() - 1;
            begin[i] = v1;
            end[i] = v2;
        }
        while (k > computersDelivered) {
            for (int i = 0; i < n; ++i) {
                addEdge(getEdgeNumber(days - 1, i), getEdgeNumber(days, i), INFINITY, 0);
            }
            for (int i = 0; i < m; ++i) {
                addEdge(getEdgeNumber(days - 1, begin[i]), getEdgeNumber(days, end[i]), 1, 0);
                addEdge(getEdgeNumber(days - 1, end[i]), getEdgeNumber(days, begin[i]), 1, 0);
            }
            int curT = t;
            t = getEdgeNumber(days, t);
            maxFlow = 0;
            computersDelivered += calculateMaxFlow(getEdgeNumber(days++, n), k - computersDelivered);
            t = curT;
        }
        out.println(days - 1);


        for (int i = 1; i < days; ++i) {
            StringBuilder sb = new StringBuilder();
            long count = 0;
            for (int j = 1; j <= k; ++j) {
                for (Edge edge : edges.get((int)answer[j])) {
                    if (edge.flow > 0) {
                        edge.flow -= 1;
                        answer[j] = edge.to;
                        if (edge.capacity != INFINITY) {
                            ++count;
                            sb.append(" " + j + " " + (edge.to % n + 1));
                        }
                        break;
                    }
                }
            }
            out.print(count);
            out.println(sb.toString());
        }
    }

    public long calculateMaxFlow(int t, long needed) {
        while (true) {
            Arrays.fill(used, 0, t, false);
            long pushedFlow = pushFlow(s, needed - maxFlow);
            if (pushedFlow == 0) {
                break;
            }
            maxFlow += pushedFlow;
            if (maxFlow == needed) {
                break;
            }
        }
        return maxFlow;
    }

    public long pushFlow(int v, long delta) {
        if (v == t) {
            return delta;
        }
        used[v] = true;
        for (Edge to : edges.get(v)) {
            if (to.capacity > to.flow && !used[to.to]) {
                long newDelta = pushFlow(to.to,  Math.min(delta, to.capacity - to.flow));
                if (newDelta > 0) {
                    to.flow += newDelta;
                    to.back.flow -= newDelta;
                    return newDelta;
                }
            }
        }
        return 0;
    }

    public void run() {
        try {
            in = new FastScanner(new File("bring.in"));
            out = new PrintWriter(new File("bring.out"));
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

        double nextDouble() {
            return Double.parseDouble(next());
        }

    }


    public static void main(String[] args) {
        new TaskH().run();
    }
}

