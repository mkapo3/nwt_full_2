package com.nwt.nwt_projekat_user.repository.order;

import com.nwt.nwt_projekat_user.models.CartProduct;
import com.nwt.nwt_projekat_user.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    public Order getById(Long id);

}
