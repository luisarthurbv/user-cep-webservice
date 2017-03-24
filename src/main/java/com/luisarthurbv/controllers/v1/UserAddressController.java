package com.luisarthurbv.controllers.v1;

import com.luisarthurbv.cases.userAddress.CreateUserAddress;
import com.luisarthurbv.cases.userAddress.RetrieveUserAddress;
import com.luisarthurbv.models.Address;
import com.luisarthurbv.models.UserAddress;
import com.luisarthurbv.services.UserAddressService;

import com.luisarthurbv.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user/address/v1")
public class UserAddressController {

    private UserAddressService userAddressService;
    private RetrieveUserAddress retrieveUserAddress;
    private CreateUserAddress createAddress;

    @Autowired
    public UserAddressController(UserAddressService userAddressService,
                                 RetrieveUserAddress retrieveUserAddress,
                                 CreateUserAddress createAddress) {
        this.userAddressService = userAddressService;
        this.retrieveUserAddress = retrieveUserAddress;
        this.createAddress = createAddress;
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseEntity<Map> retieveUserAddress(@RequestBody GetUserAddressRequest request) {
        try {
            UserAddress userAddress = retrieveUserAddress.retrieveUserAddress(request.userId);
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("address", userAddress);
            return new ResponseEntity<Map>(response, HttpStatus.OK);
        } catch (RetrieveUserAddress.UserNotFoundException e) {
            return userNotFoundResponse(request.userId);
        } catch (RetrieveUserAddress.UserAddressNotFoundException e) {
            return ResponseUtils.notFoundResponse(String.format("Couldn't find address for userId=%d", request.userId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return unknownErrorResponse();
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Map> createUserAddress(@RequestBody CreateUserAddressRequest request) {
        try {
            UserAddress userAddress = createAddress.createAddress(
                    request.userId, request.address
            );
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("address", userAddress);
            return new ResponseEntity<Map>(response, HttpStatus.OK);
        } catch (CreateUserAddress.UserNotFoundException e) {
            return userNotFoundResponse(request.userId);
        } catch (CreateUserAddress.InvalidAddressException e) {
            return ResponseUtils.invalidArgumentsResponse("Invalid address parameters");
        } catch (CreateUserAddress.CreateAddressException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", String.format("Couldn't create address for userId=%d", request.userId));
            return new ResponseEntity<Map>(response, HttpStatus.METHOD_FAILURE);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return unknownErrorResponse();
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Map> updateUserAddress(@RequestBody UpdateUserAddressRequest request) {
        return null;
    }

    private ResponseEntity<Map> userNotFoundResponse(long userId) {
        return ResponseUtils.notFoundResponse(String.format("Invalid userId=%d", userId));
    }

    private ResponseEntity<Map> unknownErrorResponse() {
        Map response = new HashMap();
        response.put("error", "Unknown error");
        return new ResponseEntity<Map>(response, HttpStatus.METHOD_FAILURE);
    }

    public static class CreateUserAddressRequest {
        private long userId;
        private Address address;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }

    public static class GetUserAddressRequest {
        private long userId;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }
    }

    public static class UpdateUserAddressRequest {
        private long userId;
        private Address address;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }

}
