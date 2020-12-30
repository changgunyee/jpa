package com.coupang.demo.repository;

import com.coupang.demo.entity.Item;
import com.coupang.demo.entity.OutboundShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {


}
