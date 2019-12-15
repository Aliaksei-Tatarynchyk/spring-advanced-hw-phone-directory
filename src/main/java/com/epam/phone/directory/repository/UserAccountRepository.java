package com.epam.phone.directory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.phone.directory.model.db.UserAccount;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {
}