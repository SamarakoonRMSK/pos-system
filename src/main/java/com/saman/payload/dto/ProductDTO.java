package com.saman.payload.dto;
import com.saman.model.Store;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductDTO {

    private Long id;

    private String name;

    private String sku;

    private String description;

    private Double mrp;

    private Double sellingPrice;

    private String brand;
    private String image;
    private CategoryDTO category;
    private Long categoryId;

    private Long storeID;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
