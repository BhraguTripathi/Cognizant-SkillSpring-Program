package DataStructureAndAlgorithms;

import java.util.Arrays;
import java.util.Comparator;

public class EcommerceSearchExample {
    public static void main(String[] args) {
        Product[] products = {
                new Product(105, "Wireless Mouse", "Electronics"),
                new Product(102, "Bluetooth Speaker", "Electronics"),
                new Product(110, "Running Shoes", "Footwear"),
                new Product(101, "Yoga Mat", "Fitness"),
                new Product(108, "Office Chair", "Furniture"),
                new Product(103, "Desk Lamp", "Furniture"),
                new Product(107, "Water Bottle", "Fitness")
        };

        System.out.println("=== Linear Search Demo ===");
        long startLinear = System.nanoTime();
        int linearResultIndex = linearSearchById(products, 107);
        long endLinear = System.nanoTime();
        printResult(products, linearResultIndex);
        System.out.println("Linear search took " + (endLinear - startLinear) + " ns\n");

        System.out.println("=== Binary Search Demo (requires sorted array) ===");
        Product[] sortedProducts = products.clone();
        Arrays.sort(sortedProducts, Comparator.comparingInt(p -> p.productId));

        System.out.println("Sorted array by productId:");
        for (Product p : sortedProducts) {
            System.out.println("  " + p);
        }

        long startBinary = System.nanoTime();
        int binaryResultIndex = binarySearchById(sortedProducts, 107);
        long endBinary = System.nanoTime();
        printResult(sortedProducts, binaryResultIndex);
        System.out.println("Binary search took " + (endBinary - startBinary) + " ns\n");

        System.out.println("=== Searching for a non-existent product (worst case) ===");
        System.out.println("Linear search result: " + linearSearchById(products, 999));
        System.out.println("Binary search result: " + binarySearchById(sortedProducts, 999));
    }

    static void printResult(Product[] arr, int index) {
        if (index == -1) {
            System.out.println("Product not found.");
        } else {
            System.out.println("Found at index " + index + ": " + arr[index]);
        }
    }

    static int linearSearchById(Product[] products, int targetId) {
        for (int i = 0; i < products.length; i++) {
            if (products[i].productId == targetId) {
                return i;
            }
        }
        return -1;
    }

    static int binarySearchById(Product[] sortedProducts, int targetId) {
        int low = 0;
        int high = sortedProducts.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midId = sortedProducts[mid].productId;

            if (midId == targetId) {
                return mid;
            } else if (midId < targetId) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}

class Product {
    int productId;
    String productName;
    String category;

    public Product(int productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{id=" + productId + ", name='" + productName + "', category='" + category + "'}";
    }
}
