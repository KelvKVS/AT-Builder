interface GameBundleBuilderInterface {
    GameBundleBuilderInterface addAAAGame(String name, double price);

    GameBundleBuilderInterface addIndieGame(String name, double price);

    GameBundleBuilderInterface addMobileGame(String name, double price);

    GameBundleBuilderInterface addDLC(String name, double price);

    GameBundleBuilderInterface clear();

    GameBundle build();
}
