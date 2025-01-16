package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.Cart;
import me.yangsongi.electrozone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
    void deleteByUser(User user);

}
