package com.dev2next.cognitiveload;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class BetterControlStructures {

    /**
     * Improved method to process a list of accounts and log details of approved
     * USD transfer transactions. This version reduces cognitive complexity by
     * using early exits and helper methods. It also gives a better and more
     * meaningful name to the method.
     *
     * @param accounts
     */
    void processAndLogApprovedUsdTransfers(List<Account> accounts) {
        if (accounts == null) {
            return;
        }
        for (Account account : accounts) {
            processAccount(account);
        }
    }

    private void processAccount(Account account) {
        if (!isValidAccount(account)) {
            return;
        }
        for (Transaction txn : account.getTransactions()) {
            processTransaction(account, txn);
        }
    }

    private void processTransaction(Account account, Transaction txn) {
        if (!isValidTransaction(txn)) {
            return;
        }
        if (!isUsdTransfer(txn)) {
            return;
        }
        for (Approval approval : txn.getApprovals()) {
            processApproval(account, txn, approval);
        }
    }

    private void processApproval(Account account, Transaction txn, Approval approval) {
        if (!isValidApproval(approval, txn)) {
            return;
        }
        logApproval(account, txn, approval);
    }

    private boolean isValidAccount(Account account) {
        return account != null && account.isActive();
    }

    private boolean isValidTransaction(Transaction txn) {
        return txn != null && txn.getAmount() > 0;
    }

    private boolean isUsdTransfer(Transaction txn) {
        return "USD".equals(txn.getCurrency()) && txn.getType() == TransactionType.TRANSFER;
    }

    private boolean isValidApproval(Approval approval, Transaction txn) {
        return approval != null && approval.isManagerApproved() && approval.getDate().after(txn.getDate());
    }

    private void logApproval(Account account, Transaction txn, Approval approval) {
        LOGGER.log(java.util.logging.Level.INFO,
                "Account: {0}, Transaction: {1}, Approved by: {2}, Approved by: {3}",
                new Object[]{account.getId(), txn.getId(), approval.getManagerId(), approval.getManagerId()});
    }

    private static final Logger LOGGER = Logger.getLogger(BetterControlStructures.class.getName());

    public static void main(String[] args) {
        // Sample data setup
        List<Account> accounts = Arrays.asList(
                new Account("A001", true, Arrays.asList(
                        new Transaction("T100", 500.0, "USD", TransactionType.TRANSFER, new Date(), Arrays.asList(
                                new Approval("MGR1", true, new Date(System.currentTimeMillis() + 10000)),
                                new Approval("MGR2", false, new Date())
                        )),
                        new Transaction("T101", 200.0, "EUR", TransactionType.DEPOSIT, new Date(), Collections.emptyList())
                )),
                new Account("A002", false, Collections.emptyList()),
                new Account("A003", true, Arrays.asList(
                        new Transaction("T200", 1000.0, "USD", TransactionType.TRANSFER, new Date(), Arrays.asList(
                                new Approval("MGR3", true, new Date(System.currentTimeMillis() + 20000))
                        ))
                ))
        );
        new BetterControlStructures().processAndLogApprovedUsdTransfers(accounts);
    }

}
