package com.myflowdb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity(name="tags")
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTag {
    @Id
    @GeneratedValue(generator = "tag_gen")
    @SequenceGenerator(name = "tag_gen", sequenceName = "tags_seq", allocationSize = 1)
    @Column(insertable = false, updatable = false, nullable = false)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Column
    @Size(max = 255)
    private String tagName;

    @Column
    @Size(max = 255)
    private String tagValue;

    @Column(updatable = false)
    private Instant createdOn;

    @Column(insertable = false)
    private Instant updatedOn;

    @PrePersist
    private void setCreatedOn() {
        createdOn = Instant.now();
    }

    @PreUpdate
    private void setUpdatedOn() {
        updatedOn = Instant.now();
    }
}
