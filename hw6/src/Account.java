import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Account Class
 *
 * @author aguo36
 * @version 1.1
 */
public class Account {
    private List<Transaction> pastTransactions;

    /**
     * Constructor
     *
     * @param t pass in transactions
     */
    public Account(List<Transaction> t) {
        pastTransactions = new ArrayList<Transaction>(t);
    }

    /**
     * getter for Transaction
     *
     * @param n defines index in list
     * @return Transaction
     */
    public Transaction getTransaction(int n) {
        return pastTransactions.get(n);
    }

    /**
     * finds the transactions given a requirement
     *
     * @param predicate pass in condition
     * @return List of Transactions
     */
    public List<Transaction> findTransactionsByPredicate(
            Predicate<Transaction> predicate) {
        List<Transaction> filteredList = pastTransactions
                .stream()
                .filter(predicate)
                .collect(ArrayList::new, ArrayList::add,
                        ArrayList::addAll);
        return filteredList;
    }

    /**
     * Gets transactions by amount
     *
     * @param amount specifies amount requirement
     * @return new list
     */
    public List<Transaction> getTransactionsByAmount(double amount) {
        InnerClass testAmount = new InnerClass(amount);
        return findTransactionsByPredicate(testAmount);
    }

    protected class InnerClass implements Predicate<Transaction> {
        private double innerAmount;

        /**
         * constructor in which you pass in variable amount
         *
         * @param amount specifies amount
         */
        public InnerClass(double amount) {
            innerAmount = amount;
        }

        /**
         * overrides method test from interface
         *
         * @param p pass in a transaction
         * @return
         */
        public boolean test(Transaction p) {
            return (p.getAmount() == innerAmount);
        }
    }

    /**
     * filters for withdrawls
     *
     * @return new list
     */
    public List<Transaction> getWithdrawals() {
        return findTransactionsByPredicate(
                new Predicate<Transaction>() {
                    public boolean test(Transaction p) {
                        return p.getType()
                                .name()
                                .equals("WITHDRAWAL");
                    }
                });
    }

    /**
     * filters for deposits
     *
     * @return new list
     */
    public List<Transaction> getDeposits() {
        return findTransactionsByPredicate(
                p -> p.getType().name().equals("DEPOSIT")
        );
    }

    /**
     * filter for comment length
     *
     * @param length specify length
     * @return new list
     */
    public List<Transaction> getTransactionsWithCommentLongerThan(
            int length) {
        return findTransactionsByPredicate(
            p -> {
                if (!p.hasComment()) {
                    return false;
                }
                return p.getComment().get().length() > length;
            });
    }

    /**
     * filter for trans. with comments
     *
     * @return new list
     */
    public List<Transaction> getTransactionsWithComment() {
        return findTransactionsByPredicate(
                Transaction::hasComment);
    }

    /**
     * getter method for transactions
     *
     * @return new list
     */
    public List<Transaction> getPastTransactions() {
        return pastTransactions;
    }
}
