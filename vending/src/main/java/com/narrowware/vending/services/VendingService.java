package com.narrowware.vending.services;

import com.narrowware.vending.dto.ProductDTO;
import com.narrowware.vending.dto.VendingItemDTO;
import com.narrowware.vending.dto.VendingPurchaseReponse;
import com.narrowware.vending.dto.clientResponse.VendingItemClientResponse;
import com.narrowware.vending.entities.Product;
import com.narrowware.vending.entities.VendingItem;
import com.narrowware.vending.exceptions.ExceedingMaximumVendingSpaceException;
import com.narrowware.vending.exceptions.InsufficientFundsException;
import com.narrowware.vending.exceptions.InvalidIdentifierException;
import com.narrowware.vending.exceptions.ItemDoesNotContainPriceException;
import com.narrowware.vending.models.money.Coin;
import com.narrowware.vending.repositories.ProductRepository;
import com.narrowware.vending.repositories.VendingItemRepository;
import com.narrowware.vending.services.validators.IValidatorIdentifier;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.ProcessingInstruction;
import java.util.List;

@Service
public class VendingService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VendingItemRepository vendingItemRepository;

    @Autowired
    private IValidatorIdentifier validatorIdentifier;

    @Autowired
    private PaymentService paymentService;

    /**
     * The method checks whether the provided payload contains valid data
     * @param vendingItemDTO - the payload proviedd by the user
     * @return Product - either from the database or a new one
     */
    private Product processProductDTO(VendingItemDTO vendingItemDTO) {
        checkIfValidIdentifierOrBreakFlow(vendingItemDTO.getIdentifier().toString());
        Product prod = null;
        if (vendingItemDTO.getProduct().isPresent()) {
            final ProductDTO productDTO = vendingItemDTO.getProduct().get();
            // Check if the product already exists
            List<Product> p = productRepository.findByName(productDTO.getName());
            if (p != null && p.size() > 0) {
                return p.get(0);
            }
            prod = new Product();
            prod.setName(productDTO.getName());
            prod.setDescription(productDTO.getDescription());
        }
        return prod;
    }

    public void checkIfValidIdentifierOrBreakFlow(String identifier) {
        if (!validatorIdentifier.isValidIdentifier(identifier)) {
            throw new InvalidIdentifierException();
        }
    }

    @Transactional
    public VendingItem saveVendingItem(final VendingItemDTO vendingItemDTO) {
        Product prod = processProductDTO(vendingItemDTO);
        final VendingItem vendingItem = new VendingItem();
        vendingItem.setIdentifier(vendingItemDTO.getIdentifier());
        final int quantity = vendingItemDTO.getQuantity();
        final int price = vendingItemDTO.getPrice();
        vendingItem.setPrice(price);
        if (quantity > 10) {
            // Throws an exception, the user will receive a hint
            throw new ExceedingMaximumVendingSpaceException();
        } else {
            vendingItem.setQuantity(quantity);
            if (prod != null) {
                vendingItem.setProduct(prod);
            }
            return vendingItemRepository.save(vendingItem);
        }
    }

    @Transactional
    public void saveProduct(final ProductDTO productDTO) {
        Product prod = null;
        prod.setName(productDTO.getName());
        prod.setDescription(productDTO.getDescription());
        productRepository.save(prod);
    }

    /**
     * Returns a page of products, for UI purposes - TBD
     * @param page - the page number
     * @param size - the size of the page
     * @return a page of products
     */
    public Page<Product> getProducts(int page, int size) {
        final Pageable pg = PageRequest.of(page, size);
        return productRepository.findAll(pg);
    }

    public List<VendingItem> getVendingItems() {
        return vendingItemRepository.findAll();
    }

    public VendingItem findSelectedProduct(List<VendingItem> vendingItems, String targetItem) {
        for (VendingItem vendingItem : vendingItems) {
            if (vendingItem.getIdentifier().toString().equals(targetItem)) {
                return vendingItem;
            }
        }
        return null;
    }

    /**
     *
     * @param vendingItem
     */
    @Transactional
    public VendingPurchaseReponse buyVendingItem(final VendingItem vendingItem) {
        int currentSessionBalance = paymentService.getSessionBalance();
        // If the session cash is less than what the item costs, operation won't proceed
        if (vendingItem.getPrice() > currentSessionBalance) {
            throw new InsufficientFundsException();
        } else {
           // Return vending item and change
            List<Coin> coinChange = paymentService.getChange(vendingItem.getPrice());
            // Return vending item and change
            VendingItem copyItem = vendingItem;
            copyItem.setQuantity(vendingItem.getQuantity()-1);
            vendingItemRepository.save(copyItem);
            return generateVendingPurchaseResponse(copyItem, coinChange);
        }
    }

    @Transactional
    public VendingItem updateVendingItem(Long id, VendingItem updatedItem) {
        // Check if the item exists in the database
        VendingItem existingItem = vendingItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VendingItem with id: " + id + "not found"));
        if (updatedItem.getQuantity() > 10) {
            throw new ExceedingMaximumVendingSpaceException();
        }
        existingItem.setIdentifier(updatedItem.getIdentifier());
        existingItem.setProduct(updatedItem.getProduct());
        existingItem.setQuantity(updatedItem.getQuantity());
        existingItem.setPrice(updatedItem.getPrice());

        // Save the updated item
        return vendingItemRepository.save(existingItem);
    }

    private VendingPurchaseReponse generateVendingPurchaseResponse(VendingItem vendingItem, List<Coin> coinChange) {
        // The response that the user will receive
        VendingPurchaseReponse resp = new VendingPurchaseReponse();
        VendingItemClientResponse vendingItemResponse = new VendingItemClientResponse();
        ProductDTO product = new ProductDTO();
        // Finalize product
        product.setName(vendingItem.getProduct().getName());
        product.setDescription(vendingItem.getProduct().getDescription());
        // Finalize the vending item
        vendingItemResponse.setPrice(vendingItem.getPrice());
        vendingItemResponse.setProduct(product);
        // Finalize the response to user
        resp.setVendingItem(vendingItemResponse);
        resp.setChange(coinChange);
        return resp;
    }

    @Transactional
    public void deleteVendingItem(Long id) {
        vendingItemRepository.deleteById(id);
    }
}
