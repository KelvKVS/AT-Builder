import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameStoreGUI extends JFrame {
    private GameBundleBuilder builder;
    private GameBundleDirector director;
    private JTextArea displayArea;
    private List<JCheckBox> checkBoxes;

    private static final String[][] GAMES = {
            {"Cyberpunk 2077", "199.90", "AAA"},
            {"The Witcher 3", "89.90", "AAA"},
            {"Elden Ring", "249.90", "AAA"},
            {"God of War", "199.90", "AAA"},
            {"Hades", "49.90", "Indie"},
            {"Stardew Valley", "24.99", "Indie"},
            {"Celeste", "19.99", "Indie"},
            {"Hollow Knight", "34.90", "Indie"},
            {"Monument Valley", "12.99", "Mobile"},
            {"Genshin Impact Starter", "29.90", "Mobile"},
            {"PUBG Mobile Premium", "15.90", "Mobile"},
            {"Minecraft Pocket Edition", "26.90", "Mobile"},
            {"Cyberpunk - Phantom Liberty", "99.90", "DLC"},
            {"Witcher 3 - Blood and Wine", "39.90", "DLC"},
            {"Elden Ring - Shadow of the Erdtree", "119.90", "DLC"},
            {"Hades - Bonus Content Pack", "19.90", "DLC"}
    };

    public GameStoreGUI() {
        builder = new GameBundleBuilder();
        director = new GameBundleDirector(builder);
        checkBoxes = new ArrayList<>();

        initComponents();
        updateDisplay();
    }

    private void initComponents() {
        setTitle("GameStore - Builder de Pacotes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton casualBtn = new JButton("Pacote Casual");
        JButton gamerBtn = new JButton("Pacote Gamer");
        JButton ultimateBtn = new JButton("Pacote Ultimate");
        JButton clearBtn = new JButton("Limpar");

        casualBtn.addActionListener(e -> createPresetBundle("casual"));
        gamerBtn.addActionListener(e -> createPresetBundle("gamer"));
        ultimateBtn.addActionListener(e -> createPresetBundle("ultimate"));
        clearBtn.addActionListener(e -> clearAll());

        buttonPanel.add(casualBtn);
        buttonPanel.add(gamerBtn);
        buttonPanel.add(ultimateBtn);
        buttonPanel.add(clearBtn);

        add(buttonPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        gamePanel.setBorder(BorderFactory.createTitledBorder("Selecione os Jogos"));

        for (String[] game : GAMES) {
            JCheckBox checkBox = new JCheckBox(game[0] + " (" + game[2] + ") - R$ " + game[1]);
            checkBox.addActionListener(new GameCheckBoxListener(game));
            checkBoxes.add(checkBox);
            gamePanel.add(checkBox);
        }

        JScrollPane gameScroll = new JScrollPane(gamePanel);
        gameScroll.setPreferredSize(new Dimension(400, 500));

        displayArea = new JTextArea(30, 30);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane displayScroll = new JScrollPane(displayArea);
        displayScroll.setBorder(BorderFactory.createTitledBorder("Seu Pacote"));

        splitPane.setLeftComponent(gameScroll);
        splitPane.setRightComponent(displayScroll);
        splitPane.setDividerLocation(400);

        add(splitPane, BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void createPresetBundle(String type) {
        clearAllCheckBoxes();

        GameBundle bundle = null;
        switch (type) {
            case "casual":
                bundle = director.createCasualBundle();
                break;
            case "gamer":
                bundle = director.createGamerBundle();
                break;
            case "ultimate":
                bundle = director.createUltimateBundle();
                break;
        }

        if (bundle != null) {
            selectGamesInBundle(bundle);
        }

        updateDisplay();
    }

    private void selectGamesInBundle(GameBundle bundle) {
        for (GameItem item : bundle.getGames()) {
            for (int i = 0; i < GAMES.length; i++) {
                if (GAMES[i][0].equals(item.getName())) {
                    checkBoxes.get(i).setSelected(true);
                    break;
                }
            }
        }
    }

    private void clearAll() {
        clearAllCheckBoxes();
        builder.clear();
        updateDisplay();
    }

    private void clearAllCheckBoxes() {
        for (JCheckBox checkBox : checkBoxes) {
            checkBox.setSelected(false);
        }
    }

    private void updateDisplay() {
        GameBundle bundle = builder.build();
        StringBuilder sb = new StringBuilder();

        sb.append("=== SEU PACOTE DE JOGOS ===\n\n");

        if (bundle.getGames().isEmpty()) {
            sb.append("Nenhum jogo selecionado.\n\n");
            sb.append("Dicas:\n");
            sb.append("- Selecione jogos individuais ou\n");
            sb.append("- Use os botões de pacotes predefinidos\n");
            sb.append("- 3+ jogos = 10% desconto\n");
            sb.append("- 5+ jogos = 20% desconto\n");
        } else {
            // Agrupar por categoria
            List<GameItem> aaaGames = new ArrayList<>();
            List<GameItem> indieGames = new ArrayList<>();
            List<GameItem> mobileGames = new ArrayList<>();
            List<GameItem> dlcGames = new ArrayList<>();

            for (GameItem game : bundle.getGames()) {
                switch (game.getCategory()) {
                    case "AAA": aaaGames.add(game); break;
                    case "Indie": indieGames.add(game); break;
                    case "Mobile": mobileGames.add(game); break;
                    case "DLC": dlcGames.add(game); break;
                }
            }

            if (!aaaGames.isEmpty()) {
                sb.append("JOGOS AAA:\n");
                for (GameItem game : aaaGames) {
                    sb.append("  - ").append(game.getName()).append(" - R$ ").append(String.format("%.2f", game.getPrice())).append("\n");
                }
                sb.append("\n");
            }

            if (!indieGames.isEmpty()) {
                sb.append("JOGOS INDIE:\n");
                for (GameItem game : indieGames) {
                    sb.append("  - ").append(game.getName()).append(" - R$ ").append(String.format("%.2f", game.getPrice())).append("\n");
                }
                sb.append("\n");
            }

            if (!mobileGames.isEmpty()) {
                sb.append("JOGOS MOBILE:\n");
                for (GameItem game : mobileGames) {
                    sb.append("  - ").append(game.getName()).append(" - R$ ").append(String.format("%.2f", game.getPrice())).append("\n");
                }
                sb.append("\n");
            }

            if (!dlcGames.isEmpty()) {
                sb.append("DLCs E EXPANSOES:\n");
                for (GameItem game : dlcGames) {
                    sb.append("  - ").append(game.getName()).append(" - R$ ").append(String.format("%.2f", game.getPrice())).append("\n");
                }
                sb.append("\n");
            }

            sb.append("========================\n");
            sb.append("Total de itens: ").append(bundle.getGames().size()).append("\n");
            sb.append("Preco total: R$ ").append(String.format("%.2f", bundle.getTotalPrice())).append("\n");

            if (bundle.getDiscount() > 0) {
                sb.append("Desconto: ").append((int)(bundle.getDiscount() * 100)).append("%\n");
                sb.append("PRECO FINAL: R$ ").append(String.format("%.2f", bundle.getFinalPrice())).append("\n");
            }
        }

        displayArea.setText(sb.toString());
    }

    // Listener para checkboxes
    private class GameCheckBoxListener implements ActionListener {
        private String[] gameData;

        public GameCheckBoxListener(String[] gameData) {
            this.gameData = gameData;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox checkBox = (JCheckBox) e.getSource();

            if (checkBox.isSelected()) {
                String name = gameData[0];
                double price = Double.parseDouble(gameData[1]);
                String category = gameData[2];

                switch (category) {
                    case "AAA":
                        builder.addAAAGame(name, price);
                        break;
                    case "Indie":
                        builder.addIndieGame(name, price);
                        break;
                    case "Mobile":
                        builder.addMobileGame(name, price);
                        break;
                    case "DLC":
                        builder.addDLC(name, price);
                        break;
                }
            } else {
                // Reconstruir o bundle sem este item
                rebuildBundle();
            }

            updateDisplay();
        }

        private void rebuildBundle() {
            builder.clear();
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    String[] game = GAMES[i];
                    String name = game[0];
                    double price = Double.parseDouble(game[1]);
                    String category = game[2];

                    switch (category) {
                        case "AAA":
                            builder.addAAAGame(name, price);
                            break;
                        case "Indie":
                            builder.addIndieGame(name, price);
                            break;
                        case "Mobile":
                            builder.addMobileGame(name, price);
                            break;
                        case "DLC":
                            builder.addDLC(name, price);
                            break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameStoreGUI().setVisible(true);
            }
        });
    }
}