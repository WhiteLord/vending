package com.narrowware.vending.dto;

import com.narrowware.vending.dto.clientResponse.VendingItemClientResponse;
import com.narrowware.vending.models.money.Coin;
import lombok.Data;
import java.util.List;

@Data
public class VendingPurchaseReponse {
    private VendingItemClientResponse vendingItem;
    private List<Coin> change;
}
