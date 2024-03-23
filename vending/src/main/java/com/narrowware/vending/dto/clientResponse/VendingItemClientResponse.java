package com.narrowware.vending.dto.clientResponse;

import com.narrowware.vending.dto.ProductDTO;
import lombok.Data;

@Data
public class VendingItemClientResponse {
    private int price;
    ProductDTO product;
}
