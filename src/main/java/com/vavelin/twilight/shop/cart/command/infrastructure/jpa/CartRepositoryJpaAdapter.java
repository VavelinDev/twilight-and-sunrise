package com.vavelin.twilight.shop.cart.command.infrastructure.jpa;

import com.vavelin.twilight.shop.cart.command.domain.Cart;
import com.vavelin.twilight.shop.cart.command.domain.CartFactory;
import com.vavelin.twilight.shop.cart.command.usecase.CartRepositoryPort;
import com.vavelin.twilight.shop.spring.stereotypes.Adapter;
import java.util.HashSet;
import java.util.Optional;

@Adapter
public class CartRepositoryJpaAdapter implements CartRepositoryPort {
    private final CartFactory cartFactory;
    private final CartCrudRepository cartCrudRepository;

    public CartRepositoryJpaAdapter(CartFactory cartFactory,
                                    CartCrudRepository cartCrudRepository) {
        this.cartFactory = cartFactory;
        this.cartCrudRepository = cartCrudRepository;
    }

    @Override
    public Optional<Cart> getActiveCart(String username) {
        Optional<CartJpa> optionalCartJpa =
            cartCrudRepository.findByUsernameAndActiveIsTrue(username);

        if (optionalCartJpa.isEmpty()) {
            return Optional.empty();
        }

        CartJpa cartJpa = optionalCartJpa.get();

        return Optional.of(cartFactory.newCart(cartJpa.id, cartJpa.username));
    }

    @Override
    public void saveCart(Cart cart) {
        CartJpa cartJpa = new CartJpa();
        cartJpa.id = cart.getId();
        cartJpa.username = cart.getUsername();
        cartJpa.active = cart.isActive();
        cartJpa.cartItems = new HashSet<>();

        cartCrudRepository.save(cartJpa);
    }

}
