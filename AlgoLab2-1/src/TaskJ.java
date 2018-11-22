import java.io.*;
import java.util.*;

public class TaskJ {
    FastScanner in;
    int n, m;
    long answer = 0;
    Edge[] edges;
    int[] parent;
    int [] rank;
    class Edge {
        int firstVertex;
        int secondVertex;
        int weight;

        public Edge(int firstVertex, int secondVertex, int weight) {
            this.firstVertex = firstVertex;
            this.secondVertex = secondVertex;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return firstVertex + " " + secondVertex + " " + weight;
        }
    }


    void unionDSU (int a, int b) {
        a = getRootDSU(a);
        b = getRootDSU(b);
        if (a == b) {
            return;
        }
        Random rand = new Random();
        int code = rand.nextInt(1000);

        if (code % 2 == 1) {
            int t = b;
            b = a;
            a = t;
        }

        parent[b] = a;
//        if (rank[a] == rank[b]) {
//            rank[a]++;
//        }
    }


    int getRootDSU(int a) {
        if (a != parent[a]) {
            parent[a] = getRootDSU(parent[a]);
        }
        return parent[a];
    }

    void makeDSU() {
        for (int i = 0; i < n; i++) {
            parent[i] = i;
//            rank[i] = 0;
        }
    }
    public void solve() {
        n = in.nextInt();
        m = in.nextInt();
        edges = new Edge[m];
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < m; i++) {
            edges[i] = new Edge(in.nextInt() - 1, in.nextInt() - 1, in.nextInt());
        }
        Arrays.sort(edges, (a, b) -> a.weight < b.weight ? -1 : 0);
        for (Edge e: edges) {
            System.out.println(e);
        }
        makeDSU();
        for (int i = 0; i < m; i++) {
            if (getRootDSU(edges[i].firstVertex) != getRootDSU(edges[i].secondVertex)) {
                answer += edges[i].weight;
                unionDSU(edges[i].firstVertex, edges[i].secondVertex);
            }
        }
        System.out.println(answer);
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
        new TaskJ().run();
    }
}


