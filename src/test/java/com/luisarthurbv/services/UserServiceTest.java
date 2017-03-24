package com.luisarthurbv.services;

import com.luisarthurbv.entity.UserEntity;
import com.luisarthurbv.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;

import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Test
    public void validUserTest() {
        long userId = 1;
        UserEntity user = new UserEntity();
        user.setId(userId);

        UserRepository repository = mock(UserRepository.class);
        when(repository.findById(userId)).thenReturn(user);

        UserService userService = new UserService(repository);
        UserEntity returnedUser = userService.findById(userId);

        assertEquals(user, returnedUser);
    }

    @Test(expected = UserService.UserNotFoundException.class)
    public void invalidUserTest() {
        long userId = 1;
        UserRepository repository = mock(UserRepository.class);
        doThrow(new UserService.UserNotFoundException(userId)).when(repository).findById(userId);
        UserService userService = new UserService(repository);
        userService.findById(userId);
    }

}
