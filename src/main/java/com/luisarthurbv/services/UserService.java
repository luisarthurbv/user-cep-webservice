package com.luisarthurbv.services;

import com.luisarthurbv.entity.UserEntity;
import com.luisarthurbv.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findById(long userId) throws UserNotFoundException {
        UserEntity user = userRepository.findById(userId);
        if(user != null) {
            return user;
        }
        throw new UserNotFoundException(userId);
    }

    public static class UserNotFoundException extends RuntimeException {

        public UserNotFoundException(long userId) {
            super(String.format("Couldn't find user with id=%d", userId));
        }

    }

}
