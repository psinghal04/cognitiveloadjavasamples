package com.dev2next.cognitiveload;

import java.util.*;

public class DeeplyNestedControlStructures {

    /**
     * Processes a list of accounts and prints details of approved USD transfer
     * transactions.
     *
     * <p>
     * <b>Cognitive Complexity Issues:</b>
     * <ul>
     * <li>Deeply nested control structures (multiple levels of for-loops and
     * if-statements) make the code hard to follow.</li>
     * 
     * <li>Multiple conditional checks at each nesting level increase mental
     * effort required to understand the flow.</li>
     * 
     * <li>Logic is spread across several nested blocks, reducing readability
     * and maintainability.</li>
     * 
     * <li>Lack of early exits or guard clauses leads to increased indentation
     * and complexity.</li>
     * 
     * <li>Single responsibility principle is violated; the method handles
     * filtering, validation, and output together.</li>
     * 
     * </ul>
     * </p>
     *
     * @param accounts the list of accounts to process
     */
    void processTransactions(List<Account> accounts) {
        for (Account account : accounts) {
            if (account != null && account.isActive()) {
                for (Transaction txn : account.getTransactions()) {
                    if (txn != null && txn.getAmount() > 0) {
                        if ("USD".equals(txn.getCurrency())) {
                            if (txn.getType() == TransactionType.TRANSFER) {
                                for (Approval approval : txn.getApprovals()) {
                                    if (approval != null && approval.isManagerApproved()) {
                                        if (approval.getDate().after(txn.getDate())) {
                                            System.out.println("Account: " + account.getId()
                                                    + ", Transaction: " + txn.getId()
                                                    + ", Approved by: " + approval.getManagerId());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

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
        new DeeplyNestedControlStructures().processTransactions(accounts);
    }
}

class Account {

    private final String id;
    private final boolean active;
    private final List<Transaction> transactions;

    public Account(String id, boolean active, List<Transaction> transactions) {
        this.id = id;
        this.active = active;
        this.transactions = transactions;
    }

    public String getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}

class Transaction {

    private final String id;
    private final double amount;
    private final String currency;
    private final TransactionType type;
    private final Date date;
    private final List<Approval> approvals;

    public Transaction(String id, double amount, String currency, TransactionType type, Date date, List<Approval> approvals) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.date = date;
        this.approvals = approvals;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public TransactionType getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public List<Approval> getApprovals() {
        return approvals;
    }
}

class Approval {

    private final String managerId;
    private final boolean managerApproved;
    private final Date date;

    public Approval(String managerId, boolean managerApproved, Date date) {
        this.managerId = managerId;
        this.managerApproved = managerApproved;
        this.date = date;
    }

    public String getManagerId() {
        return managerId;
    }

    public boolean isManagerApproved() {
        return managerApproved;
    }

    public Date getDate() {
        return date;
    }
}

enum TransactionType {
    TRANSFER,
    DEPOSIT,
    WITHDRAWAL
}
