package com.coupang.demo.repository;

import com.coupang.demo.entity.OutboundShipment;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class OutboundShipmentRepositoryImpl extends QuerydslRepositorySupport implements OutboundShipmentRepositoryCustom {

    public OutboundShipmentRepositoryImpl() {
        super(OutboundShipment.class);
    }
}
