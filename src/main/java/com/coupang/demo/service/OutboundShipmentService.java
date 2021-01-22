package com.coupang.demo.service;

import com.coupang.demo.entity.OutboundShipment;
import com.coupang.demo.entity.VendorItem;
import com.coupang.demo.repository.ItemRepository;
import com.coupang.demo.repository.OutboundShipmentRepository;
import com.coupang.demo.repository.VendorItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class OutboundShipmentService {

    @Autowired
    OutboundShipmentRepository outboundShipmentRepository;

    @Autowired
    VendorItemRepository vendorItemRepository;

    @Autowired
    ItemRepository itemRepository;

    public void save() {
        for (int i = 0; i < 5; i++) {
            VendorItem vendorItem1 = VendorItem.builder().orderNumber(1L).build();
            VendorItem vendorItem2 = VendorItem.builder().orderNumber(1L).build();
            OutboundShipment outboundShipment = OutboundShipment.builder().orderNumber(1L).build();
            vendorItemRepository.save(vendorItem1);
            vendorItemRepository.save(vendorItem2);
            outboundShipmentRepository.save(outboundShipment);

            outboundShipment.addVendorItem(vendorItem1);
            outboundShipment.addVendorItem(vendorItem2);
            vendorItemRepository.save(vendorItem1);
            vendorItemRepository.save(vendorItem2);
            outboundShipmentRepository.save(outboundShipment);
        }
    }
}
