#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <string>
#include <map>

const size_t ALPHABET_SIZE = 60;
const unsigned int MAX_SIZE = 2000000 + 50;


struct Node {
    Node* parent;
    Node* suffixLink;
    Node* shortSuffixLink;
    
    Node* links[ALPHABET_SIZE]{};
    Node* next[ALPHABET_SIZE]{};
    
    
    std::vector<int> numbers;
    
    bool isLeaf;
    char letterFromParent{};
    int count, left, right;
    
    
    Node() :
    left(MAX_SIZE), right(-1), count(0), isLeaf(false), parent(nullptr), suffixLink(nullptr),
    shortSuffixLink(nullptr) {}
};


Node rootNode;
std::map<Node*, bool> used;

Node* next(Node* currentNode, char ch);
Node* getLink(Node* currentNode);

int getCharacterCode(char ch) {
    return ch - 'A';
}

void addString(std::string& str, int numberOfString) {
    Node* currentNode = &rootNode;
    
    for (auto ch: str) {
        int i = getCharacterCode(ch);
        if (!currentNode->next[i]) { ///
            currentNode->next[i] = new Node();
            currentNode->next[i]->letterFromParent = ch;
            currentNode->next[i]->parent = currentNode;
            
        }
        
        currentNode = currentNode->next[i];
    }
    currentNode->isLeaf = true;
    currentNode->numbers.push_back(numberOfString);
}


Node* next(Node* currentNode, char ch) {
    auto symbol = getCharacterCode(ch);
    if (!currentNode->links[symbol]) {
        
        if (currentNode->next[symbol]) {
            currentNode->links[symbol] = currentNode->next[symbol];
        } else if (currentNode == &rootNode) {
            currentNode->links[symbol] = &rootNode;
        } else {
            currentNode->links[symbol] = next(getLink(currentNode), ch);
        }
        
    }
    getLink(currentNode->links[symbol]);
    
    return currentNode->links[symbol];
}


Node* getLink(Node* currentNode) {
    if (!currentNode->suffixLink) {
        if (currentNode->parent == &rootNode || currentNode == &rootNode)
            currentNode->suffixLink = &rootNode;
        else {
            currentNode->suffixLink = next(getLink(currentNode->parent), currentNode->letterFromParent);
            currentNode->shortSuffixLink = currentNode->suffixLink->isLeaf ? currentNode->suffixLink
            : currentNode->suffixLink->shortSuffixLink;
        }
        
    }
    return currentNode->suffixLink;
}



void dfs(Node* node, std::vector<std::pair<int, int>>& answer) {
    used[node] = true;
    for (auto toNode: node->links) {
        if (toNode && !used[toNode]) {
            Node* currentNode = toNode;
            int left = toNode->left;
            int right = toNode->right;
            if (toNode->count) {
                while (currentNode && currentNode != &rootNode) {
                    if (currentNode->isLeaf) {
                        for (auto num: currentNode->numbers) {
                            answer[num].first = std::min(answer[num].first, left);
                            answer[num].second = std::max(answer[num].second, right);
                        }
                    }
                    currentNode = currentNode->shortSuffixLink;
                }
            }
            dfs(toNode, answer);
        }
    }
}

void updateOccurrences(Node* currentNode, int index) {
    currentNode->left = std::min(currentNode->left, index);
    currentNode->right = std::max(currentNode->right, index);
}


int main() {
    std::ios_base::sync_with_stdio(false);
    
    size_t n;
    std::string t;
    std::vector<int> strings;
    
    std::pair<int, int> defaultPair(MAX_SIZE, -1);
    std::vector<std::pair<int, int>> answer;
    
    
    std::ifstream in("search6.in");
    std::ofstream out("search6.out");
    
    in >> n;
    
    for (size_t i = 0; i < n; ++i) {
        std::string cur;
        in >> cur;
        strings.push_back((int) cur.length());
        addString(cur, static_cast<int>(i));
    }
    
    answer.assign(n, defaultPair);
    
    in >> t;
    
    Node* currentNode = &rootNode;
    for (int i = 0; i < t.length(); ++i) {
        currentNode = next(currentNode, t[i]);
        currentNode->count = 1;
        updateOccurrences(currentNode, i);
    }
    
    dfs(&rootNode, answer);
    
    std::string outString;
    
    for (size_t i = 0; i < n; ++i) {
        if (answer[i].first == MAX_SIZE) {
            outString += "-1";
        } else {
            outString += std::to_string(answer[i].first - strings[i] + 1);
        }
        outString += " ";
        
        if (answer[i].second == -1) {
            outString += "-1";
        } else {
            outString += std::to_string(answer[i].second - strings[i] + 1);
        }
        outString += "\n";
    }
    out << outString;
    return 0;
}
