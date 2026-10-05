import java.util.Random;

/**
 * Enthaelt die Spiellogik und verwaltet den Punktestand des Gewinnspiels.
 */
public class GewinnModel {
    private int gesamtPunkte;
    private int spielerZahl;
    private int computerZahl;
    private int rundenErgebnis;
    private final Random random;

    /**
     * Erstellt ein neues Spiel mit einem Startpunktestand von 30 Punkten.
     */
    public GewinnModel() {
        this.gesamtPunkte = 30;
        this.random = new Random();
    }

    /**
     * Setzt den Punktestand und die Ergebnisse der aktuellen Runde zurueck.
     */
    public void reset() {
        this.gesamtPunkte = 30;
        this.spielerZahl = 0;
        this.computerZahl = 0;
        this.rundenErgebnis = 0;
    }

    /**
     * Liefert den aktuellen Gesamtpunktestand.
     *
     * @return Gesamtpunktestand des Spiels
     */
    public int getGesamtPunkte() {
        return gesamtPunkte;
    }

    /**
     * Liefert die zuletzt gezogene Zahl des Computers.
     *
     * @return Computerzahl zwischen 1 und 9
     */
    public int getComputerZahl() {
        return computerZahl;
    }

    /**
     * Liefert die Punkte der zuletzt berechneten Runde.
     *
     * @return Rundenergebnis in Punkten
     */
    public int getRundenErgebnis() {
        return rundenErgebnis;
    }

    /**
     * Erzeugt eine zufaellige Computerzahl zwischen 1 und 9.
     */
    public void berechneComputerZahl() {
        this.computerZahl = random.nextInt(9) + 1;
    }

    /**
     * Berechnet das Rundenergebnis und aktualisiert den Gesamtpunktestand.
     * Bei gleicher Zahl gibt es 20, bei einer Differenz von eins 5 und
     * ansonsten -10 Punkte.
     *
     * @param spielerZahl vom Spieler gewaehlte Zahl zwischen 1 und 9
     */
    public void berechneRunde(int spielerZahl) {
        this.spielerZahl = spielerZahl;
        berechneComputerZahl();

        int diff = Math.abs(this.spielerZahl - this.computerZahl);

        if (diff == 0) {
            this.rundenErgebnis = 20;
        } else if (diff == 1) {
            this.rundenErgebnis = 5;
        } else {
            this.rundenErgebnis = -10;
        }

        this.gesamtPunkte += this.rundenErgebnis;
    }
    
    /**
     * Prueft, ob der Punktestand die Gewinnschwelle erreicht hat.
     *
     * @return {@code true}, wenn mindestens 100 Punkte erreicht wurden
     */
    public boolean hatGewonnen() {
        return gesamtPunkte >= 100;
    }

    /**
     * Prueft, ob der Punktestand unter null gefallen ist.
     *
     * @return {@code true}, wenn das Spiel verloren wurde
     */
    public boolean hatVerloren() {
        return gesamtPunkte < 0;
    }
}