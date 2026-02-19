package dev.vavelin.sunrise.shop.cart.command.infrastructure.jpa;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CartCrudRepository extends CrudRepository<CartJpa, Long> {

    Optional<CartJpa> findByUsernameAndActiveIsTrue(String username);

}
