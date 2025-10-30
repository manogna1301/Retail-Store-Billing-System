import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Product Class
class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
}

// Bill Class
class Bill {
    private List<Product> products;
    private double totalAmount;
    private double discount;
    private double tax;
    private double netAmount;

    public Bill() {
        products = new ArrayList<>();
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public void calculateBill() {
        totalAmount = 0;
        for (Product p : products) {
            totalAmount += p.getTotalPrice();
        }
        discount = totalAmount * 0.10; // 10% discount
        tax = (totalAmount - discount) * 0.05; // 5% GST
        netAmount = totalAmount - discount + tax;
    }

    public String generateBillText() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== Retail Store Bill ==========\n");
        for (Product p : products) {
            sb.append(p.getName())
              .append(" | Price: ₹").append(p.getPrice())
              .append(" | Qty: ").append(p.getQuantity())
              .append(" | Total: ₹").append(p.getTotalPrice()).append("\n");
        }
        sb.append("----------------------------------------\n");
        sb.append("Subtotal: ₹").append(totalAmount).append("\n");
        sb.append("Discount (10%): ₹").append(discount).append("\n");
        sb.append("Tax (5%): ₹").append(tax).append("\n");
        sb.append("----------------------------------------\n");
        sb.append("Net Amount: ₹").append(netAmount).append("\n");
        sb.append("========================================\n");
        return sb.toString();
    }
}

// GUI Class
public class RetailStoreBillingSystemGUI extends JFrame {
    private JTextField nameField, priceField, quantityField;
    private JTextArea billArea;
    private Bill bill;

    public RetailStoreBillingSystemGUI() {
        bill = new Bill();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Retail Store Billing System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        JLabel titleLabel = new JLabel("Retail Store Billing System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Product Details"));
        inputPanel.add(new JLabel("Product Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Price (₹):"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        JButton addButton = new JButton("Add Product");
        addButton.setBackground(new Color(52, 152, 219));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> addProduct());
        inputPanel.add(addButton);

        JButton generateButton = new JButton("Generate Bill");
        generateButton.setBackground(new Color(39, 174, 96));
        generateButton.setForeground(Color.WHITE);
        generateButton.addActionListener(e -> generateBill());
        inputPanel.add(generateButton);

        add(inputPanel, BorderLayout.CENTER);

        // Bill Display Area
        billArea = new JTextArea(12, 40);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        billArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(billArea);
        add(scrollPane, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addProduct() {
        try {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int qty = Integer.parseInt(quantityField.getText());

            if (name.isEmpty() || price <= 0 || qty <= 0) {
                JOptionPane.showMessageDialog(this, "Enter valid product details!");
                return;
            }

            Product p = new Product(name, price, qty);
            bill.addProduct(p);

            JOptionPane.showMessageDialog(this, "Product added successfully!");
            nameField.setText("");
            priceField.setText("");
            quantityField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please check values.");
        }
    }

    private void generateBill() {
        bill.calculateBill();
        billArea.setText(bill.generateBillText());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RetailStoreBillingSystemGUI());
    }
}
