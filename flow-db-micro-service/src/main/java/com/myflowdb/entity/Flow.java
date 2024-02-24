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
import java.util.List;
import java.util.Set;

@Entity(name = "flows")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Flow {
    @Id
    @GeneratedValue(generator = "flow_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "flow_gen", sequenceName = "flows_seq", allocationSize = 1)
    @Column(insertable = false, updatable = false, nullable = false)
    private Long id;
    @Column(unique =  true)
    @NotNull
    @Size(max = 255)
    private String flowName;

    @Column(updatable = false)
    private Instant createdOn;

    @Column(insertable = false)
    private Instant updatedOn;

    @Column
    private String slaProfile;

    @NotNull
    private String reflectorType;

    @Column
    @Size(max = 255)
    private String testGroup;
    @Column
    @Size(max = 255)
    private String codec;
    @Column
    @Size(max = 255)
    private String maintenanceTimeZone;
    @Column
    @Size(max = 2000)
    private String remarks;
    @Column
    @Size(max = 255)
    private String anchor;
    @Column
    @Size(max = 255)
    private String uiMonitoringStatus;
    @Column
    @Size(max = 255)
    private String muteAlarm;
    @ManyToMany(cascade={CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "flow_endpoints",
            uniqueConstraints = @UniqueConstraint(columnNames={"flow_id","endpoint_id"}),
            joinColumns = @JoinColumn(name = "flow_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "endpoint_id", referencedColumnName = "id")
    )
    @OrderColumn(name = "endpoint_seq")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Endpoint> endpoints;

    @ManyToMany(cascade={CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "flow_tags",
            uniqueConstraints = @UniqueConstraint(columnNames={"flow_id","tag_id"}),
            joinColumns = @JoinColumn(name = "flow_id", referencedColumnName = "id"),
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
