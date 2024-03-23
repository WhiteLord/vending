package com.narrowware.vending.entities;

import com.narrowware.vending.entities.Product;
import com.narrowware.vending.models.Identifier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * The vending item class represents an object in the vending machine
 * The vending machine does not know about the specific details of each item
 * It only cares about releasing the spring if a specific condition is met
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Identifier identifier;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    // Price in stotinki
    @Positive
    private Integer price = 0;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
    }

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
