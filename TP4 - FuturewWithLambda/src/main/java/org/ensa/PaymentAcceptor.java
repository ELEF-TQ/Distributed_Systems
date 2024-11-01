package org.ensa;

import java.util.concurrent.Callable;

public class PaymentAcceptor implements Callable<String> {


    private Boolean isInventoryPresent;
    private int t;

    public PaymentAcceptor(Boolean isInventoryPresent, int t) {
        this.isInventoryPresent = isInventoryPresent;
        this.t = t;
    }

    private void PaymentCall(int t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Le thread a été interrompu : " + e.getMessage());
        }
    }

    @Override
    public String call() {
        PaymentCall(t);
        return isInventoryPresent ? "Payment Accepted" : "Payment not Accepted";
    }
}
