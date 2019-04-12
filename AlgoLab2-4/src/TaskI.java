import java.util.*;
import java.io.*;
import java.lang.*;


public class TaskI {
    FastScanner in;
    int n, t, s = 0;
    int[] level, deletedVertexes;
    long maxFlow = 0;
    long INFINITY = Integer.MAX_VALUE;
    boolean[] used = new boolean[1000000 + 5];
    char[][] result;
    int[] firstScores;
    int[] toGet;
    int[] possibleToGet;
    Map<Integer, Pair> map = new HashMap<>();

    class Pair {
        int fst;
        int snd;

        public Pair(int fst, int snd) {
            this.fst = fst;
            this.snd = snd;
        }
    }

    class Edge {
        int from, to;
        long flow = 0;
        long capacity;
        Edge reversed;
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
    List<List<Integer>> toPlay = new ArrayList<>();

    void addEdge(int from, int to, int capacity, int pos) {
        Edge e1 = new Edge(from, to, capacity, pos);
        Edge e2 = new Edge(from, to, 0, pos, false);
        e1.reversed = e2;
        e2.reversed = e1;
        edges.get(from).add(e1);
        edges.get(to).add(e2);
    }

    int matches = 0;
    int match = 101;

    public void solve() {
        n = in.nextInt();
        t = 10105 - 1;
        for (int i = 0; i < t + 1; ++i) {
            edges.add(new ArrayList<>());
            toPlay.add(new ArrayList<>());
        }
        result = new char[n][n];
        firstScores = new int[n];
        toGet = new int[n];
        possibleToGet = new int[n];
        level = new int[t + 1];
        deletedVertexes = new int[t + 1];

        for (int i = 0; i < n; ++i) {
            String curStr = in.next();
            result[i][i] = '#';
            for (int j = i+1; j < n; ++j) {
                result[i][j] = curStr.charAt(j);
                switch (result[i][j]) {
                    case 'W': {
                        result[j][i] = 'L';
                        firstScores[i] += 3;
                        firstScores[j] += 0;
                        break;
                    }
                    case 'L': {
                        result[j][i] = 'W';
                        firstScores[i] += 0;
                        firstScores[j] += 3;
                        break;
                    }
                    case 'w': {
                        result[j][i] = 'l';
                        firstScores[i] += 2;
                        firstScores[j] += 1;
                        break;
                    }
                    case 'l': {
                        result[j][i] = 'w';
                        firstScores[i] += 1;
                        firstScores[j] += 2;
                        break;
                    }
                    case '#': {
                        result[j][i] = '#';
                        break;
                    }
                    default: {
                        matches += 1;
                        addEdge(s, match, 3, 0);
                        addEdge(match, i + 1, 3, 0);
                        addEdge(match, j + 1, 3, 0);
                        map.put(match++, new Pair(i + 1, j + 1));
                        result[j][i] = '.';
//                        toPlay.get(i).add(j);
//                        toPlay.get(j).add(i);
                    }

                }
            }
        }

        for (int i = 0; i < n; ++i) {
            toGet[i] = in.nextInt() - firstScores[i];
        }

        for (int i = 0; i < n; ++i) {
            addEdge(i + 1, t, toGet[i], 0);
        }
//
//        for (int v = 0; v < n; ++v) {
//            possibleToGet[v] = 0;
//            for (int team : toPlay.get(v)) {
//                if (v < team) {
//                    possibleToGet[v] = possibleToGet[v] + 3;
//                }
//            }
//        }


        long fl = dinicAlgorithm();
        for (int i = 101; i < 101 + matches; ++i) {
            List<Edge> curEdges = edges.get(i);
            for (int j = 1; j < curEdges.size(); ++j) {
                Edge edge = curEdges.get(j);
                Pair g = map.get(i);
                if (edge.to == g.fst) {
                    result[g.fst - 1][g.snd - 1] = getSymbol(edge.flow);
                } else {
                    result[g.snd - 1][g.fst - 1] = getSymbol(edge.flow);
                }
            }
        }
//        for (int i = 0; i < n; ++i) {
//            for (Edge edge : edges.get(i + 1)) {
//                if (edge.to != s && i < edge.to - 1 && edge.to != t) {
//                    if (edge.flow == 3) {
//                        result[i][edge.to - 1] = 'L';
//                        result[edge.to - 1][i] = 'W';
//                    } else if (edge.flow == 0) {
//                        result[i][edge.to - 1] = 'W';
//                        result[edge.to - 1][i] = 'L';
//                    } else if (edge.flow == 2) {
//                        result[i][edge.to - 1] = 'l';
//                        result[edge.to - 1][i] = 'w';
//                    } else {
//                        result[i][edge.to - 1] = 'w';
//                        result[edge.to - 1][i] = 'l';
//                    }
//                }
//            }
//        }
        for (int i = 0; i < n; ++i) {
            System.out.println(new String(result[i]));
        }

    }

    public char getSymbol(long flow) {
        if (flow == 0) {
            return 'L';
        } else if (flow == 1) {
            return 'l';
        } else if (flow == 2) {
            return 'w';
        } else if (flow == 3) {
            return 'W';
        } else {
            throw new RuntimeException();
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
                    curEdge.reversed.flow -= newDelta;
                    return newDelta;
                }
            }
            ++deletedVertexes[u];
        }
        return 0;
    }

    boolean bfs() {
        Arrays.fill(level, (int) INFINITY);
        level[0] = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
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
        new TaskI().run();
    }
}

