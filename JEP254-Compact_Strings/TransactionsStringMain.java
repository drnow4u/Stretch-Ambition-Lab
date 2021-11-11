import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TransactionsStringMain {

    public static final Random RANDOM = new Random();

    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        while(true){
            Transaction transaction = randomTransaction();
            transactions.add(transaction);
            System.out.println(transactions.size());
        }
    }

    private static Transaction randomTransaction() {
        final Transaction transaction = new Transaction();
        transaction.transactionId = UUID.randomUUID().toString();
        transaction.amount = BigDecimal.valueOf(RANDOM.nextLong());
        transaction.transactionInformation = randomString(200);
        return transaction;
    }

    private static String randomString(int size) {
        byte[] array = new byte[size];
        RANDOM.nextBytes(array);
        return  new String(array, Charset.forName("UTF-8"));
    }

    private static class Transaction {
        public String transactionId;
        public BigDecimal amount;
        public String currency;
        public String status;
        public LocalDateTime bookingDateTime;
        public LocalDateTime valueDateTime;
        public String transactionInformation;
    }

}
