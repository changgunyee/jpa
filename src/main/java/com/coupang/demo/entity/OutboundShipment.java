package com.coupang.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OutboundShipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderNumber;

    @OneToMany(mappedBy = "outboundShipment", cascade = CascadeType.REMOVE)
    private List<VendorItem> vendorItemList;

    public void addVendorItem(VendorItem vendorItem) {
        if (vendorItemList == null) {
            vendorItemList = new ArrayList<>();
        }

        vendorItemList.add(vendorItem);
        vendorItem.setOutboundShipment(this);
    }
}
