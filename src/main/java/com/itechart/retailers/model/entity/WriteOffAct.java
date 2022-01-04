package com.itechart.retailers.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "write_off_act")
@Entity
public class WriteOffAct extends Identity {

    @Column(name = "identifier", nullable = false, unique = true)
    private String identifier;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "writeOffAct", fetch = FetchType.EAGER)
    private Set<WrittenOffItem> writtenOffItems;
}
