package org.sdia.ebankingbackend.exceptions;

public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException(String s) {
        super(s);
    }
}
