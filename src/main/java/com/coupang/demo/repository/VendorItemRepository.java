package com.coupang.demo.repository;

import com.coupang.demo.entity.VendorItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorItemRepository extends JpaRepository<VendorItem, Long> {


    List<VendorItem> findByOrderNumber(long orderNumber);

    VendorItem findFirstByOrderNumber(long orderNumber);

}
