import java.util.Optional;

/**
 * Transaction class
 *
 * @author aguo36
 * @version 1.1
 */
public class Transaction {
    private TransactionType type;
    private double amount;
    private Optional<String> comment;

    /**
     * Constructor
     * @param type enum
     * @param amount give transaction amount
     */
    public Transaction(TransactionType type, double amount) {
        this.type = type;
        this.amount = amount;
        comment = Optional.empty();
    }

    /**
     *
     * @param type enum
     * @param amount give transaction amount
     * @param c pass in comment
     */
    public Transaction(TransactionType type, double amount, String c) {
        this.type = type;
        this.amount = amount;
        comment = Optional.of(c);
    }

    /**
     * get method for whether comment exists
     * @return boolean
     */
    public boolean hasComment() {
        return comment.isPresent();
    }

    /**
     * get method for type
     * @return TransactionType
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * get method for amount
     * @return double
     */
    public double getAmount() {
        return amount;
    }

    /**
     * get method for comment
     * @return Optional of type String
     */
    public Optional<String> getComment() {
        return comment;
    }
}
