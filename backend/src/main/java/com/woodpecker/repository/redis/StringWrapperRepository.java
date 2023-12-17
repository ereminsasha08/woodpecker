package com.woodpecker.repository.redis;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StringWrapperRepository extends CrudRepository<StringWrapper, String> {

    Optional<StringWrapper> findByName(String name);
}
