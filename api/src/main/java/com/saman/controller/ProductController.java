package com.saman.controller;

import com.saman.model.User;
import com.saman.payload.dto.ProductDTO;
import com.saman.payload.response.ApiResponse;
import com.saman.service.ProductService;
import com.saman.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ProductDTO> create(
            @RequestBody ProductDTO productDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(
                productService.createProduct(productDTO,user)
        );
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ProductDTO>> getByStoreId(
            @PathVariable Long storeId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return ResponseEntity.ok(
                productService.getProductsByStoreId(storeId)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> update(
            @PathVariable Long id,
            @RequestBody ProductDTO productDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(
                productService.updateProduct(id,productDTO,user)
        );
    }

    @GetMapping("/store/{storeId}/search")
    public ResponseEntity<List<ProductDTO>> searchByKeyword(
            @PathVariable Long storeId,
            @RequestParam String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return ResponseEntity.ok(
                productService.searchByKeyword(storeId,keyword)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.getUserFromJwtToken(jwt);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Product deleted successfully");
        productService.deleteProduct(id,user);
        return ResponseEntity.ok(
                apiResponse
        );
    }
}
