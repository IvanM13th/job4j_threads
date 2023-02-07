package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            return accounts.putIfAbsent(account.id(), account) != null;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            return accounts.replace(account.id(), account) != null;
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            return accounts.remove(id) != null;
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
            Optional<Account> from = getById(fromId);
            Optional<Account> to = getById(toId);
            if (from.isPresent()
                    && to.isPresent()
                    && isBalanceSufficient(fromId, amount)) {
                update(new Account(fromId, from.get().amount() - amount));
                update(new Account(toId, to.get().amount() + amount));
                rsl = true;
            }
            return rsl;
        }
    }

    private boolean isBalanceSufficient(int id, int amount) {
        return accounts.get(id).amount() >= amount;
    }
}