package com.travel.point.store.entity.pointreview

import com.travel.point.domain.Photo
import com.travel.point.domain.Place
import com.travel.point.domain.Review
import com.travel.point.domain.User
import com.travel.point.store.entity.BaseEntity
import com.travel.point.type.EventActionType
import javax.persistence.*

@Entity
@Table(name = "POINT_REVIEW")
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
    var actionType: EventActionType = review.actionType

    fun updatePointReview(review: Review) {
        this.content = review.content
        this.userId = review.user.id
        this.placeId = review.place.id
        this.photo = review.photo.ids
        this.actionType = review.actionType
    }

    fun convertToReview() =
        Review(
            id = this.id,
            content = this.content,
            user = User(this.userId),
            place = Place(this.placeId),
            photo = Photo(this.photo),
            actionType = this.actionType
        )
}
