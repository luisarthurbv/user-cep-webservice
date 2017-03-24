package com.luisarthurbv.cases.userAddress;

import com.luisarthurbv.models.Address;
import com.luisarthurbv.models.UserAddress;
import com.luisarthurbv.services.UserAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateUserAddress {

    private UserAddressService userAddressService;

    @Autowired
    public CreateUserAddress(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    public UserAddress createAddress(long userId, Address address) throws UserNotFoundException,
            InvalidAddressException, CreateAddressException {
        try {
            return userAddressService.createAddress(userId, address);
        } catch (UserAddressService.InvalidUserException e) {
            throw new UserNotFoundException(userId);
        } catch (UserAddressService.InvalidAddressException e) {
            throw new InvalidAddressException();
        } catch (UserAddressService.UserAddressOperationException e) {
            throw new CreateAddressException(userId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CreateAddressException(userId);
        }
    }

    public static class InvalidAddressException extends RuntimeException {
        public InvalidAddressException() {
            super("The given address is invalid");
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(long userId) {
            super(String.format("Couldn't find userId=%d", userId));
        }
    }

    public static class CreateAddressException extends RuntimeException {
        public CreateAddressException(long userId) {
            super(String.format("Couldn't create address for userId=%d", userId));
        }
    }

}
