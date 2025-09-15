class GameBundleDirector {
    private GameBundleBuilderInterface builder;

    public GameBundleDirector(GameBundleBuilderInterface builder) {
        this.builder = builder;
    }

    public GameBundle createCasualBundle() {
        return builder
                .clear()
                .addIndieGame("Stardew Valley", 24.99)
                .addMobileGame("Monument Valley", 12.99)
                .addIndieGame("Celeste", 19.99)
                .build();
    }

    public GameBundle createGamerBundle() {
        return builder
                .clear()
                .addAAAGame("Cyberpunk 2077", 199.90)
                .addAAAGame("The Witcher 3", 89.90)
                .addIndieGame("Hades", 49.90)
                .addDLC("Cyberpunk - Phantom Liberty", 99.90)
                .build();
    }

    public GameBundle createUltimateBundle() {
        return builder
                .clear()
                .addAAAGame("Elden Ring", 249.90)
                .addAAAGame("God of War", 199.90)
                .addIndieGame("Hollow Knight", 34.90)
                .addMobileGame("Genshin Impact Starter", 29.90)
                .addDLC("Elden Ring - Shadow of the Erdtree", 119.90)
                .build();
    }
}
