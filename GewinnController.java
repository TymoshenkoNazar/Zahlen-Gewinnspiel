import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Verbindet das Gewinnspiel-Modell mit der grafischen Benutzeroberflaeche.
 * @version 05-10-2026
 * @author Tymoshenko Nazar
 */
public class GewinnController implements ActionListener {
    private GewinnModel model;
    private GewinnView view;

    /**
     * Erstellt einen Controller und registriert die Listener der View.
     *
     * @param model Modell mit der Spiellogik und dem aktuellen Punktestand
     * @param view Benutzeroberflaeche des Gewinnspiels
     */
    public GewinnController(GewinnModel model, GewinnView view) {
        this.model = model;
        this.view = view;

        this.view.getTxtSpielerZahl().addActionListener(this);
        this.view.getBtnReset().addActionListener(this);
    }


    /**
     * Liest die Spielereingabe ein, berechnet die Runde und aktualisiert die View.
     */
    private void verarbeiteEingabe() {
        String input = view.getTxtSpielerZahl().getText().trim();
        try {
            int zal = Integer.parseInt(input);
            if (zal < 1 || zal > 9) {
                JOptionPane.showMessageDialog(view, "Bitte eine Zahl zwischen 1 und 9 eingeben!");
                return;
            }

            model.berechneRunde(zal);
            
            view.getTxtComputerZahl().setText(String.valueOf(model.getComputerZahl()));
            view.getLblGesamtpunkte().setText(String.valueOf(model.getGesamtPunkte()));

            if (model.hatGewonnen()) {
                view.getLblRundenErgebnis().setText("Gewonnen!");
            } else if (model.hatVerloren()) {
                view.getLblRundenErgebnis().setText("Verloren!");
            } else {
                int erg = model.getRundenErgebnis();
                view.getLblRundenErgebnis().setText(erg > 0 ? "+" + erg : String.valueOf(erg));
            }

            view.getTxtSpielerZahl().setEnabled(false);
            view.getBtnReset().setEnabled(true);

            if (model.getRundenErgebnis() > 0 || model.hatGewonnen()) {
                view.getLblRundenErgebnis().setBackground(Color.GREEN);
                view.getLblGesamtpunkte().setBackground(Color.GREEN);
            } else {
                view.getLblRundenErgebnis().setBackground(Color.RED);
                view.getLblGesamtpunkte().setBackground(Color.RED);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Ungültige Eingabe! Bitte eine Zahl eingeben.");
        }
    }

    /**
     * Setzt das Modell und alle sichtbaren Eingabefelder auf den Startzustand zurueck.
     */
    private void resetRunde() {
        if (model.hatVerloren()) {
            model.reset();
        }

        view.getTxtSpielerZahl().setText("");
        view.getTxtComputerZahl().setText("");
        view.getLblRundenErgebnis().setText("");
        view.getLblGesamtpunkte().setText(String.valueOf(model.getGesamtPunkte()));

        view.getTxtSpielerZahl().setEnabled(true);
        view.getBtnReset().setEnabled(false);
        view.getLblRundenErgebnis().setBackground(Color.WHITE);
        view.getLblGesamtpunkte().setBackground(Color.WHITE);
    }

    /**
     * Verarbeitet alle Aktionen der View.
     *
     * @param e ausgelöstes Ereignis
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getTxtSpielerZahl()) {
            verarbeiteEingabe();
        } else if (e.getSource() == view.getBtnReset()) {
            resetRunde();
        }
    }
    /**
     * Startet die Swing-Anwendung im Event-Dispatch-Thread.
     *
     * @param args Kommandozeilenargumente, die von der Anwendung nicht benoetigt werden
     */
    public static void main(String[] args) {
        GewinnModel model = new GewinnModel();
        GewinnView view = new GewinnView();
        new GewinnController(model, view);
        view.setVisible(true);
    }
}