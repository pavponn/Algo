#include <iostream>
#include <map>
#include <vector>
#include <fstream>

class SuffixAutomata {
public:
    explicit SuffixAutomata(std::string& str) {
        last = 0;
        size = 1;
        states.resize(str.size() * 2 + 1);
        states[0].length = 0;
        states[0].link = -1;
        for (char i : str) {
            addCharacter(i);
        }
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
            length = 0;
            link = 0;
        }
    };

    std::vector<State> states;
    int size;
    int last;

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

    std::ifstream in("search4.in");
    std::ofstream out("search4.out");
    int n;
    in >> n;
    std::vector<std::string> strings;
    for (size_t i = 0; i < n; ++i) {
        std::string str;
        in >> str;
        strings.emplace_back(str);
    }
    std::string str;
    in >> str;

    SuffixAutomata suffixAutomata(str);
    for (auto string : strings) {
        if (suffixAutomata.containsSubstring(string)) {
            out << "YES\n";
        } else {
            out << "NO\n";
        }
    }

    return 0;
}