#include <iostream>
#include <vector>
#include <random>
#include <ctime>

#define ll long long
 
ll n, e, C, q, p;
 
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
 
ll powWithMod(ll a, ll n, ll m) {
    ll result = 1;
    while (n != 0) {
        if ((n & 1) != 0)
            result = multiplybymod(result, a, m);
        a = multiplybymod(a, a, m);
        n >>= 1;
    }
    return result;
}

ll gcd_extra(ll a, ll b, ll &x, ll &y) {
    if (a == 0) {
        x = 0;
        y = 1;
        return b;
    }
    ll x1 = 0, y1 = 0;
    ll d = gcd_extra(b % a, a, x1, y1);
    x = y1 - (b / a) * x1;
    y = x1;
    return d;
}

int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cin >> n >> e >> C;
    for (ll i = 2; i < n; ++i) {
        if (n %  i == 0) {
            q = i;
            p = n / i;
            break;
        }
    }
    ll ph = (p - 1) * (q - 1);
    ll x, y;
    ll ans = gcd_extra(e, ph, x, y);
    ll d = (x + ph) % ph;
    ll M = powWithMod(C, d, n);
    std::cout << M << "\n";
    return 0;
}
