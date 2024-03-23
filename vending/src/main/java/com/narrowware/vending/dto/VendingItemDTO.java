package com.narrowware.vending.dto;

import com.narrowware.vending.models.Identifier;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * This is the object that is used by the administrator
 * to create a vending item
 */
@Data
public class VendingItemDTO {

    @NotNull
    private Identifier identifier;
    @NotNull
    private int quantity;
    @NotNull
    private int price;
    Optional<ProductDTO> product;
}
