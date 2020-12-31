package com.coupang.demo.repository;

import com.coupang.demo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "select i from Item i join fetch i.vendorItem vi join fetch vi.outboundShipment o where o.orderNumber=:orderNumber")
    List<Item> findByOrderNumberTwoDepthJoinFetch(@Param(value = "orderNumber") Long orderNumber);
}
