package com.saman.service;

import com.saman.domain.StoreStatus;
import com.saman.exceptions.UserException;
import com.saman.model.Store;
import com.saman.model.User;
import com.saman.payload.dto.StoreDTO;

import java.util.List;

public interface StoreService {

    StoreDTO createStore(StoreDTO storeDTO, User user);
    StoreDTO getStoreById(Long storeId) throws Exception;
    List<StoreDTO> getAllStores();
    Store getStoreByAdmin() throws UserException;
    StoreDTO updateStore(Long id, StoreDTO storeDTO) throws Exception;
    void deleteStore(Long id) throws UserException;
    StoreDTO getStoreByEmployee() throws UserException;
    StoreDTO moderateStore(Long id, StoreStatus status) throws Exception;
}
