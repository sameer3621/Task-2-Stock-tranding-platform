import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class TradingApp {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        // 1) Setup market with a few stocks
        Map<String, Stock> market = new LinkedHashMap<>();
        market.put("AAPL", new Stock("AAPL", 160.00));
        market.put("GOOG", new Stock("GOOG", 2800.00));
        market.put("TSLA", new Stock("TSLA", 720.00));
        market.put("AMZN", new Stock("AMZN", 3400.00));

        // 2) Create user portfolio with $10,000
        Portfolio portfolio = new Portfolio(10_000);

        boolean running = true;
        while (running) {
            System.out.println("\n=== Menu ===");
            System.out.println("1) Show Market Prices");
            System.out.println("2) Buy Shares");
            System.out.println("3) Sell Shares");
            System.out.println("4) View Portfolio");
            System.out.println("5) Next Day (Update Prices)");
            System.out.println("0) Exit");
            System.out.print("> ");
            String cmd = in.nextLine().trim();

            try {
                switch (cmd) {
                    case "1":
                        market.values().forEach(System.out::println);
                        break;
                    case "2":
                        trade(portfolio, market, true);
                        break;
                    case "3":
                        trade(portfolio, market, false);
                        break;
                    case "4":
                        portfolio.printReport(market);
                        break;
                    case "5":
                        market.values().forEach(Stock::randomUpdate);
                        System.out.println("Market prices updated.");
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        System.out.println("Thanks for trading—goodbye!");
    }

    private static void trade(Portfolio p, Map<String, Stock> m, boolean isBuy) {
        System.out.print("Enter symbol: ");
        String sym = in.nextLine().toUpperCase();
        Stock s = m.get(sym);
        if (s == null) {
            System.out.println("No such stock.");
            return;
        }
        System.out.print("Enter quantity: ");
        int qty = Integer.parseInt(in.nextLine());
        if (isBuy) {
            p.buy(s, qty);
            System.out.printf("Bought %d of %s%n", qty, sym);
        } else {
            p.sell(s, qty);
            System.out.printf("Sold  %d of %s%n", qty, sym);
        }
    }
}
