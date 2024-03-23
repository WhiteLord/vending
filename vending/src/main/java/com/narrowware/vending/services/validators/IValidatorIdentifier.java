package com.narrowware.vending.services.validators;

import com.narrowware.vending.models.Identifier;

public interface IValidatorIdentifier {
    boolean isValidIdentifier(String identifier);
    boolean isValidIdentifier(Identifier i);
}
