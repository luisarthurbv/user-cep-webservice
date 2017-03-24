package com.luisarthurbv.repositories;

import com.luisarthurbv.entity.UserAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddressEntity, Long> {

    UserAddressEntity findByUserId(long userId);
    void deleteByUserId(long userId);

}
