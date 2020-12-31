package com.coupang.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class VendorItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private OutboundShipment outboundShipment;

    @OneToMany(mappedBy = "vendorItem")
    private List<Item> itemList;
}
