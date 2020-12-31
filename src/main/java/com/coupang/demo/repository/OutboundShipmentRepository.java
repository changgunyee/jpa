package com.coupang.demo.repository;

import com.coupang.demo.entity.OutboundShipment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OutboundShipmentRepository extends JpaRepository<OutboundShipment, Long>, OutboundShipmentRepositoryCustom {


    @Query(value = "select o from OutboundShipment o join fetch o.vendorItemList")
    List<OutboundShipment> findByOrderNumberJoinFetch(Long orderNumber);

    @Query(value = "select distinct o from OutboundShipment o join fetch o.vendorItemList")
    List<OutboundShipment> findByOrderNumberJoinFetchDistinct(Long orderNumber);


    @EntityGraph(attributePaths = {"vendorItemList"})
    List<OutboundShipment> findByOrderNumber(Long orderNumber);

}
