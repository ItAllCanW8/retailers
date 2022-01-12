package com.itechart.retailers.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
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

    @NotNull
    @Column(name = "application_number", length = 45, unique = true)
    private String applicationNumber;

    @Column(name = "reg_date_time")
    private LocalDateTime regDateTime;

    @Column(name = "last_upd_date_time")
    private LocalDateTime lastUpdDateTime;

    @Column(name = "status")
    private String status;

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

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<ApplicationItem> itemAssoc = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        if (!Objects.equals(applicationNumber, that.applicationNumber))
            return false;
        if (!Objects.equals(status, that.status)) return false;
        if (!Objects.equals(srcLocation, that.srcLocation)) return false;
        if (!Objects.equals(destLocation, that.destLocation)) return false;
        if (!Objects.equals(createdBy, that.createdBy)) return false;
        if (!Objects.equals(lastUpdBy, that.lastUpdBy)) return false;
        return Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        int result = applicationNumber != null ? applicationNumber.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (srcLocation != null ? srcLocation.hashCode() : 0);
        result = 31 * result + (destLocation != null ? destLocation.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (lastUpdBy != null ? lastUpdBy.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        return result;
    }
}
