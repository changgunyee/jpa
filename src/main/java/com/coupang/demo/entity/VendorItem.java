package com.coupang.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private OutboundShipment outboundShipment;

    @OneToMany
    private List<Item> itemList;
}
