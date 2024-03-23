package com.narrowware.vending.dto;

import lombok.Data;

/**
 * This is the object that is sent from the UI to the BE
 * when the user wants to "buy" an item.
 */
@Data
public class IdentifierRequest {

    // This field maps to com.narrowware.vending.models.Identifier entry
    private String item;
}
