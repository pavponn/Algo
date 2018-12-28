#include <iostream>
#include <map>
#include <vector>
#include <fstream>

class SuffixAutomata {
public:
    explicit SuffixAutomata(std::string& str) {
        last = 0;
        size = 1;
        states.resize(str.size() * 2);
        d.resize(str.size() * 2);
        states[0].length = 0;
        states[0].link = -1;
        for (char i : str) {
            addCharacter(i);
        }
    }

    long long countSubstrings(int v = 0) {
        long long result = 0;
        if (d[v] != 0) {
            return d[v];
        }
        for (auto& it : states[v].next) {
            result += countSubstrings(it.second);
        }

        d[v] = result + 1;
        return d[v];
    }

    bool containsSubstring(std::string& t) {
        if (size < t.length()) {
            return false;
        }

        int cur = 0;
        for (char i : t) {
            if (!states[cur].next.count(i)) {
                return false;
            }
            cur = states[cur].next[i];
        }
        return true;
    }
private:
    struct State {
        int length = 0;
        int link = 0;
        std::map<char, int> next;
        State() {
            length = 0; link = 0;
        }
    };

    std::vector<State> states;
    int size;
    int last;
    std::vector<long long> d;
    void addCharacter(char ch) {
        int cur = size++;
        states[cur].length = states[last].length + 1;
        int p = last;
        for (; p != -1 && !states[p].next.count(ch); p = states[p].link) {
            states[p].next[ch] = cur;
        }
        if (p == -1) {
            states[cur].link = 0;
        } else {
            int q = states[p].next[ch];
            if (states[p].length + 1 == states[q].length) {
                states[cur].link = q;
            } else {
                int clone = size++;
                states[clone].length = states[p].length + 1;
                states[clone].next = states[q].next;
                states[clone].link = states[q].link;
                for (; p != -1 && states[p].next[ch] == q; p = states[p].link) {
                    states[p].next[ch] = clone;
                }
                states[q].link = clone;
                states[cur].link = clone;
            }

        }
        last = cur;
    }
};

int main() {
    std::ios_base::sync_with_stdio(false);

    std::ifstream in("count.in");
    std::ofstream out("count.out");
    std::string str;

    in >> str;

    SuffixAutomata suffixAutomata(str);
    out << suffixAutomata.countSubstrings() - 1 << std::endl;

    return 0;
}
