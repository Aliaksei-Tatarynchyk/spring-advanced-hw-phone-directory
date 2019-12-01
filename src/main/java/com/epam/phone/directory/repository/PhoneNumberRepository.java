package com.epam.phone.directory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.phone.directory.model.db.PhoneNumber;

@Repository
public interface PhoneNumberRepository extends CrudRepository<PhoneNumber, Long> {
}