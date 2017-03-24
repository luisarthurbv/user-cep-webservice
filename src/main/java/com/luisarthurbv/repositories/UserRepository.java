package com.luisarthurbv.repositories;

import com.luisarthurbv.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findById(long id);

}
