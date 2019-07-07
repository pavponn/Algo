import java.util.*;
import java.io.*;
import java.lang.*;


public class TaskD {
    FastScanner in;
    PrintWriter out;

    public void solve() {
        long r1 = in.nextLong();
        long s1 = in.nextLong();
        long p1 = in.nextLong();
        long r2 = in.nextLong();
        long s2 = in.nextLong();
        long p2 = in.nextLong();
        long res = Math.max(r1 - r2 - p2, Math.max(p1 - p2 - s2, s1 -s2 - r2));
        res = Math.max(0, res);
        out.println(res);
    }


    public void run() {
        try {
            in = new FastScanner(new File("rps2.in"));
            out = new PrintWriter(new File("rps2.out"));
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
        new TaskD().run();
    }
}

