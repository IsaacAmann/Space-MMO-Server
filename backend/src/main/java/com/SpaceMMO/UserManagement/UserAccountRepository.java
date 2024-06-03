package com.SpaceMMO.UserManagement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer>
{
    UserAccount findByUsername(String username);
    Page<UserAccount> findAll(Pageable pageable);

}
