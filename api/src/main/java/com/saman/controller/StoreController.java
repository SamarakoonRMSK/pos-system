package com.saman.controller;

import com.saman.domain.StoreStatus;
import com.saman.exceptions.UserException;
import com.saman.mapper.StoreMapper;
import com.saman.model.User;
import com.saman.payload.dto.StoreDTO;
import com.saman.payload.response.ApiResponse;
import com.saman.service.StoreService;
import com.saman.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<StoreDTO> createStore(
            @RequestBody StoreDTO storeDTO,
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(storeService.createStore(storeDTO, user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getStoreById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @GetMapping()
    public ResponseEntity<List<StoreDTO>> getAllStore(
            @RequestHeader("Authorization") String jwt
    ) {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/admin")
    public ResponseEntity<StoreDTO> getStoreByAdmin(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return ResponseEntity.ok(StoreMapper.toDTO(storeService.getStoreByAdmin()));
    }

    @GetMapping("/employee")
    public ResponseEntity<StoreDTO> getStoreByEmployee(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return ResponseEntity.ok(storeService.getStoreByEmployee());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDTO> updateStore(
            @PathVariable Long id,
            @RequestBody StoreDTO storeDTO
    ) throws Exception{
        return ResponseEntity.ok(storeService.updateStore(id,storeDTO));
    }

    @PutMapping("/{id}/moderate")
    public ResponseEntity<StoreDTO> moderateStore(
            @PathVariable Long id,
            @RequestParam StoreStatus status
    ) throws Exception{
        return ResponseEntity.ok(storeService.moderateStore(id,status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStore(
            @PathVariable Long id
    ) throws Exception{
        storeService.deleteStore(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Successfully deleted store");
        return ResponseEntity.ok(apiResponse);
    }
}
