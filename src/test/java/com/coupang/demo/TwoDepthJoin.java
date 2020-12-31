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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwoDepthJoin {

    public static final String THEN = "---------- THEN ----------\n";
    @Autowired
    OutboundShipmentRepository outboundShipmentRepository;

    @Autowired
    VendorItemRepository vendorItemRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        clear();

        for (int i = 0; i < 3; i++) {
            OutboundShipment outboundShipment = OutboundShipment.builder()
                .orderNumber(1L)
                .build();
            outboundShipmentRepository.save(outboundShipment);

            Item item1 = itemRepository.save(new Item());
            Item item2 = itemRepository.save(new Item());
            VendorItem vendorItem1 = vendorItemRepository.save(new VendorItem());
            VendorItem vendorItem2 = vendorItemRepository.save(new VendorItem());
            item1.setVendorItem(vendorItem1);
            item2.setVendorItem(vendorItem1);
            vendorItem1.setItemList(Arrays.asList(item1, item2));
            itemRepository.save(item1);
            itemRepository.save(item2);
            vendorItemRepository.save(vendorItem1);

            outboundShipment.addVendorItem(vendorItem1);
            outboundShipment.addVendorItem(vendorItem2);
            vendorItem1.setOutboundShipment(outboundShipment);
            vendorItem2.setOutboundShipment(outboundShipment);
            vendorItemRepository.save(vendorItem1);
            vendorItemRepository.save(vendorItem2);
            outboundShipmentRepository.save(outboundShipment);
        }

        System.out.printf(THEN);
    }

    private void clear() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.createNativeQuery("delete from item").executeUpdate();
        em.createNativeQuery("delete from outbound_shipment").executeUpdate();
        em.createNativeQuery("delete from vendor_item").executeUpdate();
        tx.commit();
        em.close();
    }

    @Test
    @DisplayName("two depth")
    public void twoDepth() {
        List<Item> items = itemRepository.findByOrderNumberTwoDepthJoinFetch(1L);

        assertThat(1).isEqualTo(1);
    }
}
