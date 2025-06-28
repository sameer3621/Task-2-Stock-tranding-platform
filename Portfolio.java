import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private double cash;
    private final Map<String, Integer> holdings = new HashMap<>();

    public Portfolio(double initialCash) {
        this.cash = initialCash;
    }

    public double getCash() { return cash; }

    // Current market value of all shares
    public double totalHoldingsValue(Map<String, Stock> market) {
        return holdings.entrySet().stream()
                .mapToDouble(e -> market.get(e.getKey()).getPrice() * e.getValue())
                .sum();
    }

    public void buy(Stock s, int qty) {
        double cost = s.getPrice() * qty;
        if (cost > cash) {
            throw new IllegalArgumentException("Insufficient cash.");
        }
        cash -= cost;
        holdings.merge(s.getSymbol(), qty, Integer::sum);
    }

    public void sell(Stock s, int qty) {
        int owned = holdings.getOrDefault(s.getSymbol(), 0);
        if (qty > owned) {
            throw new IllegalArgumentException("Not enough shares to sell.");
        }
        holdings.put(s.getSymbol(), owned - qty);
        cash += s.getPrice() * qty;
    }

    public void printReport(Map<String, Stock> market) {
        System.out.printf("Cash Balance: $%,.2f%n", cash);
        System.out.println("Holdings:");
        System.out.printf("%-6s %8s %10s%n","SYM","QTY","MKT VAL");
        holdings.forEach((sym, qty) -> {
            if (qty > 0) {
                double val = market.get(sym).getPrice() * qty;
                System.out.printf("%-6s %8d $%,10.2f%n", sym, qty, val);
            }
        });
        System.out.printf("Total Portfolio Value: $%,.2f%n",
                cash + totalHoldingsValue(market));
    }
}
