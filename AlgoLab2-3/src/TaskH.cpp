#include <iostream>
#include <vector>
#include <queue>
#include <stack>
#include <string>
#include <cmath>
#include <algorithm>
#include <set>
#include <map>
#include <fstream>
#include <unordered_map>
#include <unordered_set>




struct State {
    int length, link;
    std::map <char, int> next;
};


int size;
int last;
int countOccurrences[2000000 + 10];
State states[2000000 + 10];

std::vector<std::vector<int>> sortedStates;

void initialize() {
    size = 1;
    last = 0;
    states[0].length = 0;
    states[0].link = -1;
}

void addCharacter(char ch) {
    int cur = size++;
    countOccurrences[cur] = 1;
    states[cur].length = states[last].length + 1;
    int p;
    for (p = last; p != -1 && !states[p].next.count(ch); p = states[p].link) {
        states[p].next[ch] = cur;
    }
    if (p == -1)
        states[cur].link = 0;
    else {
        int q = states[p].next[ch];
        if (states[p].length + 1 == states[q].length) {
            states[cur].link = q;
        }
        else {
            int clone = size++;
            states[clone].length = states[p].length + 1;
            states[clone].next = states[q].next;
            states[clone].link = states[q].link;
            for (; p != -1 && states[p].next[ch] == q; p = states[p].link) {
                states[p].next[ch] = clone;
            }
            states[q].link  = clone;
            states[cur].link = clone;
        }
    }
    last = cur;
}

void calculate() {
    sortedStates.assign(states[last].length + 1, std::vector <int>());
    for (int i = 0; i < last + 1; ++i) {
        sortedStates[states[i].length].push_back(i);
    }
    for (int i = sortedStates.size() - 1; i != 0; --i) {
        for (int j = 0; j < sortedStates[i].size(); ++j) {
            countOccurrences[states[sortedStates[i][j]].link] += countOccurrences[sortedStates[i][j]];
        }
    }
}

int findOccur(const std::string& str) {
    int cur = 0;
    
    for (char j : str) {
        if (states[cur].next.count(j) == 1) {
            cur = states[cur].next[j];
        }
        else {
            return 0;
        }
    }
    return countOccurrences[cur];
}

int main() {
    
    freopen("search5.in", "r", stdin);
    freopen("search5.out", "w", stdout);
    int n;
    
    scanf("%d\n", &n);
    std::vector<std::string> strings;
    for (int i = 0; i < n; ++i) {
        std::string s;
        char c;
        scanf("%c", &c);
        while (c != '\n') {
            s += c;
            scanf("%c", &c);
        }
        strings.push_back(s);
    }
    initialize();
    char c;
    while (scanf("%c", &c) == 1) {
        addCharacter(c);
    }
    calculate();
    for (int i = 0; i < strings.size(); ++i) {
        printf("%d\n" ,findOccur(strings[i]));
    }
    return 0;
}
