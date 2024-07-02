package com.startup.oda.service;

import com.startup.oda.dto.response.UserProfileDto;
import com.startup.oda.repository.OwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
}
