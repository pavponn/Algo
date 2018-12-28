import java.util.*;
import java.io.*;
import java.lang.*;
/*
    Tl29, C++ is ok
 */
public class TaskG {
    FastScanner in;
    PrintWriter out;

    public void solve() {
        int n = in.nextInt();
        String[] strings = new String[n];
        for (int i = 0; i < n; ++i) {
            strings[i] = in.next();
        }
        String str = in.next();
        SuffixAutomata suffixAutomata = new SuffixAutomata(str);
        for (String string : strings) {
            if (suffixAutomata.containsSubstring(string)) {
                out.println("YES");
            } else {
                out.println("NO");
            }
        }
    }

    class SuffixAutomata {
        private State[] states;
        private int size;
        private int last;

        private class State {
            int length;
            int link;
            Map<Character, Integer> next;

            State() {
                next = new HashMap<>();
                length = 0;
                link = 0;
            }
        }


        public SuffixAutomata(String str) {
            states = new State[str.length() * 2 + 1];
            Arrays.setAll(states, i -> new State());

            last = 0; size = 1;
            states[0].length = 0; states[0].link = -1;

            for (int i = 0; i < str.length(); ++i) {
                addCharacter(str.charAt(i));
            }
        }


        public boolean containsSubstring(String t) {
            if (size < t.length()) {
                return false;
            }
            int cur = 0;
            for (int i = 0; i < t.length(); ++i) {
                Integer go = states[cur].next.get(t.charAt(i));
                if (go == null) {
                    return false;
                }
                cur = go;
            }
            return true;
        }

        private void addCharacter(Character ch) {
            int cur = size++;
            states[cur].length = states[last].length + 1;
            int p = last;
            for (; p != -1 && !states[p].next.containsKey(ch); p = states[p].link) {
                states[p].next.put(ch, cur);
            }
            if (p == -1) {
                states[cur].link = 0;
            } else {
                int q = states[p].next.get(ch);
                if (states[p].length + 1 == states[q].length) {
                    states[cur].link = q;
                } else {
                    int clone = size++;
                    states[clone].length = states[p].length + 1;
                    states[clone].next = new HashMap<Character, Integer>(states[q].next);
                    states[clone].link = states[q].link;
                    for (; p != -1 && states[p].next.get(ch) == q; p = states[p].link) {
                        states[p].next.put(ch, clone);
                    }
                    states[q].link = states[cur].link = clone;
                }

            }

            last = cur;
        }
    }


    public void run() {
        try {
            in = new FastScanner(new File("search4.in"));
            out = new PrintWriter(new File("search4.out"));
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
        new TaskG().run();
    }
}