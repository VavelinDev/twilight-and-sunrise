package com.vavelin.twilight.shop.cart.command.infrastructure.jpa;

import com.vavelin.twilight.shop.cart.command.domain.Cart;
import com.vavelin.twilight.shop.cart.command.domain.CartFactory;
import com.vavelin.twilight.shop.cart.command.usecase.GetActiveCartPort;
import com.vavelin.twilight.shop.cart.command.usecase.PersistCartPort;
import com.vavelin.twilight.shop.spring.stereotypes.OutputAdapter;
import java.util.HashSet;
import java.util.Optional;

@OutputAdapter
public class CartRepositoryJpaAdapter implements GetActiveCartPort, PersistCartPort {
    private final CartFactory cartFactory;
    private final CartCrudRepository cartCrudRepository;

    public CartRepositoryJpaAdapter(CartFactory cartFactory,
                                    CartCrudRepository cartCrudRepository) {
        this.cartFactory = cartFactory;
        this.cartCrudRepository = cartCrudRepository;
    }

    @Override
    public Optional<Cart> apply(String username) {
        Optional<CartJpa> optionalCartJpa =
            cartCrudRepository.findByUsernameAndActiveIsTrue(username);

        if (optionalCartJpa.isEmpty()) {
            return Optional.empty();
        }

        CartJpa cartJpa = optionalCartJpa.get();

        return Optional.of(cartFactory.newCart(cartJpa.id, cartJpa.username));
    }

    @Override
    public void accept(Cart cart) {
        CartJpa cartJpa = new CartJpa();
        cartJpa.id = cart.getId();
        cartJpa.username = cart.getUsername();
        cartJpa.active = cart.isActive();
        cartJpa.cartItems = new HashSet<>();

        cartCrudRepository.save(cartJpa);
    }

}
