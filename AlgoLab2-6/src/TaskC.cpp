#include <iostream>
#include <vector>
#include <random>
#include <ctime>
 
#define ll long long
 
size_t n;
ll cur;
 
ll multiplybymod(ll a, ll n, ll m) {
    ll result = 0;
    while (n != 0) {
        if (n % 2 == 1)
            result = (result + a) % m;
        a = (a + a) % m;
        n /= 2;
    }
    return result;
}
 
ll fastPow(ll a, ll n, ll m) {
    ll result = 1;
    while (n != 0) {
        if ((n & 1) != 0)
            result = multiplybymod(result, a, m);
        a = multiplybymod(a, a, m);
        n >>= 1;
    }
    return result;
}
 
bool check(ll num) {
    ll p = 0;
    ll q = num - 1;
    if (num == 2 || num == 3 || num == 5 || num == 7 || num == 11 || num == 13) {
        return true;
    }
    if (num == 1 || num % 2 == 0) {
        return false;
    }
    while (q % 2 == 0) {
        ++p;
        q = q / 2;
    }
    std::random_device randomDevice;
    std::mt19937 mt(randomDevice());
    mt.seed(time(nullptr));
    std::uniform_int_distribution<int> dist(0, 100111111);
    for (size_t i = 0; i < 20; ++i) {
        ll a = (dist(mt) % (num - 2)) + 2;
        ll powx = fastPow(a, q, num);
        if (powx == num - 1 || powx == 1) {
            continue;
        }
        for (int j = 1; j < p; ++j) {
            powx = multiplybymod(powx, powx, num);
            if (powx == num - 1) {
                break;
            }
            if (powx == 1) {
                return false;
            }
        }
        if (powx != num - 1) {
            return false;
        }
    }
    return true;
}
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cin >> n;
    for (size_t i = 0; i < n; ++i) {
        std::cin >> cur;
        if (!check(cur)) {
            std::cout << "NO\n";
        } else {
            std::cout << "YES\n";
        }
    }
     
    return 0;
}
