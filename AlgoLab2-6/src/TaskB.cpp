#include <stdio.h>
#include <vector>
static const int MAX = 1000005;
int n, cur;
std::vector<int> dividers(MAX);
std::vector<bool> primes(MAX, true);
int main() {
    scanf("%d", &n);
    primes[0] = primes[1] = false;
    for (int i = 2; i < 1000; ++i) {
        if (primes[i]) {
            for (long long j = static_cast<long> (i) * i; j < MAX; j += i) {
                if (primes[j]) {
                    primes[j] = false;
                    dividers[j] = i;
                    
                }
            }
        }
    }
    for (int i = 0; i < n; ++i) {
        scanf("%d", &cur);
        while (!primes[cur]) {
            printf("%d ", dividers[cur]);
            cur = cur / dividers[cur];
        }
        printf("%d\n", cur);
    }
    return 0;
}
