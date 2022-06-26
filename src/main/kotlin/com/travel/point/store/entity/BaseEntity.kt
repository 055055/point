package com.travel.point.store.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity {
    @Column(nullable = false)
    @CreatedDate
    var createdDateTime: LocalDateTime = LocalDateTime.MIN
        protected set

    @Column(nullable = false)
    @LastModifiedDate
    var modifiedDateTime: LocalDateTime = LocalDateTime.MIN
        protected set
}
