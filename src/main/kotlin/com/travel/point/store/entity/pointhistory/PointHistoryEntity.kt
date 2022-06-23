package com.travel.point.store.entity.pointhistory

import com.travel.point.domain.User
import com.travel.point.store.entity.BaseEntity
import com.travel.point.store.entity.point.PointEntity
import com.travel.point.store.entity.pointreview.PointReviewEntity
import com.travel.point.type.PointActionType
import com.travel.point.type.PointType
import javax.persistence.*

@Entity
@Table(name = "POINT_HISTORY")
class PointHistoryEntity(pointEntity: PointEntity, pointReviewEntity: PointReviewEntity,
                         pointType: PointType, point: Int, comment: String)
    : BaseEntity() {
    @Id
    @GeneratedValue
    var seq: Long? = null

    @ManyToOne(cascade = [CascadeType.ALL])
    var pointEntity: PointEntity = pointEntity

    @JoinColumn(name = "point_review_id")
    @ManyToOne(cascade = [CascadeType.ALL])
    var pointReviewEntity: PointReviewEntity = pointReviewEntity

    @Embedded
    @Column(name = "userId")
    var user: User = pointEntity.user

    @Enumerated(EnumType.STRING)
    var pointType: PointType = pointType
    var point: Int = point
    var totalPoint: Int = pointEntity.point

    var comment: String = comment
}
