import java.util.*;
public class Finance_Tracker {
    static ArrayList<Transaction> transactions;
    static ArrayList<Budget> budgets;
    static Scanner N = new Scanner(System.in);

    public Finance_Tracker() {
        transactions = new ArrayList<>();
        budgets = new ArrayList<>();
    }

    public class Transaction {
        String transactionID;
        double amount;
        String type;
        String category;
        Date date;

        public Transaction(String transactionID, double amount, String type, String category, Date date) {
            this.transactionID = transactionID;
            this.amount = amount;
            this.type = type;
            this.category = category;
            this.date = date;
        }

        public String toString() {
            return "Transaction ID: " + transactionID + ", Amount: " + amount + ", Type: " + type + ", Category: " + category + ", Date: " + date;
        }
    }

    public class Budget {
        String category;
        double monthlyBudget;
        double spentAmount;

        public Budget(String category, double monthlyBudget) {
            this.category = category;
            this.monthlyBudget = monthlyBudget;
            this.spentAmount = 0;
        }

        public String toString() {
            return "Category: " + category + ", Monthly Budget: " + monthlyBudget + ", Spent Amount: " + spentAmount;
        }
    }

    public void add_Transaction_From_Input() {
        System.out.println("Enter transaction details:");
        System.out.print("Transaction ID: ");
        String transactionID = N.next();
        System.out.print("Amount: ");
        double amount = N.nextDouble();
        System.out.print("Type (income or expense): ");
        String type = N.next();
        System.out.print("Category: ");
        String category = N.next();
        Date date = new Date();
        Transaction transaction = new Transaction(transactionID, amount, type, category, date);
        transactions.add(transaction);
        update_Budgets();
    }

    public void set_Budget_From_Input() {
        System.out.println("Enter budget details:");
        System.out.print("Category: ");
        String category = N.next();
        System.out.print("Monthly Budget: ");
        double monthlyBudget = N.nextDouble();
        Budget budget = new Budget(category, monthlyBudget);
        budgets.add(budget);
        update_Budgets();
    }

    public void remove_Transaction(String transactionID) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).transactionID.equals(transactionID)) {
                transactions.remove(i);
                update_Budgets();
                break;
            }
        }
    }

    public void update_Transaction(String transactionID, double amount, String type, String category, Date date) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).transactionID.equals(transactionID)) {
                transactions.get(i).amount = amount;
                transactions.get(i).type = type;
                transactions.get(i).category = category;
                transactions.get(i).date = date;
                update_Budgets();
                break;
            }
        }
    }

    public void update_Budgets() {
        for (int i = 0; i < budgets.size(); i++) {
            double totalSpent = calculate_Total_Spent(budgets.get(i).category);
            budgets.get(i).spentAmount = totalSpent;
        }
    }

    public double calculate_Total_Spent(String category) {
        double totalSpent = 0.0;
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).category.equals(category) && transactions.get(i).type.equals("expense")) {
                totalSpent += transactions.get(i).amount;
            }
        }
        return totalSpent;
    }

    public void generate_Report() {
        System.out.println("Transactions Report:");
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println(transactions.get(i));
        }
    }

    public void display_Budget_Status() {
        System.out.println("Budget Status:");
        for (int i = 0; i < budgets.size(); i++) {
            System.out.println(budgets.get(i));
        }
        notify_User();
    }

    public void notify_User() {
        for (int i = 0; i < budgets.size(); i++) {
            if (budgets.get(i).spentAmount > budgets.get(i).monthlyBudget) {
                System.out.println("Alert: You have exceeded your budget for " + budgets.get(i).category);
            }
        }
    }

    public static void main(String[] args) {
        Finance_Tracker tracker = new Finance_Tracker();
        boolean exit = false;

        while (!exit) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Transaction");
            System.out.println("2. Set Budget");
            System.out.println("3. Generate Report");
            System.out.println("4. Display Budget Status");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int num = N.nextInt();

            switch (num) {
                case 1:
                    tracker.add_Transaction_From_Input();
                    break;
                case 2:
                    tracker.set_Budget_From_Input();
                    break;
                case 3:
                    tracker.generate_Report();
                    break;
                case 4:
                    tracker.display_Budget_Status();
                    break;
                case 5:
                    exit = true;
                    System.out.print("Confirm Exit(Y/N):");
                    char confirm=N.next().charAt(0);
                    if(confirm=='N' || confirm=='n') 
                    	exit=false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        System.out.println("Exited Finance Tracker..");
    }
}
