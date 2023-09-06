package com.vavelin.example.hexagon.cart.command.infrastructure.jpa;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface CartCrudRepository extends CrudRepository<CartJpa, Long> {

    Optional<CartJpa> findByUsernameAndActiveIsTrue(@Param("username") String username);

}
