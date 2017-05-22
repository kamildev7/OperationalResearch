#include <bits/stdc++.h>
using namespace std;

constexpr long long MAGIC_PRIME = 9876543217;

struct Warehouse
{
  double x, y, s;
};

struct Demand
{
  size_t store;
  string label;  
  double x, y, s;
};

double cost(const Demand& d, const Warehouse& w)
{
  return sqrt((d.x-w.x)*(d.x-w.x) + (w.y-d.y)*(w.y-d.y)) * d.s;
}

long long lpow(long long base, long long exp)
{
  return exp > 0 ? base * lpow(base, exp - 1) : base;
}

struct Skiplines { size_t num = 1; };
istream& operator>>(istream& in, Skiplines skip)
{
  string _;
  while (skip.num-->0)
    getline(in, _);
  return in;
}

int main()
{
  size_t W, S;
  cin >> Skiplines() >> W;
  cin >> Skiplines{2} >> S >> Skiplines{3};  
  
  vector<Warehouse> warehouses;
  vector<Demand> demands;
  
  for (size_t warehouse = 0; warehouse < W; ++warehouse)
  {
    double x, y, s;
    cin >> x >> y >> s;
    warehouses.push_back({x, y, s});
  }
  
  cin >> Skiplines{3};
  
  for(size_t store = 0; store < S; ++store)
  {
    double x, y;
    cin >> x >> y;   
    
    string line;
    getline(cin, line);
    istringstream irest(line);
    for(;;)
    {
      string label;
      double amount;
      irest >> label >> amount;
      if (!irest)
        break;
        
      demands.push_back({store, label, x, y, amount});
    }
  }
  
  auto dumpSolution = [&](long long ss){    
    vector<double> usage(warehouses.size());
    for (size_t demandId = 0; demandId < demands.size(); ++demandId)
    {
      size_t warehouseId = ss % warehouses.size();
      const Warehouse& warehouse = warehouses[warehouseId];
      const Demand& demand = demands[demandId];
      
      usage[warehouseId] += demand.s;
      
      cout << "Store (" << demand.x << ", " << demand.y << ") gets "
           << "from Wareh (" << warehouse.x << ", " << warehouse.y << ") "
           << demand.label << " [" << demand.s << "]; "
           << "cost = " << cost(demand, warehouse) << "" << endl;
      
      ss /= warehouses.size();
    }
    
    cout << "Warehouses: ";
    for (size_t w = 0; w< warehouses.size(); ++w)
      cout << usage[w] << "/" << warehouses[w].s << "  ";
    cout << endl;
  };
  
  double bestScore = 1e300;
  const long long solutionsCount = lpow(warehouses.size(), demands.size());
  long long solution = 0;
  do
  {
    long long ss = solution;
    double score = 0;
    vector<double> usage(warehouses.size());
    
    bool ok = true;
    for (size_t demandId = 0; demandId < demands.size(); ++demandId)
    {
      size_t warehouseId = ss % warehouses.size();
      
      if ((usage[warehouseId] += demands[demandId].s) > warehouses[warehouseId].s)
      {
        ok = false;
        break;
      }
      score += cost(demands[demandId], warehouses[warehouseId]);
      
      ss /= warehouses.size();
    }
    
    if (ok && score < bestScore)
    {
      bestScore = score;
      cout << "Sol #" << solution << " -> score = " << score << endl;
      dumpSolution(solution);
      cout << endl;
    }
    
    solution += MAGIC_PRIME;
    solution %= solutionsCount;
  }
  while (solution);
}
