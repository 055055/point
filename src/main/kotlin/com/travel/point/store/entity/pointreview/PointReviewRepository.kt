package com.travel.point.store.entity.pointreview

import com.travel.point.type.ActionType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PointReviewRepository : JpaRepository<PointReviewEntity, String> {

    @Query(
        """
         SELECT count(pr) FROM PointReviewEntity pr 
            WHERE pr.actionType <> :actionType
            AND pr.placeId = :placeId
        """
    )
    fun findByPlaceId(@Param("placeId") placeId: String, @Param("actionType") actionType: ActionType = ActionType.DELETE): Int
}
