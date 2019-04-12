import java.util.*;
import java.io.*;
import java.lang.*;


public class TaskE {
    FastScanner in;
    int n, m;
    long INFINITY = Long.MAX_VALUE;
    int[] matching;


    class Edge {
        int  to, x, y;
        long flow = 0;
        Edge reversedEdge;

        Edge(int to, int x, int y) {
            new Edge(to, x, y, 0);
        }

        Edge(int to, int x, int y, long flow) {
            this.to = to;
            this.x = x;
            this.y = y;
            this.flow = flow;
        }
    }

    void addEdges(int from, int to, int i, int j) {
        Edge e1 = new Edge(to, i, j, 1);
        Edge e2 = new Edge(from, i, j, 0);
        e1.reversedEdge = e2;
        e2.reversedEdge = e1;

        edges.get(from).add(e1);
        edges.get(to).add(e2);

    }


    class Tern {
        int t, x, y;

        public Tern(int t, int x, int y) {
            this.t = t;
            this.x = x;
            this.y = y;
        }
    }


    final static int MAX_SZ = 880;
    List<List<Edge>> edges = new ArrayList<>();
    int[][] board = new int[110][110];
    boolean[] used = new boolean[MAX_SZ];
    int sz;

    public void solve() {
        n = in.nextInt();
        m = in.nextInt();
        sz = n + m;
        String cur;
        for (int i = 0; i < n; ++i) {
            cur = in.next();
            for (int j = 0; j < m; ++j) {
                if (cur.charAt(j) == 'W') {
                    board[i][j] = (i + j) % 2;
                } else {
                    board[i][j] = Math.abs((i + j) % 2 - 1);
                }
            }
        }
        Tern[] result;
        int type;
        Tern[] resWB = getRes();
        changeColours();
        Tern[] resBW = getRes();
        if (resWB.length >= resBW.length) {
            result = resBW;
            type = 1;
        } else {
            result = resWB;
            type = 0;
        }

        System.out.println(result.length);
        for (int i = 0; i < result.length; ++i) {
            char ch = ((result[i].x + result[i].y) % 2 == type) ? 'W' : 'B';
            System.out.println(result[i].t + " " + result[i].x + " " + result[i].y + " " + ch);
        }
    }

    void createEdgeList() {
        edges.clear();
        for (int i = 0; i < MAX_SZ; ++i) {
            edges.add(new ArrayList<>());
        }
    }

    Tern[] getRes() {
        int result = 0;
        createEdgeList();
        for (int i = 1; i < 2 * sz; ++i) {
            addEdges(0, i, i + 1, i + 1);
        }
        for (int i = (2 * sz); i < (4 * sz); ++i) {
            addEdges(i, 4 * sz, i + 1, i + 1);
        }


        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (board[i][j] == 1) {
                    addEdges(i - j + sz, i + j + 2 * sz, i + 1, j + 1);
                }
            }
        }

        while (true) {
            used = new boolean[MAX_SZ];
            if (!kuhn(0)) {
                break;
            }
            result += 1;
        }

        Tern[] res = new Tern[result];
        for (int i = 0; i < result; ++i) {
            res[i] = new Tern(0, 0, 0);
        }

        int ind = 0;

        for (int i = 0; i < 2 * sz; ++i) {
            if (!used[i]) {
                for (Edge edge : edges.get(i)) {
                    if (edge.to != 0) {
                        res[ind] = new Tern(2, edge.x, edge.y);
                        ++ind;
                        break;
                    }
                }
            }
        }
        for (int i = 2 * sz; i < 4 * sz; ++i) {
            if (used[i]) {
                for (Edge edge : edges.get(i)) {
                    if (edge.to != 4 * sz) {
                        res[ind++] = new Tern(1, edge.x, edge.y);
                        break;
                    }
                }
            }
        }
        return res;
    }

    void changeColours() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                board[i][j] = 1 - board[i][j];
            }
        }
    }


    boolean kuhn(int v) {
        if (v == 4 * sz) {
            return true;
        }
        used[v] = true;
        for (Edge edge : edges.get(v)) {
            if (edge.flow == 1 && !used[edge.to] && kuhn(edge.to)) {
                edge.reversedEdge.flow = 1;
                edge.flow = 0;
                return true;
            }
        }
        return false;
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
        new TaskE().run();
    }
}

