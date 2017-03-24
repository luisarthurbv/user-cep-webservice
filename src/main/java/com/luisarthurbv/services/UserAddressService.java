package com.luisarthurbv.services;

import com.luisarthurbv.entity.UserAddressEntity;
import com.luisarthurbv.models.Address;
import com.luisarthurbv.models.User;
import com.luisarthurbv.models.UserAddress;
import com.luisarthurbv.repositories.UserAddressRepository;
import com.luisarthurbv.utils.AddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserAddressService {

    private UserAddressRepository userAddressRepository;
    private UserService userService;

    @Autowired
    public UserAddressService(
            UserService userService,
            UserAddressRepository userAddressRepository) {
        this.userService = userService;
        this.userAddressRepository = userAddressRepository;
    }

    private boolean isValidUser(long userId) {
        try {
            userService.findById(userId);
            return true;
        } catch (UserService.UserNotFoundException e) {
            return false;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public UserAddress retrieveAddress(long userId) throws InvalidUserException, UserAddressNotFoundException {
        if(!isValidUser(userId)) {
            throw new InvalidUserException(userId);
        }
        UserAddressEntity userAddressEntity = userAddressRepository.findByUserId(userId);
        if(userAddressEntity != null) {
            return createUserAddressObject(userAddressEntity);
        }
        throw new UserAddressNotFoundException(userId);
    }

    public UserAddress createAddress(long userId, Address address) throws InvalidUserException,
            InvalidAddressException, UserAddressOperationException {
        if(!isValidUser(userId)) {
            throw new InvalidUserException(userId);
        }
        if(!AddressUtils.isValid(address)) {
            throw new InvalidAddressException();
        }
        try {
            UserAddressEntity entity = userAddressRepository.save(createUserAddressEntityObject(userId, address));
            return createUserAddressObject(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new UserAddressOperationException(userId, "create");
        }
    }

    public UserAddress updateAddress(long userId, Address address) throws InvalidUserException, UserAddressOperationException {
        if(!isValidUser(userId)) {
            throw new InvalidUserException(userId);
        }
        UserAddressEntity entity = userAddressRepository.findByUserId(userId);
        if (entity == null) {
            throw new UserAddressOperationException(userId, "update");
        }
        UserAddressEntity givenEntity = createUserAddressEntityObject(userId, address);
        entity.mergeAddress(givenEntity);
        entity = userAddressRepository.save(entity);
        if (entity == null) {
            throw new UserAddressOperationException(userId, "update");
        }
        return createUserAddressObject(entity);
    }

    public boolean deleteUserAddress(long userId) throws InvalidUserException, UserAddressOperationException {
        if(!isValidUser(userId)) {
            return false;
        }
        try {
            userAddressRepository.deleteByUserId(userId);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    private static Address createAddress(UserAddressEntity entity) {
        Address address = new Address();
        address.setCep(entity.getCep());
        address.setStreet(entity.getStreet());
        address.setNumber(entity.getNumber());
        address.setComplements(entity.getComplements());
        address.setNeighborhood(entity.getNeighborhood());
        address.setCity(entity.getCity());
        address.setState(entity.getState());
        return address;
    }

    private static UserAddressEntity createUserAddressEntityObject(long userId, Address address) {
        UserAddressEntity userAddress = new UserAddressEntity();
        userAddress.setUserId(userId);
        userAddress.setCep(address.getCep());
        userAddress.setStreet(address.getStreet());
        userAddress.setNumber(address.getNumber());
        userAddress.setComplements(address.getComplements());
        userAddress.setNeighborhood(address.getNeighborhood());
        userAddress.setCity(address.getCity());
        userAddress.setState(address.getState());
        return userAddress;
    }

    private static UserAddress createUserAddressObject(UserAddressEntity entity) {
        Address address = createAddress(entity);
        User user = new User();
        user.setId(entity.getUserId());
        UserAddress userAddress = new UserAddress();
        userAddress.setUser(user);
        userAddress.setAddress(address);
        return userAddress;
    }

    public static class InvalidUserException extends RuntimeException {
        public InvalidUserException(long userId) {
            super(String.format("userId: %d is invalid", userId));
        }
    }

    public static class InvalidAddressException extends RuntimeException {
        public InvalidAddressException() {
            super("The given address is invalid");
        }
    }

    public static class UserAddressNotFoundException extends RuntimeException {
        public UserAddressNotFoundException(long userId) {
            super(String.format("Couldn't find address for userId=%d", userId));
        }
    }

    public static class UserAddressOperationException extends RuntimeException {
        public UserAddressOperationException(long userId, String operation) {
            super(String.format("Couldn't execute operation=%s for userId=%d", operation, userId));
        }
    }
}
