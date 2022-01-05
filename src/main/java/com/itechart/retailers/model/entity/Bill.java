package com.itechart.retailers.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bill")
public class Bill extends Identity {

    @NotNull
    @Column(name = "number", unique = true, length = 45)
    private String number;

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
    private List<BillItem> itemAssoc;
}
