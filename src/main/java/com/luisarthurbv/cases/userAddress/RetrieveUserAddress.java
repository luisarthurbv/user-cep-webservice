package com.luisarthurbv.cases.userAddress;

import com.luisarthurbv.models.UserAddress;
import com.luisarthurbv.services.UserAddressService;
import com.luisarthurbv.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RetrieveUserAddress {

    private UserAddressService userAddressService;

    @Autowired
    public RetrieveUserAddress(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    public UserAddress retrieveUserAddress(long userId) throws UserNotFoundException, UserAddressNotFoundException{
        try {
            return this.userAddressService.retrieveAddress(userId);
        } catch (UserAddressService.InvalidUserException e) {
            throw new UserNotFoundException(userId);
        } catch (UserAddressService.UserAddressNotFoundException e) {
            throw new UserAddressNotFoundException(userId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new UserAddressNotFoundException(userId);
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(long userId) {
            super(String.format("Couldn't find userId=%d", userId));
        }
    }

    public static class UserAddressNotFoundException extends RuntimeException {
        public UserAddressNotFoundException(long userId) {
            super(String.format("Couldn't find address for userId=%d", userId));
        }
    }


}
