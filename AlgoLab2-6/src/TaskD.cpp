#include <iostream>
#include <vector>

long long a, b, n, m;

int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(NULL);
    std:: cin >> a >> b >> n >> m;
    long long i = 1;
    while (i * m % n != a) {
        ++i;
    }
    long long j = 1;
    while (j * n % m != b) {
        ++j;
    }
    long long result = (i * m + j * n) % (m * n);
    std:: cout << result << std::endl;
    return 0;
}
