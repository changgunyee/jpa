package com.coupang.demo.service;

import com.coupang.demo.entity.Item;
import com.coupang.demo.entity.VendorItem;
import com.coupang.demo.repository.ItemRepository;
import com.coupang.demo.repository.OutboundShipmentRepository;
import com.coupang.demo.repository.VendorItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class VendorItemService {

    @Autowired
    OutboundShipmentRepository outboundShipmentRepository;

    @Autowired
    VendorItemRepository vendorItemRepository;

    @Autowired
    ItemRepository itemRepository;

    @Transactional
    public VendorItem save() {
        Item item = new Item();
        itemRepository.save(item);
        VendorItem saved = vendorItemRepository.save(VendorItem.builder()
            .itemList(Collections.singletonList(item))
            .build());
        item.setVendorItem(saved);
        itemRepository.save(item);
        return saved;
    }

    @Transactional
    public VendorItem delete(VendorItem vendorItem) {
        itemRepository.deleteAll(vendorItem.getItemList());
        return vendorItem;
    }
}
