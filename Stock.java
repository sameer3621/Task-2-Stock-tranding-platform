public class Stock {
    private final String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol.toUpperCase();
        this.price = price;
    }

    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }

    // Simulate a random price update ±5%
    public void randomUpdate() {
        double changePct = (Math.random() * 10 - 5) / 100.0;
        price = Math.max(0.01, price * (1 + changePct));
    }

    @Override
    public String toString() {
        return String.format("%-6s $%,8.2f", symbol, price);
    }
}
