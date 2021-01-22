package com.coupang.demo;

import com.coupang.demo.entity.OutboundShipment;
import com.coupang.demo.repository.ItemRepository;
import com.coupang.demo.repository.OutboundShipmentRepository;
import com.coupang.demo.repository.VendorItemRepository;
import com.coupang.demo.service.OutboundShipmentService;
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
    OutboundShipmentService outboundShipmentService;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        clear();

        outboundShipmentService.save();
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
    @DisplayName("deleteAll은 N+1 발생 cascade반영 o")
    public void cascadeRemove() {

        List<OutboundShipment> outboundShipments = outboundShipmentRepository.findByOrderNumber(1L);
        outboundShipmentRepository.deleteAll(outboundShipments);
    }

    @Test
    @DisplayName("join fetch는 distinct나 set을 써야함")
    public void joinFetch_noDistinct() {
        List<OutboundShipment> outboundShipments = outboundShipmentRepository.findByOrderNumberJoinFetch(1L);

        assertThat(outboundShipments).hasSize(3 * 2);
    }

    @Test
    @DisplayName("join fetch는 distinct나 set을 써야함")
    public void joinFetch_distinct() {
        List<OutboundShipment> outboundShipments = outboundShipmentRepository.findByOrderNumberJoinFetchDistinct(1L);

        assertThat(outboundShipments).hasSize(3);
    }

    @Test
    @DisplayName("entitygraph")
    public void entitygraph() {
        outboundShipmentRepository.save(OutboundShipment.builder()
            .orderNumber(1L)
            .build());

        System.out.println(THEN);
        List<OutboundShipment> outboundShipments = outboundShipmentRepository.findByOrderNumber(1L);
        assertThat(outboundShipments).hasSize(4);
    }
}
