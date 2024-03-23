package com.narrowware.vending.repositories;

import com.narrowware.vending.entities.VendingItem;
import com.narrowware.vending.models.Identifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendingItemRepository extends JpaRepository<VendingItem, Long> {
    List<VendingItem> findByQuantity(int quantity);
    List<VendingItem> findByIdentifier(Identifier identifier);
}
