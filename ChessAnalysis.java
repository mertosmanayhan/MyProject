import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.game.PgnHolder;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChessAnalysis {

    public static void main(String[] args) throws Exception {
        // PGN dosyasının yolu
        String pgnFile = "path/to/your/game.pgn"; // PGN dosyanızın tam yolu
        
        // PGN dosyasını okuyup FEN pozisyonları ve etiketleri çıkar
        List<String> fenPositions = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();
        processPgnFile(pgnFile, fenPositions, labels);

        // FEN pozisyonlarını vektörlere çevir
        int[][] vectors = new int[fenPositions.size()][64];
        for (int i = 0; i < fenPositions.size(); i++) {
            vectors[i] = fenToVector(fenPositions.get(i));
        }

        // Makine öğrenimi modeli için verileri hazırla ve eğit
        int[] labelArray = labels.stream().mapToInt(i -> i).toArray();
        trainAndEvaluateModel(vectors, labelArray);
    }

    // PGN dosyasını işleyerek FEN pozisyonlarını ve etiketleri çıkarır
    public static void processPgnFile(String pgnFile, List<String> fenPositions, List<Integer> labels) throws Exception {
        PgnHolder pgn = new PgnHolder(pgnFile);
        pgn.loadPgn();

        for (Game game : pgn.getGame()) {
            Board board = new Board();
            String result = game.getResult().name();
            int label = result.equals("WHITE_WON") ? 2 : result.equals("BLACK_WON") ? 0 : 1;

            for (String move : game.getHalfMoves()) {
                board.doMove(move);
                fenPositions.add(board.getFen());
                labels.add(label);
            }
        }
        System.out.println("FEN Pozisyonları ve Etiketler işlenmiştir.");
    }

    // FEN formatını vektöre dönüştürür
    public static int[] fenToVector(String fen) {
        int[] vector = new int[64];
        String[] parts = fen.split(" ");
        String boardState = parts[0];

        int index = 0;
        for (char c : boardState.toCharArray()) {
            if (Character.isDigit(c)) {
                index += Character.getNumericValue(c);
            } else if (c != '/') {
                vector[index++] = pieceToValue(c);
            }
        }
        return vector;
    }

    // Taşları sayısal değerlere dönüştürür
    public static int pieceToValue(char piece) {
        switch (piece) {
            case 'p': return -1; // Siyah piyon
            case 'r': return -2; // Siyah kale
            case 'n': return -3; // Siyah at
            case 'b': return -4; // Siyah fil
            case 'q': return -5; // Siyah vezir
            case 'k': return -6; // Siyah şah
            case 'P': return 1;  // Beyaz piyon
            case 'R': return 2;  // Beyaz kale
            case 'N': return 3;  // Beyaz at
            case 'B': return 4;  // Beyaz fil
            case 'Q': return 5;  // Beyaz vezir
            case 'K': return 6;  // Beyaz şah
            default: return 0;
        }
    }

    // Makine öğrenimi modeli oluşturur ve eğitir
    public static void trainAndEvaluateModel(int[][] vectors, int[] labels) throws Exception {
        // Özellikleri tanımla
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            attributes.add(new Attribute("square" + i));
        }
        ArrayList<String> classValues = new ArrayList<>();
        classValues.add("Loss");
        classValues.add("Draw");
        classValues.add("Win");
        attributes.add(new Attribute("class", classValues));

        // Verileri Weka formatına dönüştür
        Instances dataset = new Instances("ChessData", attributes, vectors.length);
        dataset.setClassIndex(64);

        for (int i = 0; i < vectors.length; i++) {
            double[] instanceValues = new double[65];
            for (int j = 0; j < 64; j++) {
                instanceValues[j] = vectors[i][j];
            }
            instanceValues[64] = labels[i];
            dataset.add(new DenseInstance(1.0, instanceValues));
        }

        // Random Forest Modeli Eğit
        RandomForest model = new RandomForest();
        model.buildClassifier(dataset);

        System.out.println("Model eğitildi.");
        System.out.println(model.toString());
    }
} 