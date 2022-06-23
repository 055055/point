package com.travel.point.store.entity.point

import com.travel.point.domain.User
import com.travel.point.store.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "POINT")
class PointEntity(user: User)
    : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var seq: Long? = null

    @Embedded
    @Column(name = "userId")
    var user: User = user
    var point: Int = 0

    fun addPoint(score: Int) {
        this.point += score
    }

    fun subtractPoint(score: Int) {
        this.point -= score
    }
}
