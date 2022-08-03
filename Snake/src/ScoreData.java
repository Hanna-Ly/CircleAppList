public class ScoreData {
    private String name; // Nimi failis
    private int score; // Punktid failis

    public ScoreData(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
