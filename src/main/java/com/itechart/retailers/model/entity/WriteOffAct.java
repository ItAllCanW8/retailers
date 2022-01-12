package com.itechart.retailers.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
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

    @NotNull
    @Column(name = "identifier", unique = true)
    private String identifier;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "writeOffAct", fetch = FetchType.EAGER)
    private Set<WrittenOffItem> writtenOffItems = new HashSet<>();
}
