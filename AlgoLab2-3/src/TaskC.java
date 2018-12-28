import java.util.*;
import java.io.*;
import java.lang.*;

public class TaskC {
    FastScanner in;


    public void solve() {
        String str = in.next();
        int [] z = zFunction(str);
        StringBuilder builder = new StringBuilder();
        builder.append(z[1]);
        for (int i = 2; i < z.length; ++i) {
            builder.append(" " + z[i]);
        }
        System.out.println(builder.toString());
    }

    public int [] zFunction(String str) {
        int [] z = new int[str.length()];
        int l = 0;
        int r = 0;
        for (int i = 1; i < str.length(); ++i) {
            if (i <= r) {
                z[i] = Math.min(r - i + 1, z[i - l]);
            }
            while (z[i] + i < str.length() && str.charAt(z[i]) == str.charAt(z[i] + i)) {
                ++z[i];
            }
            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }

        }
        return z;
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
        new TaskC().run();
    }
}
