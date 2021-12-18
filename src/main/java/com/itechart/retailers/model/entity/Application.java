package com.itechart.retailers.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "application")
public class Application extends Identity {

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    @ToString.Exclude
    private User createdBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "last_upd_by")
    @ToString.Exclude
    private User lastUpdBy;

    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<ApplicationItem> itemAssoc;
}
