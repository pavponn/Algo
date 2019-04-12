import java.util.*;
import java.io.*;
import java.lang.*;


public class TaskD {
    FastScanner in;
    int[] level, deletedVertexes;
    boolean[] used;
    int n, m, t, s;
    long INFINITY = Long.MAX_VALUE;
    int[] matching;
    List<List<Integer>> edges = new ArrayList<>();

    public void solve() {
        int n = in.nextInt();
        int m = in.nextInt();
        for (int i = 0; i < n; ++i) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < n; ++i) {
            int num = in.nextInt();
            while (num != 0) {
                edges.get(i).add(num - 1);
                num = in.nextInt();
            }
        }

        matching = new int[m];
        Arrays.fill(matching, -1);
        for (int i = 0; i < n; ++i) {
            used = new boolean[n];
            kuhn(i);
        }
        long size = Arrays.stream(matching).parallel().filter(x -> x != -1).count();
        System.out.println(size);
        for (int i = 0; i < m; ++i) {
            if (matching[i] != -1) {
                System.out.println((matching[i] + 1) + " " + (i + 1));
            }
        }


    }

    boolean kuhn(int v) {
        if (used[v]) {
            return false;
        }
        used[v] = true;
        for (int to : edges.get(v)) {
            if (matching[to] == -1 || kuhn(matching[to])) {
                matching[to] = v;
                return true;
            }
        }
        return false;
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
        new TaskD().run();
    }
}

