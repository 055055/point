package com.travel.point.store.entity.pointhistory

import com.travel.point.domain.User
import com.travel.point.store.entity.BaseEntity
import com.travel.point.store.entity.point.PointEntity
import com.travel.point.store.entity.pointreview.PointReviewEntity
import com.travel.point.type.ActionType
import javax.persistence.*

@Entity
@Table(name = "point_history")
class PointHistoryEntity(pointEntity: PointEntity, pointReviewEntity: PointReviewEntity, actionType: ActionType, point: Int, comment: String)
    : BaseEntity() {
    @Id
    @GeneratedValue
    var seq: Long? = null

    @ManyToOne(cascade = [CascadeType.ALL])
    var pointEntity: PointEntity = pointEntity

    @ManyToOne(cascade = [CascadeType.ALL])
    var pointReviewEntity: PointReviewEntity = pointReviewEntity

    @Embedded
    @Column(name = "userId")
    var user: User = pointEntity.user

    @Enumerated(EnumType.STRING)
    var action: ActionType = actionType
    var point: Int = point
    var totalPoint: Int = pointEntity.point

    var comment: String = comment
}
