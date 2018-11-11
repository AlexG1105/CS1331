import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Account {
    private List<Transaction> pastTransactions;

    public Account(List<Transaction> t) {
        pastTransactions = t; //unsure
        //pastTransactions = new ArrayList<Transaction>(t);
    }

    public Transaction getTransaction(int n) {
        return pastTransactions.get(n);
    }

    public List<Transaction> findTransactionsByPredicate(
            Predicate<Transaction> predicate) {
        List<Transaction> filteredList = pastTransactions
                .stream()
                .filter(predicate)
                .collect(ArrayList::new, ArrayList::add,
                        ArrayList::addAll);
        return filteredList;
    }

    public List<Transaction> getTransactionsByAmount(double amount) {
        InnerClass testAmount = new InnerClass(amount);
        return findTransactionsByPredicate(testAmount);
    }

    protected class InnerClass implements Predicate<Transaction> {
        double innerAmount;

        public InnerClass(double amount) {
            innerAmount = amount;
        }

        public boolean test(Transaction p) {
            return (p.getAmount() == innerAmount);
        }
    }

    public List<Transaction> getWithdrawls() {
        return findTransactionsByPredicate(
                new Predicate<Transaction>() {
                    public boolean test(Transaction p) {
                        return p.getType()
                                .name()
                                .equals("WITHDRAWAL");
                    }
                });
    }

    public List<Transaction> getDeposits() {
        return findTransactionsByPredicate(
                p -> p.getType().name().equals("DEPOSIT")
        );
    }

    public List<Transaction> getTransactionsWithCommentLongerThan(
            int length) {
        return findTransactionsByPredicate(
                p -> p.getComment().get().length() > length);
    }

    public List<Transaction> getTransactionsWithComment() {
        return findTransactionsByPredicate(
                Transaction::hasComment);
    }

    public List<Transaction> getPastTransactions() {
        return pastTransactions;
    }
}
