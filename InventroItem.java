 class InventoryItem {
    private String name;
    private int quantity;

    public InventoryItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + "," + quantity;
    }

    public static InventoryItem fromString(String str) {
        String[] parts = str.split(",");
        return new InventoryItem(parts[0], Integer.parseInt(parts[1]));
    }
}