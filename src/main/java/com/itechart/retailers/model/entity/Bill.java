package com.itechart.retailers.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bill")
public class Bill extends Identity{

    @Column(name = "number", unique = true, length = 45, nullable = false)
    private String number;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "total_units")
    private Long totalUnits;

    @Column(name = "date_time")
    private LocalDateTime regDateTime;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "shop_manager_id")
    private User shopManager;

    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<BillItem> itemAssoc;
}
