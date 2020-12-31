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
public class FetchJoinTest {

    public static final String THEN = "---------- THEN ----------";
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
            VendorItem vendorItem1 = VendorItem.builder().itemList(Arrays.asList(item1, item2)).build();
            VendorItem vendorItem2 = VendorItem.builder().build();
            outboundShipment.addVendorItem(vendorItem1);
            outboundShipment.addVendorItem(vendorItem2);
            vendorItemRepository.save(vendorItem1);
            vendorItemRepository.save(vendorItem2);
            outboundShipmentRepository.save(outboundShipment);
        }
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
    @DisplayName("deleteAll은 N+1 발생 cascade반영 o")
    void cascadeRemove() {
        List<OutboundShipment> outboundShipments = outboundShipmentRepository.findByOrderNumber(1L);
        outboundShipmentRepository.deleteAll(outboundShipments);
    }

    @Test
    @DisplayName("join fetch는 distinct나 set을 써야함")
    void joinFetch_noDistinct() {
        List<OutboundShipment> outboundShipments = outboundShipmentRepository.findByOrderNumberJoinFetch(1L);

        assertThat(outboundShipments).hasSize(3 * 2);
    }

    @Test
    @DisplayName("join fetch는 distinct나 set을 써야함")
    void joinFetch_distinct() {
        List<OutboundShipment> outboundShipments = outboundShipmentRepository.findByOrderNumberJoinFetchDistinct(1L);

        assertThat(outboundShipments).hasSize(3);
    }

    @Test
    @DisplayName("entitygraph")
    void entitygraph() {
        outboundShipmentRepository.save(OutboundShipment.builder()
            .orderNumber(1L)
            .build());

        System.out.println(THEN);
        List<OutboundShipment> outboundShipments = outboundShipmentRepository.findByOrderNumber(1L);
        assertThat(outboundShipments).hasSize(4);
    }
}
