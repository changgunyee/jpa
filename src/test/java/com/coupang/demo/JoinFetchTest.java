package com.coupang.demo;

import com.coupang.demo.entity.Item;
import com.coupang.demo.entity.OutboundShipment;
import com.coupang.demo.entity.VendorItem;
import com.coupang.demo.repository.ItemRepository;
import com.coupang.demo.repository.OutboundShipmentRepository;
import com.coupang.demo.repository.VendorItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JoinFetchTest {

    @Autowired
    OutboundShipmentRepository outboundShipmentRepository;

    @Autowired
    VendorItemRepository vendorItemRepository;

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        outboundShipmentRepository.deleteAll();
        vendorItemRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("하위 엔티티 save안하고 상위 엔티티에서 save하면 에러")
    void saveBeforePersist() {
        assertThatThrownBy(() -> {
            vendorItemRepository.save(VendorItem.builder()
                .itemList(Collections.singletonList(new Item()))
                .build());
        }).hasMessageContaining("object references an unsaved transient instance");
    }

    @Test
    @DisplayName("left join fetch 쓰면 left outer join으로 가져옴")
    void leftJoinFetch() {
        Item item = itemRepository.save(new Item());
        VendorItem vendorItem = vendorItemRepository.save(VendorItem.builder().itemList(Arrays.asList(item)).build());
        OutboundShipment outboundShipment1 = outboundShipmentRepository.save(OutboundShipment.builder().orderNumber(1L).vendorItemList(Arrays.asList(vendorItem)).build());
        OutboundShipment outboundShipment2 = outboundShipmentRepository.save(OutboundShipment.builder().orderNumber(1L).build());

        List<OutboundShipment> outboundShipments = outboundShipmentRepository.findByOrderNumber(1L);
        assertThat(outboundShipments).hasSize(2);
    }
}
