package com.coupang.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Item {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private VendorItem vendorItem;
}
