import java.util.ArrayList;
import java.util.List;

class GameBundle {
    private List<GameItem> games;
    private double totalPrice;
    private double discount;

    public GameBundle() {
        this.games = new ArrayList<>();
        this.totalPrice = 0.0;
        this.discount = 0.0;
    }

    public List<GameItem> getGames() {
        return games;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public double getFinalPrice() {
        return totalPrice * (1 - discount);
    }

    void addGame(GameItem game) {
        games.add(game);
        totalPrice += game.getPrice();
        updateDiscount();
    }

    void clear() {
        games.clear();
        totalPrice = 0.0;
        discount = 0.0;
    }

    private void updateDiscount() {
        int totalItems = games.size();
        if (totalItems >= 5) {
            discount = 0.20; // 20% desconto
        } else if (totalItems >= 3) {
            discount = 0.10; // 10% desconto
        } else {
            discount = 0.0;
        }
    }
}
