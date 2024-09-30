import java.util.LinkedList;
import java.util.List;

public class EnumGuitar {

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        
        // Gitar ekleme
        inventory.addGuitar("12345", 1500.00, Builder.KILIÇSAZEVİ, Model.ÖĞRENCİ, Type.ELECTRIC, BackWood.MAUN, TopWood.PELESENK);
        inventory.addGuitar("67890", 1200.00, Builder.ANKAMÜZİK, Model.PROFOSYONEL, Type.ACOUSTIC, BackWood.ARDIÇ, TopWood.ABONOZ);
        
        // Gitar arama
        Guitar searchGuitar = new Guitar("", 0, Builder.ANKAMÜZİK, Model.PROFOSYONEL, Type.ACOUSTIC, null, null);
        Guitar foundGuitar = inventory.search(searchGuitar);
        
        if (foundGuitar != null) {
            System.out.println("Guitar found: " + foundGuitar.getSerialNumber());
        } else {
            System.out.println("Guitar not found.");
        }
    }
}

enum Type {
    ACOUSTIC, ELECTRIC;
}

enum Builder {
    KILIÇSAZEVİ, ANKAMÜZİK, DOLDURSAZEVİ, SIRRIMÜZİK;
}

enum BackWood {
    MAUN, ARDIÇ, KİRAZ;
}

enum TopWood {
    PELESENK, ABONOZ;
}

enum Model {
    ÖĞRENCİ, ORTAKALİTE, PROFOSYONEL;
}

class Guitar {
    private String serialNumber;
    private double price;
    private Builder builder;
    private Model model; 
    private Type type;
    private BackWood backWood;
    private TopWood topWood;

    public Guitar(String serialNumber, double price,
                  Builder builder, Model model, Type type,
                  BackWood backWood, TopWood topWood) {
        this.serialNumber = serialNumber;
        this.price = price;
        this.builder = builder;
        this.model = model; 
        this.type = type;
        this.backWood = backWood;
        this.topWood = topWood;
    }

    public String getSerialNumber() { return serialNumber; }
    public double getPrice() { return price; }
    public void setPrice(double newPrice) { this.price = newPrice; }
    public Builder getBuilder() { return builder; }
    public Model getModel() { return model; } 
    public Type getType() { return type; }
    public BackWood getBackWood() { return backWood; }
    public TopWood getTopWood() { return topWood; }
}

class Inventory {
    private List<Guitar> guitars;

    public Inventory() {
        guitars = new LinkedList<>();
    }

    public void addGuitar(String serialNumber, double price,
                          Builder builder, Model model, Type type,
                          BackWood backWood, TopWood topWood) {
        Guitar guitar = new Guitar(serialNumber, price, builder,
                model, type, backWood, topWood);
        guitars.add(guitar);
    }

    public Guitar getGuitar(String serialNumber) {
        for (Guitar guitar : guitars) {
            if (guitar.getSerialNumber().equals(serialNumber)) {
                return guitar;
            }
        }
        return null;
    }

    public Guitar search(Guitar searchGuitar) {
        for (Guitar guitar : guitars) {
            
            if (!isMatch(guitar.getBuilder(), searchGuitar.getBuilder())) continue;
            if (!isMatch(guitar.getModel(), searchGuitar.getModel())) continue; 
            if (!isMatch(guitar.getType(), searchGuitar.getType())) continue;
            if (!isMatch(guitar.getBackWood(), searchGuitar.getBackWood())) continue;
            if (!isMatch(guitar.getTopWood(), searchGuitar.getTopWood())) continue;

            return guitar;
        }
        return null;
    }

    private boolean isMatch(Enum<?> guitarValue, Enum<?> searchValue) {
        if (searchValue == null) return true; 
        return guitarValue != null && guitarValue == searchValue; 
    }
}
