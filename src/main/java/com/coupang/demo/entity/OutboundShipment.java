package com.coupang.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboundShipment {

    @Id
    @GeneratedValue
    private Long id;

    private Long orderNumber;

    @OneToMany
    private List<VendorItem> vendorItemList;
}
