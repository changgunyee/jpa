package com.coupang.demo.repository;

import com.coupang.demo.entity.OutboundShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OutboundShipmentRepository extends JpaRepository<OutboundShipment, Long> {


    @Query(value = "select o from OutboundShipment o left join fetch o.vendorItemList")
    List<OutboundShipment> findByOrderNumber(Long orderNumber);

}
