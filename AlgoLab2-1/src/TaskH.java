import java.util.*;
import java.io.*;
import java.lang.*;

public class TaskH {
    FastScanner in;
    PrintWriter out;
    int[][] graph;
    int n;
    int mid;
    int right = 1000000001;
    int left = 0;
    boolean[] used;
    boolean accessed = true;

    public void solve() {

        n = in.nextInt();
        graph = new int[n][n];
        used = new boolean[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = in.nextInt();
            }
        }

        while (right > left) {
            mid = (right + left) / 2;
            dfs(0);
            if (check()) {
                reset();
                dfs2(0);
                if (check()) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            } else {
                left = mid + 1;
            }
            reset();
        }
        out.println(left);


    }

    void reset() {
        for (int i = 0; i < n; i++) {
            used[i] = false;
        }
    }

    boolean check() {
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                return false;
            }
        }
        return true;
    }

    void dfs(int v) {
        used[v] = true;
        for (int i = 0; i < n; i++) {
            if (mid >= graph[v][i] && !used[i]) {
                dfs(i);
            }
        }
    }

    void dfs2(int v) {
        used[v] = true;
        for (int i = 0; i < n; i++) {
            if (mid >= graph[i][v] && !used[i]) {
                dfs2(i);
            }
        }
    }


    public void run() {
        try {
//        in = new FastScanner();
            in = new FastScanner(new File("avia.in"));
            out = new PrintWriter(new File("avia.out"));
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
        new TaskH().run();
    }
}

