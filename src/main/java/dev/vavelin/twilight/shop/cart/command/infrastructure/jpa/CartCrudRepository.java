package dev.vavelin.twilight.shop.cart.command.infrastructure.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface CartCrudRepository extends CrudRepository<CartJpa, Long> {

    Optional<CartJpa> findByUsernameAndActiveIsTrue(@Param("username") String username);

}
