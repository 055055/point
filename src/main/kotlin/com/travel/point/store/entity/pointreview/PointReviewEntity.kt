package com.travel.point.store.entity.pointreview

import com.travel.point.domain.Review
import com.travel.point.store.entity.BaseEntity
import com.travel.point.type.ReviewActionType
import javax.persistence.*

@Entity
@Table(name = "point_review")
class PointReviewEntity(review: Review)
    : BaseEntity() {
    @Id
    var id: String = review.id
    var content: String = review.content
    var userId: String = review.user.id
    var placeId: String = review.place.id

    @ElementCollection
    var photo: List<String> = review.photo.ids

    @Enumerated(EnumType.STRING)
    var actionType: ReviewActionType = review.actionType
}
