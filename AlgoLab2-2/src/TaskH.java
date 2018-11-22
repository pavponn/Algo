import java.util.*;
import java.io.*;
import java.lang.*;

public class TaskH {
    FastScanner in;
    PrintWriter out;
    int n;
    int m;
    List<List<Pair>> graph = new ArrayList<>();
    long cost[];
    boolean used[];
    List<List<Pair>> edges = new ArrayList<>();
    Queue<Integer> queue = new ArrayDeque<>();
    class Pair implements Comparable<Pair> {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Pair o) {
            return Long.compare(this.x, o.x);
        }

    }

//    final long INF = (long)1e18;

    public void solve() {
        n = in.nextInt();
        m = in.nextInt();
        cost = new long[n];
        used = new boolean[n];
        Arrays.fill(used, false);
        for (int i = 0; i < n; i++) {
            cost[i] = in.nextLong();
            queue.add(i);
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int a = in.nextInt() - 1;
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            edges.get(x).add(new Pair(y, a));
            edges.get(y).add(new Pair(x, a));
        }
        while (!queue.isEmpty()) {
            int current = queue.poll();
            used[current] = true;
            for (Pair pair :edges.get(current)) {
                if (cost[pair.y] > cost[pair.x] + cost[current]) {
                    cost[pair.y] = cost[pair.x] + cost[current];
                    if (used[pair.y]) {
                        queue.add(pair.y);
                    }
                    used[pair.y] = false;
                }
            }
        }
        out.println(cost[0]);
    }



    public void run() {
          try {
//        in = new FastScanner();
        in = new FastScanner(new File("dwarf.in"));
         out = new PrintWriter(new File("dwarf.out"));
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