package com.narrowware.vending.services.validators;

import com.narrowware.vending.models.Identifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * The service iterates over the enum values and checks if the provided identifier
 * matches the currently available identifiers.
 */
@Service
@Primary
public class ValidatorIdentifier implements IValidatorIdentifier{

    @Override
    public boolean isValidIdentifier(String identifier) {
        return this.checkValues(identifier);
    }

    @Override
    public boolean isValidIdentifier(Identifier identifier) {
        return this.checkValues(identifier.toString());
    }

    private boolean checkValues(String identifier) {
        if (identifier.isEmpty() || identifier.isBlank()) {
            return false;
        }
        for (Identifier i: Identifier.values()) {
            if (i.toString().equals(identifier)) {
                return true;
            }
        }
        return false;
    }
}
