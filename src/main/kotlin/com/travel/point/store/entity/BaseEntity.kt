package com.travel.point.store.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity {
    @CreatedDate
    var createdDateTime: LocalDateTime = LocalDateTime.MIN
        protected set

    @LastModifiedDate
    var modifiedDateTime: LocalDateTime = LocalDateTime.MIN
        protected set
}
