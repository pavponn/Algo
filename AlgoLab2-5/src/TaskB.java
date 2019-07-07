import java.util.*;
import java.io.*;
import java.lang.*;


public class TaskB {
    FastScanner in;
    PrintWriter out;
    final static int INFINITY = Integer.MAX_VALUE - 10;
    List<ArrayList<Integer>> edges = new ArrayList<>();
    List<ArrayList<Integer>> children = new ArrayList<>();
    List<ArrayList<Integer>> table = new ArrayList<>();
    int [][][] dp = new int[1010][40][40];
    boolean [] dfsUsed = new boolean[1010];

    int[][] matrix;
    int n, k, p;


    public void solve() {
        n = in.nextInt();
        k = in.nextInt();
        p = in.nextInt();
        for (int i = 0; i < 1012; i++) {
            ArrayList<Integer> toAdd = new ArrayList<>();
            for (int k = 0; k < 42; k++) {
                toAdd.add(0);
            }
            table.add(toAdd);
            edges.add(new ArrayList<>());
            children.add(new ArrayList<>());
        }

        matrix = new int[n + 1][n + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < k + 1; j++) {
                matrix[i][j] = in.nextInt();
            }
        }


        for (int i = 0; i < n - 1; i++) {
            int from = in.nextInt();
            int to = in.nextInt();
            edges.get(from).add(to);
            edges.get(to).add(from);
        }
    }
    int dfs (int v) {
        int minCost = INFINITY;
        for (int to: children.get(v)) {
            dfs(to);
        }

        for (int prevColor = 1; prevColor < k + 1; prevColor++) {
            for (int color = 1; color < k + 1; color++) {
                if (!children.get(v).isEmpty()) {
                    dp[v][color][prevColor] = p + matrix[v][color];
                }

                for (int child: children.get(v)) {
                    int childCost = INFINITY;
                    for (int childCol = 1; childCol < k + 1; childCol++) {
                        childCost = Math.min(childCost, dp[child][childCol][color]);
                    }
                    dp[v][color][prevColor] += childCost;
                }

                if (!children.get(v).isEmpty() && children.get(v).size() < k || children.get(v).size() < k + 1 && v == 1) {
                    for (int i = 0; i < children.get(v).size(); i++) {
                        for (int childCol = 1; childCol < k + 1; childCol++){
                            if (childCol == prevColor && v != 1) {
                                table.get(i + 1).set(childCol, INFINITY);
                            } else {
                                table.get(i + 1).set(childCol, dp[children.get(v).get(i)][childCol][color]);
                            }
                        }
                    }
                    dp[v][color][prevColor] = Math.min(hungryAlgorithm(table, children.get(v).size(), k).fst + matrix[v][color],
                            dp[v][color][prevColor]);
                }
                minCost = Math.min(minCost, dp[v][color][prevColor]);
            }
        }
        return minCost;
    }
    void normalize(int v) {
        dfsUsed[v] = true;
        for (int to: edges.get(v)) {
            if (!dfsUsed[to]) {
                children.get(v).add(to);
                normalize(to);
            }
        }
        if (children.get(v).isEmpty()) {
            for (int c = 1; c < k + 1; c++) {
                for (int p = 0; p < k + 1; p++) {
                    dp[v][c][p] = matrix[v][c];
                }
            }
        }
    }



    Pair hungryAlgorithm(List<ArrayList<Integer>> matrix, int n, int m) {
        int[] u = new int[n + 1];
        int[] v = new int[m + 1];
        int[] matching = new int[m + 1];
        int[] way = new int[m + 1];


        for (int r = 1; r < n + 1; r++) {
            matching[0] = r;
            int col = 0;
            int[] minV = new int[m + 1];
            boolean[] used = new boolean[m + 1];
            Arrays.fill(minV, INFINITY);

            do {
                used[col] = true;
                int curRow = matching[col];
                int curCol = 0;
                int delta = INFINITY;

                for (int j = 1; j < m + 1; j++) {
                    if (!used[j]) {
                        int cur = matrix.get(curRow).get(j) - u[curRow] - v[j];
                        if (cur < minV[j]) {
                            minV[j] = cur;
                            way[j] = col;
                        }
                        if (minV[j] < delta) {
                            delta = minV[j];
                            curCol = j;
                        }
                    }
                }
                for (int j = 0; j < m + 1; j++) {
                    if (used[j]) {
                        u[matching[j]] += delta;
                        v[j] -= delta;
                    } else {
                        minV[j] -= delta;
                    }
                }
                col = curCol;

            } while (matching[col] != 0);

            do {
                int j0 = way[col];
                matching[col] = matching[j0];
                col = j0;
            } while (col > 0);
        }
        int[] result = new int[n + 1];
        for (int j = 1; j < m + 1; j++) {
            result[matching[j]] = j;
        }
        return new Pair(-v[0], result);
    }

    class Pair {
        int fst;
        int[] snd;

        public Pair(int fst, int[] snd) {
            this.fst = fst;
            this.snd = snd;
        }
    }

    public void run() {
        try {
            in = new FastScanner(new File("assignment.in"));
            out = new PrintWriter(new File("assignment.out"));
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
        new TaskB().run();
    }
}

