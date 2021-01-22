package com.coupang.demo;

import com.coupang.demo.entity.Item;
import com.coupang.demo.entity.OutboundShipment;
import com.coupang.demo.entity.VendorItem;
import com.coupang.demo.repository.ItemRepository;
import com.coupang.demo.repository.OutboundShipmentRepository;
import com.coupang.demo.repository.VendorItemRepository;
import com.coupang.demo.service.OutboundShipmentService;
import com.coupang.demo.service.VendorItemService;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrudTest {

    public static final String THEN = "----------- THEN ----------\n";
    @Autowired
    OutboundShipmentRepository outboundShipmentRepository;

    @Autowired
    VendorItemRepository vendorItemRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    OutboundShipmentService outboundShipmentService;

    @Autowired
    VendorItemService vendorItemService;

    @Autowired
    EntityManager em;

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
    @DisplayName("리턴 널 가능")
    public void find() {
        List<VendorItem> vendorItems = vendorItemRepository.findAll();
        assertThat(vendorItems).isNotNull().hasSize(0);
    }

    @Test
    @DisplayName("자식 엔티티 save안하고 부모 엔티티에서 save하면 자식 엔티티에는 저장이 안됨")
    public void saveBeforeSavingChild() {
        vendorItemRepository.save(VendorItem.builder()
            .itemList(Collections.singletonList(new Item()))
            .build());
    }


    @Test
    @DisplayName("delete하고 엔티티 조회")
    public void getEntityAfterDelete() {
        VendorItem vendorItem = vendorItemService.save();

        vendorItemService.delete(vendorItem);

        System.out.printf(THEN);
        System.out.printf(vendorItem.toString());
    }

    @Test
    @DisplayName("부모 엔티티 save한다고 해서 자식 엔티티 자동으로 save되는 것은 아님")
    public void saveNotSavingChild() {
        VendorItem vendorItem = new VendorItem();
        OutboundShipment outboundShipment = new OutboundShipment();
        outboundShipment.addVendorItem(vendorItem);
        vendorItem.setOutboundShipment(outboundShipment);
        outboundShipmentRepository.save(outboundShipment);

        assertThat(vendorItemRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("OneToMany, findAll, N+1 확인")
    public void oneToMany_findAll() {
        outboundShipmentService.save();
        //새로운 transaction시작이므로 새로운 EntityManager생성한다.

        System.out.printf(THEN);
        List<VendorItem> vendorItems = vendorItemRepository.findByOrderNumber(1L);
        assertThat(vendorItems).hasSize(10);
    }

    @Test
    @DisplayName("ManyToOne에서 자식 가져올 때, N+1 확인")
    public void manyToOne() {
        outboundShipmentService.save();
        //새로운 transaction시작이므로 새로운 EntityManager생성한다.

        System.out.printf(THEN);
        OutboundShipment outboundShipment = outboundShipmentRepository.findTopByOrderNumber(1L);
        List<VendorItem> vendorItemList = outboundShipment.getVendorItemList();
        System.out.printf(THEN + vendorItemList.size() + THEN);
    }
}
