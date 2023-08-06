public class Payment {
    private int orderId;
    private double amount;
    private boolean isPaid;

    public Payment(int orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
        this.isPaid = false;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void markAsPaid() {
        isPaid = true;
    }
}
