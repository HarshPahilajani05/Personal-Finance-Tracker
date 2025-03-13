import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PersonalFinanceTracker {

    static class Transaction {
        String description;
        double amount;
        boolean isIncome;

        Transaction(String description, double amount, boolean isIncome) {
            this.description = description;
            this.amount = amount;
            this.isIncome = isIncome;
        }
    }

    private JFrame frame;
    private JTextField fieldIncomeDesc, fieldIncomeAmt, fieldExpenseDesc, fieldExpenseAmt;
    private DefaultListModel<String> listModel;
    private JLabel labelTotalIncome, labelTotalExpense, labelNet;
    private ArrayList<Transaction> transactions;

    public PersonalFinanceTracker() {
        transactions = new ArrayList<>();

        // Initialize the main application window
        frame = new JFrame("My Personal Finance Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLayout(new BorderLayout());

        // Build and add top input panel
        frame.add(buildTopPanel(), BorderLayout.NORTH);

        // Center area with list of transactions
        listModel = new DefaultListModel<>();
        JList<String> listTransactions = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listTransactions);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for totals
        frame.add(buildBottomPanel(), BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel buildTopPanel() {
        // This panel will contain two rows: one for income, one for expenses
        JPanel panel = new JPanel(new GridLayout(2, 1));
        
        // Income row
        JPanel incomePanel = new JPanel();
        incomePanel.add(new JLabel("Income Desc:"));
        fieldIncomeDesc = new JTextField(8);
        incomePanel.add(fieldIncomeDesc);

        incomePanel.add(new JLabel("Amount:"));
        fieldIncomeAmt = new JTextField(5);
        incomePanel.add(fieldIncomeAmt);

        JButton buttonAddIncome = new JButton("Add Income");
        incomePanel.add(buttonAddIncome);
        buttonAddIncome.addActionListener(e -> addTransaction(true));

        // Expense row
        JPanel expensePanel = new JPanel();
        expensePanel.add(new JLabel("Expense Desc:"));
        fieldExpenseDesc = new JTextField(8);
        expensePanel.add(fieldExpenseDesc);

        expensePanel.add(new JLabel("Amount:"));
        fieldExpenseAmt = new JTextField(5);
        expensePanel.add(fieldExpenseAmt);

        JButton buttonAddExpense = new JButton("Add Expense");
        expensePanel.add(buttonAddExpense);
        buttonAddExpense.addActionListener(e -> addTransaction(false));

        panel.add(incomePanel);
        panel.add(expensePanel);

        return panel;
    }

    private JPanel buildBottomPanel() {
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        labelTotalIncome = new JLabel("Total Income: 0");
        labelTotalExpense = new JLabel("Total Expense: 0");
        labelNet = new JLabel("Net: 0");

        bottomPanel.add(labelTotalIncome);
        bottomPanel.add(labelTotalExpense);
        bottomPanel.add(labelNet);

        return bottomPanel;
    }

    private void addTransaction(boolean isIncome) {
        String desc;
        double amt;

        if (isIncome) {
            desc = fieldIncomeDesc.getText();
            amt = parseAmount(fieldIncomeAmt.getText());
            listModel.addElement("Income: " + desc + " - $" + amt);
        } else {
            desc = fieldExpenseDesc.getText();
            amt = parseAmount(fieldExpenseAmt.getText());
            listModel.addElement("Expense: " + desc + " - $" + amt);
        }

        transactions.add(new Transaction(desc, amt, isIncome));
        calculateTotals();
    }

    private double parseAmount(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            // Fallback to 0 if the input isn't a valid number
            return 0;
        }
    }

    private void calculateTotals() {
        double totalInc = 0;
        double totalExp = 0;

        for (Transaction t : transactions) {
            if (t.isIncome) {
                totalInc += t.amount;
            } else {
                totalExp += t.amount;
            }
        }

        labelTotalIncome.setText("Total Income: " + totalInc);
        labelTotalExpense.setText("Total Expense: " + totalExp);
        labelNet.setText("Net: " + (totalInc - totalExp));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PersonalFinanceTracker::new);
    }
}
