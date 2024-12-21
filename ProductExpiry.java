import java.time.LocalDate;
import java.util.*;

public class ProductExpiry {
    private String productName;
    private LocalDate expiryDate;

    public ProductExpiry(String productName, LocalDate expiryDate) {
        this.productName = productName;
        this.expiryDate = expiryDate;
    }

    public String getProductName() {
        return productName;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public String toString() {
        return productName + " expires on " + expiryDate;
    }
}