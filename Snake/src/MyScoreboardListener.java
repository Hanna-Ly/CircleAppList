import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MyScoreboardListener implements ActionListener {
    private GamePanel gamePanel;
    private InfoPanel infoPanel;
    private String filename = "Scoreboard.txt"; // Edetabeli fail
    private List<ScoreData> scoreData; // Siin on faili sisu nimi, skoor
    private int topLength = 0; // Kui mitu isikut edetabelis naidta 0=>koik
    public MyScoreboardListener(GamePanel gamePanel, InfoPanel infoPanel) { // InfoPanel infoPanel
        this.gamePanel = gamePanel;
        this.infoPanel = infoPanel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // System.out.println("Klikiti edetabelil"); // TEST!
        topLength = (Integer) infoPanel.getSpinner().getValue();
        try {
            if (readFromFile()) {
                JDialog dialog = new JDialog();
                createScoreboard(dialog);
                dialog.setModal(true); // gamePanel aknast midagi valida ei saa
                dialog.pack(); // Et komponendid laheks omale kohale
                dialog.setLocationRelativeTo(null); // Ekraani keskele
                dialog.setVisible(true); // Naita dialogi akent
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Pole midagi naidata");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    private boolean readFromFile() throws IOException {
        scoreData = new ArrayList<>(); // Tee muutuja uuesti
        File f = new File(filename); // Failinimi tehti objektiks fail
        if (f.exists()) { //  Kas fail on olemas
            if (f.length() > 0 ) { // Kui failis on midagi, ehk pole tuhi
                try(BufferedReader br = new BufferedReader( new FileReader(filename))) {
                    for(String line; (line = br.readLine()) != null;) {
                        String name = line.split(";")[0];
                        int score = Integer.parseInt(line.split(";")[1]);
                        scoreData.add(new ScoreData(name, score));
                    }
                    /* Sorteerimine skoori jargi kahanevalt Z -> A */
                    scoreData.sort(Comparator.comparing(ScoreData::getScore, Comparator.reverseOrder()));
                    return true;
                }
            }
        }else {
            f.createNewFile(); // Faili pole, teeme selle
            JOptionPane.showMessageDialog(gamePanel, "Edetabeli fail just tehti!");
        }
        return false;
    }
    private void createScoreboard(JDialog dialog) {
        /* Tabeli paise veeru nimed */
        String[] columnNames = new String[] {"Jrk", "Nimi", "Puntkid"}; // Pais muutus Punkt 3
        // Mitu isikut naidata kas koik voi 1 - 25 k.a. Punkt 4
        if(topLength == 0 || topLength > 25 || topLength > scoreData.size()) {
            topLength = scoreData.size(); // Kogu massiiv
        }
        String[][] data = new String[topLength][3]; // Siin on andmed, mida naidata
        /* scoreData'st lisa info data */
        for (int x = 0; x < topLength; x++) {
            data[x][0] = String.valueOf(x+1); // Taisarv stringiks Punkt 3 ja 4
            data[x][1] = scoreData.get(x).getName();
            data[x][2] = String.valueOf(scoreData.get(x).getScore()); // int => stringiks
        }
        JTable table = new JTable(data, columnNames); // Teeb tabeli andmetega
        dialog.add(new JScrollPane(table)); // Tabel dialoogi aknasse
        dialog.setTitle("Edetabel");
    }
}
