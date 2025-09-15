class GameBundleBuilder implements GameBundleBuilderInterface {
    private GameBundle bundle;

    public GameBundleBuilder() {
        this.bundle = new GameBundle();
    }

    @Override
    public GameBundleBuilder addAAAGame(String name, double price) {
        bundle.addGame(new GameItem(name, price, "AAA"));
        return this;
    }

    @Override
    public GameBundleBuilder addIndieGame(String name, double price) {
        bundle.addGame(new GameItem(name, price, "Indie"));
        return this;
    }

    @Override
    public GameBundleBuilder addMobileGame(String name, double price) {
        bundle.addGame(new GameItem(name, price, "Mobile"));
        return this;
    }

    @Override
    public GameBundleBuilder addDLC(String name, double price) {
        bundle.addGame(new GameItem(name, price, "DLC"));
        return this;
    }

    @Override
    public GameBundleBuilder clear() {
        this.bundle = new GameBundle();
        return this;
    }

    @Override
    public GameBundle build() {
        return bundle;
    }
}
