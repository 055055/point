package com.travel.point.store.entity.pointhistory

import com.travel.point.domain.User
import com.travel.point.store.entity.BaseEntity
import com.travel.point.store.entity.point.PointEntity
import com.travel.point.store.entity.pointreview.PointReviewEntity
import com.travel.point.type.PointType
import javax.persistence.*

@Entity
@Table(
    name = "POINT_HISTORY",
    indexes = [
        Index(name = "point_history_index_1", columnList = "createdDateTime")
    ]
)
class PointHistoryEntity(
    pointEntity: PointEntity,
    pointReviewEntity: PointReviewEntity,
    pointType: PointType,
    point: Int,
    comment: String
) : BaseEntity() {
    @Id
    @GeneratedValue
    var seq: Long? = null

    @JoinColumn(name = "point_id")
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var pointEntity: PointEntity = pointEntity

    @JoinColumn(name = "review_id")
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var pointReviewEntity: PointReviewEntity? = pointReviewEntity

    @Embedded
    var user: User = pointEntity.user

    @Enumerated(EnumType.STRING)
    var pointType: PointType = pointType

    var point: Int = point

    var totalPoint: Int = pointEntity.point

    var comment: String = comment
}
