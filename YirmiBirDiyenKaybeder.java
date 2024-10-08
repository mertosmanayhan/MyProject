import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YirmiBirDiyenKaybeder {
    private static int currentNumber = 0;
    private static String currentPlayer = "Oyuncu 1";
    private static JLabel currentNumberLabel;
    private static JTextArea gameLog;

    public static void main(String[] args) {
        // Arayüz oluşturma
        JFrame frame = new JFrame("21 Diyen Kaybeder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        currentNumberLabel = new JLabel("Mevcut Sayı: " + currentNumber);
        frame.add(currentNumberLabel);

        gameLog = new JTextArea(10, 30);
        gameLog.setEditable(false);
        frame.add(new JScrollPane(gameLog));

        JButton add1Button = new JButton("1 Ekle");
        JButton add2Button = new JButton("2 Ekle");
        JButton add3Button = new JButton("3 Ekle");

        // Butonlara dinleyici ekleme
        add1Button.addActionListener(new MoveAction(1));
        add2Button.addActionListener(new MoveAction(2));
        add3Button.addActionListener(new MoveAction(3));

        frame.add(add1Button);
        frame.add(add2Button);
        frame.add(add3Button);

        frame.setVisible(true);
    }

    // Oyuncu hareketini işleyen sınıf
    static class MoveAction implements ActionListener {
        private int increment;

        public MoveAction(int increment) {
            this.increment = increment;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentNumber < 21) {
                currentNumber += increment;
                gameLog.append(currentPlayer + " " + increment + " ekledi. Yeni sayı: " + currentNumber + "\n");

                if (currentNumber >= 21) {
                    gameLog.append(currentPlayer + " 21 diyerek kaybetti!\n");
                    JOptionPane.showMessageDialog(null, currentPlayer + " kaybetti!", "Oyun Bitti", JOptionPane.INFORMATION_MESSAGE);
                    resetGame();
                } else {
                    // Sıra değiştir
                    currentPlayer = currentPlayer.equals("Oyuncu 1") ? "Oyuncu 2" : "Oyuncu 1";
                    currentNumberLabel.setText("Mevcut Sayı: " + currentNumber);
                }
            }
        }
    }

    private static void resetGame() {
        currentNumber = 0;
        currentPlayer = "Oyuncu 1";
        currentNumberLabel.setText("Mevcut Sayı: " + currentNumber);
        gameLog.setText("");
    }
}

