package com.narrowware.vending.controllers;

import com.narrowware.vending.dto.IdentifierRequest;
import com.narrowware.vending.dto.ProductDTO;
import com.narrowware.vending.dto.VendingItemDTO;
import com.narrowware.vending.dto.VendingPurchaseReponse;
import com.narrowware.vending.entities.VendingItem;
import com.narrowware.vending.exceptions.InvalidIdentifierException;
import com.narrowware.vending.services.PaymentService;
import com.narrowware.vending.services.VendingService;
import com.narrowware.vending.services.validators.IValidatorIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vending")
public class VendingController {

    @Autowired
    private VendingService vendingService;
    @Autowired
    private IValidatorIdentifier validatorIdentifier;
    @Autowired
    private PaymentService paymentService;

    // Returns entity object ONLY FOR DEMO PURPOSES
    @PostMapping("/vendingItem")
    @CrossOrigin(origins = "*")
    public VendingItem addItem(@Valid @RequestBody VendingItemDTO vendingItemDTO) {
        return this.vendingService.saveVendingItem(vendingItemDTO);
    }

    @PutMapping("/vendingItem/{id}")
    @CrossOrigin(origins = "*")
    public VendingItem updateItem(@PathVariable Long id, @Valid @RequestBody VendingItem updatedItem) {
        return vendingService.updateVendingItem(id, updatedItem);
    }

    @DeleteMapping("/vendingItem/{id}")
    @CrossOrigin(origins = "*")
    public void deleteProduct(@PathVariable Long id) {
        vendingService.deleteVendingItem(id);
    }

    @PostMapping("/addProduct")
    @CrossOrigin(origins = "*")
    public void addProduct(@Valid @RequestBody ProductDTO productDTO) {
        this.vendingService.saveProduct(productDTO);
    }

    @PostMapping("/purchase")
    @CrossOrigin(origins = "*")
    public VendingPurchaseReponse buy(@Valid @RequestBody IdentifierRequest identifierDto) {
        // If the identifier is invalid, fail fast
        vendingService.checkIfValidIdentifierOrBreakFlow(identifierDto.getItem());
        final List<VendingItem> products = vendingService.getVendingItems();
        // Check if a vending item is present in the UI
        // Could also throw exception by directly calling the repository
        if (products != null && products.size() > 0) {
            final VendingItem vendingItem = vendingService.findSelectedProduct(products, identifierDto.getItem());
            if (vendingItem == null || vendingItem.getQuantity() == 0) {
                // Let the users know that the item is not present, could be granular
                throw new InvalidIdentifierException();
            } else {
                return this.vendingService.buyVendingItem(vendingItem);
            }
        } else {
            throw new InvalidIdentifierException();
        }
    }

    @GetMapping("/getAll")
    @CrossOrigin(origins = "*")
    public List<VendingItem> getAllItems() {
        return this.vendingService.getVendingItems();
    }
}
