package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            boolean rsl = false;
            if (getById(account.id()).isEmpty()) {
                accounts.put(account.id(), account);
                rsl = true;
            }
            return rsl;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            boolean rsl = false;
            if (getById(account.id()).isPresent()) {
                accounts.replace(account.id(), account);
                rsl = true;
            }
            return rsl;
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            boolean rsl = false;
            if (getById(id).isPresent()) {
                accounts.remove(id);
                rsl = true;
            }
            return rsl;
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            boolean rsl = false;
            if (getById(fromId).isPresent()
                    && getById(toId).isPresent()
                    && isBalanceSufficient(fromId, amount)) {
                update(new Account(fromId, accounts.get(fromId).amount() - amount));
                update(new Account(toId, accounts.get(toId).amount() + amount));
                rsl = true;
            }
            return rsl;
        }
    }

    private boolean isBalanceSufficient(int id, int amount) {
        return accounts.get(id).amount() >= amount;
    }
}