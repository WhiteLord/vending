package com.narrowware.vending.services;

import com.narrowware.vending.dto.PaymentRequest;
import com.narrowware.vending.exceptions.MachineNeedsMaintenanceException;
import com.narrowware.vending.models.money.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PaymentService {

    @Autowired
    private AllowanceService allowanceService;

    // A map that will hold the currently available sum of coins
    private Map<Coin, Integer> allCoins = new HashMap<>();
    private List<Coin> sessionCoins = new LinkedList<>();

    public int getSessionBalance() {
        return sessionCoins.stream().mapToInt(Coin::getValue).sum();
    }

    private int getBalance(final Map<Coin, Integer> coins) {
        if (coins == null || coins.isEmpty()) {
            return 0;
        }
        int sum = 0;
        for (Map.Entry<Coin, Integer> entry : coins.entrySet()) {
            Coin coin = entry.getKey();
            int quantity = entry.getValue();
            sum += coin.getValue() * quantity;
        }
        return sum;
    }

    public Map<Coin, Integer> getAllCoins() {
        return this.allCoins;
    }

    public List<Coin> addToSessionBalance(PaymentRequest paymentRequest) {
        if (Integer.MAX_VALUE * 0.75 <= getSessionBalance()) {
            // Add a way to inform the manager that the value is nearing the limit
            // It might be an automated call, SMS, Email, whatever
            paymentRequest.getCoin().ifPresent(coin -> {
                sessionCoins.add(coin);
            });
        }
        if (Integer.MAX_VALUE * 0.90 <= getSessionBalance()) {
            // Escalate to director and owner
            paymentRequest.getCoin().ifPresent(coin -> {
                sessionCoins.add(coin);
            });
        }
        if (Integer.MAX_VALUE * 0.95 <= getSessionBalance()) {
            // Log information to the user that the machine needs maintenance
            // Do not process money, fail operation
            throw new MachineNeedsMaintenanceException();
        } else {
            paymentRequest.getCoin().ifPresent(coin -> {
                sessionCoins.add(coin);
            });
        }
        return sessionCoins;
    }

    /**
     * Returns the currently available sessionCoins to the client
     */
    public List<Coin> getRefundCoins() {
        return this.sessionCoins;
    }

    public List<Coin> getChange(int priceForItem) {
        // Create a copy of allCoins map
        Map<Coin, Integer> allCoinsCopy = new HashMap<>(allCoins);

        // Add a copy of sessionCoins to allCoinsCopy
        List<Coin> sessionCoinsCopy = new ArrayList<>(sessionCoins);
        for (Coin coin : sessionCoinsCopy) {
            allCoinsCopy.put(coin, allCoinsCopy.getOrDefault(coin, 0) + 1);
        }
        // Find the sum that we need to return, obviously if it's < 0
        final int sumToBeReturned = this.getSessionBalance() - priceForItem;
        List<Coin> changeToBeReturned = findOptimalChange(sumToBeReturned, allCoinsCopy);
        this.emptySessionCoins();
        return changeToBeReturned;
    }

    /**
     * The goal of the function is to sort the list of all coins (stored + session).
     * We start with the biggest denomination and work our way to the smallest
     * EG: Provided 600 -> 3*200, a product costs 450. We should return 150.
     * The biggest denominator is 100 (LEV), so if no LEV is present, go to 50st
     * @param amount - The amount of money to be returned
     * @param allCoinsCopy - a copy of all coins
     * @return
     */
    private List<Coin> findOptimalChange(int amount, Map<Coin, Integer> allCoinsCopy) {
        List<Coin> change = new ArrayList<>();
        List<Coin> sortedCoins = new ArrayList<>(allCoinsCopy.keySet());
        sortedCoins.sort(Comparator.comparingInt(Coin::getValue).reversed());

        for (Coin coin : sortedCoins) {
            int coinValue = coin.getValue();
            while (amount >= coinValue && allCoinsCopy.getOrDefault(coin, 0) > 0) {
                change.add(coin);
                amount -= coinValue;
                allCoinsCopy.put(coin, allCoinsCopy.get(coin) - 1);
            }
        }
        updateAvailableCoins(allCoinsCopy);
        return change;
    }

    private void updateAvailableCoins(Map<Coin, Integer> allCoinsCopy) {
        this.allCoins = allCoinsCopy;
    }

    public void emptySessionCoins() {
        boolean isEnabled = allowanceService.issClearSessionCoinsEnabled();
        if (isEnabled) {
            this.sessionCoins = new ArrayList<>();
        }
    }
}
