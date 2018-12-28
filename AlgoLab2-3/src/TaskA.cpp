#include <iostream>
#include <vector>


std::vector<long long> prefixHashes;
long p = 31;
std::vector<long long> powers(100200);

void countPowers() {
    powers[0] = 1;
    for (size_t i = 1; i < powers.size(); ++i) {
        powers[i] = (powers[i - 1] * p);
    }
}
void countPrefixHashes(std::string str) {
    prefixHashes[0] = (str[0] - 'a' + 1) * powers[0];
    for (size_t i = 1; i < str.length(); ++i) {
        prefixHashes[i] = (str[i] - 'a' + 1) * powers[i];
        prefixHashes[i] += prefixHashes[i - 1];
    }

}
long long getHash(int l, int r) {
    long long hash = prefixHashes[r];
    if (l != 0) {
        hash -= prefixHashes[l - 1];
    }
    return hash;
}

int main() {
    std::string str;
    std::cin >> str;

    prefixHashes.resize(str.size());
    countPowers();
    countPrefixHashes(str);

    int m;
    std::cin >> m;

    for (int i = 0; i < m; ++i) {
        unsigned int a, b, c, d;
        std::cin >> a >> b >> c >> d;
        --a;
        --b;
        --c;
        --d;
        if (a > c) {
            std::swap(a, c);
            std::swap(b, d);
        }
        long long hash1 = getHash(a, b);
        long long hash2 = getHash(c, d);
        if ((b - a) != (d - c)) {
            std::cout << "No" << std::endl;
        } else if (hash1 * powers[c - a] == hash2) {
            std::cout << "Yes" << std::endl;
        } else {
            std::cout << "No" << std::endl;
        }
    }

    return 0;
}