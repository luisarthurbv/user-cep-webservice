package com.luisarthurbv.cases.userAddress;

import com.luisarthurbv.models.Address;
import com.luisarthurbv.models.UserAddress;
import com.luisarthurbv.services.UserAddressService;
import com.luisarthurbv.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UpdateUserAddress {

    private UserAddressService userAddressService;

    @Autowired
    public UpdateUserAddress(UserAddressService userAddressService) throws UserNotFoundException, UpdateUserAddressException {
        this.userAddressService = userAddressService;
    }

    public UserAddress updateUserAddress(long userId, Address address) throws UserNotFoundException,
            UpdateUserAddressException {
        try {
            return userAddressService.updateAddress(userId, address);
        } catch (UserAddressService.InvalidUserException e) {
            throw new UserService.UserNotFoundException(userId);
        } catch (UserAddressService.UserAddressOperationException e) {
            throw new UpdateUserAddressException(userId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new UpdateUserAddressException(userId);
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(long userId) {
            super(String.format("Couldn't find userId=%d", userId));
        }
    }

    public static class UpdateUserAddressException extends RuntimeException {
        public UpdateUserAddressException(long userId) {
            super(String.format("Couldn't update address from userId=%d", userId));
        }
    }

}
