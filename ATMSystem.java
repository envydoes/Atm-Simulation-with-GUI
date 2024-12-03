import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import javax.swing.*;

class Account {
    private String username;
    private String pin;
    private double balance;
    private List<String> transactionHistory; // List to store transaction history

    public Account(String username, String pin) {
        this.username = username;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>(); // Initialize the transaction history list
    }

    public String getUsername() {
        return username;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String newPin) {
        this.pin = newPin;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            String transaction = "Deposited: P" + amount + " on " + getCurrentDateTime();
            transactionHistory.add(transaction); // Add deposit transaction to history
            JOptionPane.showMessageDialog(null, "Successfully deposited: P" + amount);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            String transaction = "Withdrew: P" + amount + " on " + getCurrentDateTime();
            transactionHistory.add(transaction); // Add withdrawal transaction to history
            JOptionPane.showMessageDialog(null, "Successfully withdrew: P" + amount);
        } else if (amount > balance) {
            JOptionPane.showMessageDialog(null, "Insufficient funds.");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid withdrawal amount.");
        }
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory; // Return the transaction history
    }

    // Method to get the current date and time formatted as a string
    private String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}

public class ATMSystem {
    private static Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        JFrame loginFrame = new JFrame("ATM System - Login");

        // Set the image icon for the frame
        ImageIcon icon = new ImageIcon("atm_icon.jpg");  // Make sure the image is in the correct path
        loginFrame.setIconImage(icon.getImage());

        JLabel welcomeLabel = new JLabel("         ATM Simulation ");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 18));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("PIN:");
        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        JButton createButton = new JButton("Create Account");

        loginFrame.setLayout(null);
        welcomeLabel.setBounds(50, 10, 250, 30);
        userLabel.setBounds(30, 50, 100, 30);
        passLabel.setBounds(30, 90, 100, 30);
        userField.setBounds(120, 50, 150, 30);
        passField.setBounds(120, 90, 150, 30);
        loginButton.setBounds(50, 130, 100, 30);
        createButton.setBounds(160, 130, 150, 30);

        loginFrame.add(welcomeLabel);
        loginFrame.add(userLabel);
        loginFrame.add(passLabel);
        loginFrame.add(userField);
        loginFrame.add(passField);
        loginFrame.add(loginButton);
        loginFrame.add(createButton);

        // Set tooltips
        loginButton.setToolTipText("Click to log in to your account");
        createButton.setToolTipText("Click to create a new account");

        loginFrame.setSize(350, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
        loginFrame.setResizable(false);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String pin = new String(passField.getPassword());

            Account account = accounts.get(username);

            if (account != null && account.getPin().equals(pin)) {
                JOptionPane.showMessageDialog(loginFrame, "Login Successful!");
                loginFrame.dispose();
                openMenu(account);
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid Credentials, Try Again!");
            }
        });

        createButton.addActionListener(e -> openCreateAccountFrame());
    }

    private static void openCreateAccountFrame() {
        JFrame createFrame = new JFrame("ATM System - Create Account");

        // Set the image icon for the frame
        ImageIcon icon = new ImageIcon("atm_icon.jpg");  // Make sure the image is in the correct path
        createFrame.setIconImage(icon.getImage());

        JLabel userLabel = new JLabel("New Username:");
        JLabel passLabel = new JLabel("New PIN:");
        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JButton createButton = new JButton("Create");

        createFrame.setLayout(null);
        userLabel.setBounds(30, 30, 120, 30);
        passLabel.setBounds(30, 70, 120, 30);
        userField.setBounds(150, 30, 150, 30);
        passField.setBounds(150, 70, 150, 30);
        createButton.setBounds(100, 120, 100, 30);

        createFrame.add(userLabel);
        createFrame.add(passLabel);
        createFrame.add(userField);
        createFrame.add(passField);
        createFrame.add(createButton);

        // Set tooltip for createButton
        createButton.setToolTipText("Click to create a new account");

        createFrame.setSize(350, 200);
        createFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createFrame.setVisible(true);

        createButton.addActionListener(e -> {
            String username = userField.getText();
            String pin = new String(passField.getPassword());

            // Validate username (letters only)
            if (!username.matches("[a-zA-Z]+")) {
                JOptionPane.showMessageDialog(createFrame, "Username must only contain letters!");
                return;
            }

            // Validate PIN (must be exactly 6 digits)
            if (!pin.matches("\\d{6}")) {
                JOptionPane.showMessageDialog(createFrame, "PIN must be exactly 6 digits!");
                return;
            }

            // Check if fields are empty
            if (username.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(createFrame, "Please fill in all fields!");
            } else if (accounts.containsKey(username)) {
                JOptionPane.showMessageDialog(createFrame, "Account already exists!");
            } else {
                Account newAccount = new Account(username, pin);
                accounts.put(username, newAccount);
                JOptionPane.showMessageDialog(createFrame, "Account Created Successfully!");
                createFrame.dispose();
            }
        });
    }

    private static void openMenu(Account account) {
        JFrame menuFrame = new JFrame("ATM System - Menu");

        // Set the image icon for the frame
        ImageIcon icon = new ImageIcon("atm_icon.jpg");  // Make sure the image is in the correct path
        menuFrame.setIconImage(icon.getImage());

        // Add ATM Simulation label to menu
        JLabel welcomeLabel = new JLabel("      ATM Simulation ");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 18));
        welcomeLabel.setBounds(40, 5, 250, 30);

        JButton balanceButton = new JButton("Balance Inquiry");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton changePinButton = new JButton("Change PIN");
        JButton transactionMenuButton = new JButton("Transaction History");
        JButton logoutButton = new JButton("Logout");

        menuFrame.setLayout(null);
        balanceButton.setBounds(50, 30, 150, 30);
        depositButton.setBounds(50, 70, 150, 30);
        withdrawButton.setBounds(50, 110, 150, 30);
        changePinButton.setBounds(50, 150, 150, 30);
        transactionMenuButton.setBounds(50, 190, 150, 30);
        logoutButton.setBounds(50, 230, 150, 30);

        menuFrame.add(welcomeLabel);  // Add the ATM Simulation label to the frame
        menuFrame.add(balanceButton);
        menuFrame.add(depositButton);
        menuFrame.add(withdrawButton);
        menuFrame.add(changePinButton);
        menuFrame.add(transactionMenuButton);
        menuFrame.add(logoutButton);

        // Set tooltips
        balanceButton.setToolTipText("Click to check your account balance");
        depositButton.setToolTipText("Click to deposit money into your account");
        withdrawButton.setToolTipText("Click to withdraw money from your account");
        changePinButton.setToolTipText("Click to change your account PIN");
        transactionMenuButton.setToolTipText("Click to view or manage your transaction history");
        logoutButton.setToolTipText("Click to log out of your account");

        menuFrame.setSize(250, 350);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);

        balanceButton.addActionListener(e -> JOptionPane.showMessageDialog(menuFrame, "Balance: P" + account.getBalance()));

        depositButton.addActionListener(e -> {
            String amountString = JOptionPane.showInputDialog(menuFrame, "Enter deposit amount:");
            try {
                double amount = Double.parseDouble(amountString);
                account.deposit(amount);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(menuFrame, "Invalid amount. Please enter a number.");
            }
        });

        withdrawButton.addActionListener(e -> {
            String amountString = JOptionPane.showInputDialog(menuFrame, "Enter withdrawal amount:");
            try {
                double amount = Double.parseDouble(amountString);
                account.withdraw(amount);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(menuFrame, "Invalid amount. Please enter a number.");
            }
        });

        changePinButton.addActionListener(e -> {
            String newPin = JOptionPane.showInputDialog(menuFrame, "Enter new PIN (6 digits):");
            if (newPin != null && newPin.matches("\\d{6}")) {
                account.setPin(newPin);
                JOptionPane.showMessageDialog(menuFrame, "PIN changed successfully!");
            } else {
                JOptionPane.showMessageDialog(menuFrame, "Invalid PIN. It must be exactly 6 digits.");
            }
        });

        transactionMenuButton.addActionListener(e -> openTransactionMenu(account, menuFrame));

        logoutButton.addActionListener(e -> {
            menuFrame.dispose();
            main(null); // Restart the program (Login screen)
        });
    }

    private static void openTransactionMenu(Account account, JFrame parentFrame) {
        JFrame transactionFrame = new JFrame("Transaction Menu");

        // Set the image icon for the frame
        ImageIcon icon = new ImageIcon("atm_icon.jpg");  // Make sure the image is in the correct path
        transactionFrame.setIconImage(icon.getImage());

        JLabel label = new JLabel("Transaction History:");
        label.setFont(new Font("Serif", Font.BOLD, 18));
        label.setBounds(20, 10, 300, 30);

        JTextArea transactionArea = new JTextArea(10, 30);
        transactionArea.setEditable(false);

        // Display the transaction history
        List<String> history = account.getTransactionHistory();
        if (history.isEmpty()) {
            transactionArea.setText("No transactions yet.");
        } else {
            StringBuilder historyText = new StringBuilder();
            for (String transaction : history) {
                historyText.append(transaction).append("\n");
            }
            transactionArea.setText(historyText.toString());
        }

        JScrollPane scrollPane = new JScrollPane(transactionArea);
        scrollPane.setBounds(20, 50, 400, 200);

        JButton backButton = new JButton("Back");
        backButton.setBounds(170, 260, 100, 30);

        transactionFrame.setLayout(null);
        transactionFrame.add(label);
        transactionFrame.add(scrollPane);
        transactionFrame.add(backButton);

        transactionFrame.setSize(450, 350);
        transactionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        transactionFrame.setVisible(true);

        backButton.addActionListener(e -> {
            transactionFrame.dispose();
            parentFrame.setVisible(true);  // Return to the main menu
        });
    }
}
