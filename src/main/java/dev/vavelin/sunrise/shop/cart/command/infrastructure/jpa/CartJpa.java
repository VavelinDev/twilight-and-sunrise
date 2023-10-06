package dev.vavelin.sunrise.shop.cart.command.infrastructure.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "cart")
class CartJpa {

    @Id
    Long id;

    String username;

    boolean active = true;

    @OneToMany(mappedBy = "cart", orphanRemoval = true)
    Set<CartItemJpa> cartItems = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartJpa that = (CartJpa) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Entity
    static class CartItemJpa {
        @Id
        Long id;
        Long productId;
        int quantity;
        @ManyToOne
        CartJpa cart;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CartItemJpa that = (CartItemJpa) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
