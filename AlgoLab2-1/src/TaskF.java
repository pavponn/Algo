//import java.io.*;
//import java.util.*;
//
//public class TaskF {
//    FastScanner in;
//    int n, m;
//    int currentColor = 0;
//
//    List<List<Pair>> graph;
//    List<List<Pair>> graphTran;
//    List<List<Integer>> table;
//    boolean[] used;
//    long[] weight;
//    long[] minEdge;
//    List<Integer> list, component;
//
//
//    class Pair {
//        int vertex;
//        int number;
//
//        Pair(int vertex, int number) {
//            this.vertex = vertex;
//            this.number = number;
//        }
//    }
//
//    public void solve() {
//
//        n = in.nextInt();
//        m = in.nextInt();
//
//        graph = new ArrayList<List<Pair>>(n);
//        graphTran = new ArrayList<List<Pair>>(n);
//
//        list = new ArrayList<Integer>(n);
//
//        weight = new long[m];
//        used = new boolean[n];
//
//        for (int i = 0; i < n; i++) {
//            graph.add(new ArrayList<Pair>());
//            graphTran.add(new ArrayList<Pair>());
//            used[i] = false;
//        }
//
//        for (int i = 0; i < m; i++) {
//            int x, y;
//            x = in.nextInt() - 1;
//            y = in.nextInt() - 1;
//            weight[i] = in.nextInt();
//            graph.get(x).add(new Pair(y, i));
//            graphTran.get(y).add(new Pair(x, i));
//
//        }
//
//        if (!check(0, false)) {
//            System.out.println("NO");
//            return;
//        }
//        System.out.println("YES");
//        long result = findMinSpanTree(0, -1);
//        System.out.println(result);
//
//    }
//
//    private long findMinSpanTree(int start, int oldComp) {
//        long res = 0;
//        minEdge = new long[graphTran.size()];
//        for (int i = 0; i < minEdge.length; i++) {
//            minEdge[i] = Long.MAX_VALUE;
//        }
//        for (int i = 0; i < graphTran.size(); i++) {
//            if (i == start) {
//                continue;
//            }
//            for (int j = 0; j < graphTran.get(i).size(); j++) {
//                minEdge[i] = Math.min(minEdge[i], weight[graphTran.get(i).get(j).number]);
//            }
//            for (int j = 0; j < graphTran.get(i).size(); j++) {
//                weight[graphTran.get(i).get(j).number] -= minEdge[i];
//            }
//            res = res + minEdge[i];
//        }
//        if (check(start, true)) {
//            return res;
//        }
//        currentColor = 0;
//        list.clear();
//        used = new boolean[graph.size()];
//        component = new ArrayList<>();
//        for (int i = 0; i < graph.size(); i++) {
//            component.set(i, -1);
//            used[i] = false;
//        }
//        ///Конденсация////
//        for (int i = 0; i < graph.size(); i++) {
//            if(!used[i]) {
//                dfs1(i);
//            }
//        }
//
//        used = new boolean[graph.size()];   ////Надо ли?
//        for (int i = 0; i < graph.size(); i++) {
//            used[i] = false;
//        }
//
//        for (int i = 0; i < graph.size(); i++) {
//            int v = list.get(graph.size() - i - 1);
//            if (!used[v]) {
//                dfs2(v);
//                currentColor++;
//            }
//        }
//
//        table = new ArrayList<List<Integer>>(currentColor);
//        for (int i = 0; i < currentColor; i++) {
//            table.add(new ArrayList<Integer>(currentColor));
//            for (int j = 0; j < currentColor; j++) {
//                table.get(i).set(j, -1);
//            }
//        }
//
//        for (int i = 0; i < graph.size(); i++) {
//            for (int j = 0; j < graph.get(i).size(); j++) {
//                int v = component.get(i);
//                int u = component.get(graph.get(i).get(j).vertex);
//                if (u != v) {
//                    int x = table.get(v).get(u);
//                    if (x == -1 || weight[x] > weight[graph.get(i).get(j).number]) {
//                        table.get(v).set(u, graph.get(i).get(j).number);
//                    }
//                }
//            }
//        }
//
//        graph = new ArrayList<List<Pair>>(currentColor);
//        graphTran = new ArrayList<List<Pair>>(currentColor);
//        for (int i = 0; i  < currentColor; i++) {
//            graph.add(new ArrayList<Pair>());
//            graphTran.add(new ArrayList<Pair>());
//        }
//
//        for (int i = 0; i < currentColor; i++) {
//            for (int j = 0 ; j < currentColor; j++) {
//                if (table.get(i).get(j) != -1) {
//                    graph.get(i).add(new Pair(j, table.get(i).get(j)));
//                    graphTran.get(j).add(new Pair(i, table.get(i).get(j)));
//                }
//            }
//        }
//        if (currentColor == oldComp) {
//            return 1;
//        }
//        return (res + findMinSpanTree(component.get(start), currentColor));
//
//    }
//
//    private void dfs2(int v) {
//        used[v] = true;
//        for (Pair pair: graphTran.get(v)) {
//            if (!used[pair.vertex] && weight[pair.number] == 0) {
//                dfs2(pair.vertex);
//            }
//        }
//        component.set(v, currentColor);
//    }
//
//    private void dfs1(int v) {
//        used[v] = true;
//        for (Pair pair: graph.get(v)) {
//            if (!used[pair.vertex] && weight[pair.number] == 0) {
//                dfs1(pair.vertex);
//            }
//        }
//        list.add(v);
//    }
//
//    private boolean check(int start, boolean ifZero) {
//        for (int i = 0; i < graph.size(); i++) {
//            used[i] = false;
//        }
//        dfs(start, ifZero);
//        for (int i = 0; i < graph.size(); i++) {
//            if (!used[i]) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private void dfs(int v, boolean ifZero) {
//        used[v] = true;
//        for (Pair pair : graph.get(v)) {
//            int first = pair.vertex;
//            int second = pair.number;
//            if (ifZero) {
//                if (!used[first] && weight[second] == 0) {
//                    dfs(first, ifZero);
//                }
//            } else {
//                if (!used[first]) {
//                    dfs(first, ifZero);
//                }
//            }
//        }
//    }
//
//
//    public void run() {
////          try {
//
//        in = new FastScanner();
////        in = new FastScanner(new File("input.txt"));
//        // out = new PrintWriter(new File("output.txt"));
//        solve();
//
////            out.close();
//
////        } catch (IOException e) {
////              e.printStackTrace();
////          }
//    }
//
//
//    class FastScanner {
//        BufferedReader br;
//        StringTokenizer st;
//        String str;
//
//        FastScanner() {
//            br = new BufferedReader(new InputStreamReader(System.in));
//        }
//
//        FastScanner(File f) {
//            try {
//                br = new BufferedReader(new FileReader(f));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        String next() {
//            while (st == null || !st.hasMoreTokens()) {
//
//
//                try {
//                    str = br.readLine();
//                    if (str == null) {
//                        return "";
//                    } else {
//                        st = new StringTokenizer(str);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return st.nextToken();
//        }
//
//        int nextInt() {
//            return Integer.parseInt(next());
//        }
//
//        long nextLong() {
//            return Long.parseLong(next());
//        }
//
//    }
//
//
//    public static void main(String[] args) {
//        new TaskF().run();
//    }
//}
//
//
