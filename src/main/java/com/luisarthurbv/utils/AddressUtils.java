package com.luisarthurbv.utils;

import com.luisarthurbv.models.Address;

public class AddressUtils {

    public static boolean isValid(Address address){
        return address.getCep() != null &&
                address.getStreet() != null &&
                address.getNumber() != null &&
                address.getCity() != null &&
                address.getState() != null;
    }

}
