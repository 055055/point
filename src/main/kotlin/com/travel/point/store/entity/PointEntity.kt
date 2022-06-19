package com.travel.point.store.entity

import com.travel.point.domain.User
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class PointEntity {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Embedded
    var user: User? = null
    var point: Long = 0L
}
