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
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrudTest {

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
    @DisplayName("하위 엔티티 save안하고 상위 엔티티에서 save하면 에러")
    void saveBeforeSavingChild() {
        vendorItemRepository.save(VendorItem.builder()
            .itemList(Collections.singletonList(new Item()))
            .build());
    }

    @Test
    @DisplayName("상위 엔티티 save한다고 해서 하위 엔티티 자동으로 save되는 것은 아님")
    void saveNotSavingChild() {
        VendorItem vendorItem = new VendorItem();
        OutboundShipment outboundShipment = new OutboundShipment();
        outboundShipment.addVendorItem(vendorItem);
        outboundShipmentRepository.save(outboundShipment);

        assertThat(vendorItemRepository.findAll()).isEmpty();
    }
}
