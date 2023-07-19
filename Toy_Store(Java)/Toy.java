
public class Toy {
    private int id;
    private String name;
    private int frequency;
    private int quantity;

    public Toy(int id, String name, int frequency, int quantity) {
        this.id = id;
        this.name = name;
        this.frequency = frequency;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
