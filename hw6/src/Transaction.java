import java.util.Optional;

public class Transaction {
    private TransactionType type;
    private double amount;
    private Optional<String> comment;

    public Transaction(TransactionType type, double amount) {
        this.type = type;
        this.amount = amount;
        comment = Optional.empty();
    }

    public Transaction(TransactionType type, double amount, String c) {
        this.type = type;
        this.amount = amount;
        comment = Optional.of(c);
    }

    public boolean hasComment() {
        return comment.isPresent();
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Optional<String> getComment() {
        return comment;
    }
}
