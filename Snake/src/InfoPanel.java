import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPanel extends JPanel {
    private GamePanel gamePanel;
    private JButton btnStart;
    private JButton btnScoreboard;
    private JTextField txtPlayerName;
    private JSpinner spinner;

    public InfoPanel(GamePanel gamePanel) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT)); // Nupud hakkaks vasakult, vaikimisi keskelt
        this.gamePanel = gamePanel;
        this.setBackground(new Color(150,255,230));
        this.setPreferredSize(new Dimension(300, 75));
        createStartButton();
        createScoreBoardButton();
        createTextField();
        createSpinner();
    }

    private void createStartButton() {
        btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("Klikiti"); // TEST konsooli
                gamePanel.startGame();
                gamePanel.requestFocus(); // Et nooleklahvid jaaks toole
                // System.out.println(gamePanel.getPlayerName());
            }
        });
        this.add(btnStart);
    }

    private void createScoreBoardButton() {
        btnScoreboard = new JButton("Edetabel");
        btnScoreboard.addActionListener(new MyScoreboardListener(gamePanel, this));
        this.add(btnScoreboard);
    }
    private void createTextField() {
        txtPlayerName = new JTextField("",15);
        this.add(txtPlayerName);
    }
    public JTextField getTxtPlayerName() {
        return txtPlayerName;
    }
    public void createSpinner(){
        spinner = new JSpinner(); // Loob spinneri
        spinner.setPreferredSize(new Dimension(70,25));
        this.add(spinner); // Lisamine infopanelile

        }

    public JSpinner getSpinner() {
        return spinner;
    }
}

