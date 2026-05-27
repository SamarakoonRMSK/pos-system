package com.saman.service;

import com.saman.exceptions.UserException;
import com.saman.model.User;
import com.saman.payload.dto.BranchDTO;

import java.util.List;

public interface BranchService {
    BranchDTO createBranch(BranchDTO branchDTOr) throws UserException;
    BranchDTO updateBranch(Long id, BranchDTO branchDTO) throws Exception;
    void deleteBranch(Long id) throws Exception;
    List<BranchDTO> getAllBranchesByStoreId(Long storeId);
    BranchDTO getBranchById(Long id) throws Exception;
}
