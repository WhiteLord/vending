package com.narrowware.vending.dto;

// import com.narrowware.vending.models.money.Banknote;
import com.narrowware.vending.models.money.Coin;
import lombok.Data;

import java.util.Optional;

@Data
public class PaymentRequest {
    private Optional<Coin> coin;
}
