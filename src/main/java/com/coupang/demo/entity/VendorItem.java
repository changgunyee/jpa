package com.coupang.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class VendorItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderNumber;

    @ManyToOne
    private OutboundShipment outboundShipment;

    @OneToMany(mappedBy = "vendorItem")
    private List<Item> itemList;
}
