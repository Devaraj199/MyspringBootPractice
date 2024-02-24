package com.myflowdb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.Instant;
import java.util.Set;

@lombok.experimental.SuperBuilder(toBuilder = true)

@Entity(name="endpoints")
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Endpoint {

    @Id
    @GeneratedValue(generator = "endpoint_gen")
    @SequenceGenerator(name = "endpoint_gen", sequenceName = "endpoints_seq", allocationSize = 1)
    private Long id;
    @NotNull
    private String endpointName;
    private Instant createdOn;
    private Instant updatedOn;
    @Column
    @Size(max = 16)
    private String deviceType;
    @Column
    @Size(max = 16)
    private String deviceVersion;
    @Column
    private Double latitude;
    @Column
    private Double longitude;
    @Column
    private Double elevation;

    @ManyToMany(cascade={CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "endpoint_tags",
            uniqueConstraints = @UniqueConstraint(columnNames={"endpoint_id","tag_id"}),
            joinColumns = @JoinColumn(name = "endpoint_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<UserTag> tags;
    @PrePersist
    private void setCreatedOn() {
        createdOn = Instant.now();
    }

    @PreUpdate
    private void setUpdatedOn() {
        updatedOn = Instant.now();
    }
}
