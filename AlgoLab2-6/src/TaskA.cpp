#include <iostream>
#include <vector>
#include <stdio.h>
 
const int M = 20000000 + 2;
std::vector<bool> compositeNums (M + 2, false);
int n;
int main() {
    compositeNums[0] = true;
    compositeNums[1] = true;
    for (long long i = 2; i < M; i++) {
        if (!compositeNums[(int)i]) {
            long long sqrt = i * i;
            for (long long j = sqrt; j <= M; j += i) {
                compositeNums[(int) j] = true;
            }
        }
    }
    scanf("%d", &n);
    int cur;
    for (int i = 0; i < n; ++i) {
        scanf("%d", &cur);
        if (compositeNums[cur]) {
            printf("NO\n");
        } else {
            printf("YES\n");
        }
    }
    return 0;
}
