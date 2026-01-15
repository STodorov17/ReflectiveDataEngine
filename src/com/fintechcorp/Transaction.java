package com.fintechcorp;

import com.fintechcorp.annotations.*;

@FileSource(delimiter = "|")
public class Transaction {

    @Column(index = 0, name = "id")
    @NotNull(message = "Transaction Id cannot be empty")
    @Range(min = 1, max = 999999, message = "Id must be between 1 and 999999")
    private int id;

    @Column(index = 1, name = "amount")
    @Range(min = 0, max = 100000, message = "Amount must be higher than -1 and under 100k")
    private double amount;

    @Column(index = 2, name = "date")
    @Regex(pattern = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date must be in Y-M-D format")
    private String date; // Ползваме String, за да тестваме Regex

    public Transaction() {}

    @Override
    public String toString() {
        return "Transaction{id=" + id + ", amount=" + amount + ", date='" + date + "'}";
    }
}