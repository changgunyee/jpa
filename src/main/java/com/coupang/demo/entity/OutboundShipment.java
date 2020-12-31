package com.coupang.demo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    @GeneratedValue
    private Long id;

    private Long orderNumber;

    @OneToMany(mappedBy = "outboundShipment")
    private List<VendorItem> vendorItemList = new ArrayList<>();

    public void addVendorItem(VendorItem vendorItem) {
        if (vendorItemList == null) {
            vendorItemList = new ArrayList<>();
        }

        vendorItemList.add(vendorItem);
        vendorItem.setOutboundShipment(this);
    }
}
