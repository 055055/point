package com.travel.point.store.entity

import com.travel.point.domain.User
import com.travel.point.type.ActionType
import javax.persistence.*

@Entity
@Table(name = "point_history")
class PointHistoryEntity(pointEntity: PointEntity, actionType: ActionType, point: Int) {
    @Id
    @GeneratedValue
    var seq: Long? = null

    @ManyToOne(cascade = [CascadeType.ALL])
    var pointEntity: PointEntity = pointEntity

    @Embedded
    @Column(name = "userId")
    var user: User = pointEntity.user

    @Enumerated(EnumType.STRING)
    var action: ActionType = actionType
    var point: Int = point
    var totalPoint: Int = pointEntity.point
}
