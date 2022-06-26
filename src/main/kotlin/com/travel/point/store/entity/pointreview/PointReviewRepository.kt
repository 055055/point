package com.travel.point.store.entity.pointreview

import com.travel.point.type.EventActionType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PointReviewRepository : JpaRepository<PointReviewEntity, String> {

    @Query(
        """
         SELECT count(pr) FROM PointReviewEntity pr 
            WHERE pr.actionType <> :actionType
            AND pr.place.id = :placeId
            AND pr.user.id <> :userId
        """
    )
    fun countNotDeletedPlaceId(
        @Param("placeId") placeId: String,
        @Param("userId") userId: String,
        @Param("actionType") eventActionType: EventActionType = EventActionType.DELETE
    ): Int
}
