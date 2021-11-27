package com.itechart.retailers.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
//@Builder
@Entity
@Table(name = "application")
public class Application extends Identity{
    @Column(name = "application_number")
    private String applicationNumber;

    @Column(name = "reg_date_time")
    private LocalDateTime regDateTime;

    @Column(name = "last_upd_date_time")
    private LocalDateTime lastUpdDateTime;

    @Column(name = "status")
    private String status;

    @Column(name = "items_total")
    private Long itemsTotal;

    @Column(name = "units_total")
    private Long unitsTotal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_location")
    private Location srcLocation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_location")
    private Location destLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_upd_by")
    private User lastUpdBy;

    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    private Set<ApplicationItem> itemAssoc;
}
