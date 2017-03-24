package com.luisarthurbv.cases.userAddress;

import com.luisarthurbv.services.UserAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeleteUserAddress {

    private UserAddressService userAddressService;

    @Autowired
    public DeleteUserAddress(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    public boolean deleteUserAddress(long userId) {
        return userAddressService.deleteUserAddress(userId);
    }

}
