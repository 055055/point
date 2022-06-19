package com.travel.point.store.entity

import com.travel.point.domain.User
import javax.persistence.*

@Entity
@Table(name = "point")
class PointEntity(user: User) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var seq: Long? = null

    @Embedded
    @Column(name = "userId")
    var user: User = user
    var point: Int = 0

    fun addPoint(point: Int) {
        this.point += point
    }
}
