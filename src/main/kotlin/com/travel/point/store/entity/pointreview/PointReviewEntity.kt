package com.travel.point.store.entity.pointreview

import com.travel.point.domain.Photo
import com.travel.point.domain.Place
import com.travel.point.domain.Review
import com.travel.point.domain.User
import com.travel.point.store.entity.BaseEntity
import com.travel.point.type.EventActionType
import javax.persistence.*

@Entity
@Table(
    name = "POINT_REVIEW",
    indexes = [
        Index(name = "point_review_index_1", columnList = "placeId, userId"),
        Index(name = "point_review_index_2", columnList = "createdDateTime")
    ]
)
class PointReviewEntity(review: Review)
    : BaseEntity() {
    @Id
    var id: String = review.id
        protected set

    @Column(nullable = false)
    var content: String = review.content
        protected set

    @Embedded
    var user: User = review.user
        protected set

    @Embedded
    var place: Place = review.place
        protected set

    @CollectionTable(
        name = "POINT_REVIEW_PHOTO",
        joinColumns = [JoinColumn(name = "point_review_id",
            referencedColumnName = "id")]
    )
    @ElementCollection(fetch = FetchType.EAGER)
    var photo: List<String> = review.photo.ids
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var actionType: EventActionType = review.actionType
        protected set

    fun updatePointReview(review: Review) {
        this.content = review.content
        this.user = review.user
        this.place = review.place
        this.photo = review.photo.ids
        this.actionType = review.actionType
    }

    fun convertToReview() =
        Review(
            id = this.id,
            content = this.content,
            user = this.user,
            place = this.place,
            photo = Photo(this.photo),
            actionType = this.actionType
        )
}
