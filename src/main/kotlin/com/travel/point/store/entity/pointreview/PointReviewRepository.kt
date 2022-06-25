package com.travel.point.store.entity.pointreview

import com.travel.point.type.EventActionType
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PointReviewRepository : JpaRepository<PointReviewEntity, String> {

    @Query(
        """
         SELECT count(pr) FROM PointReviewEntity pr 
            WHERE pr.actionType <> :actionType
            AND pr.placeId = :placeId
            AND pr.userId <> :userId
        """
    )
    fun countNotDeletedPlaceId(
        @Param("placeId") placeId: String,
        @Param("userId") userId: String,
        @Param("actionType") eventActionType: EventActionType = EventActionType.DELETE
    ): Int

    @Query(
        """
         SELECT pr FROM PointReviewEntity pr 
            WHERE pr.actionType <> :reviewActionType
            AND pr.placeId = :placeId
            ORDER BY pr.createdDateTime ASC, pr.id ASC
        """
    )
    fun findFirstReviewByPlaceId(
        @Param("placeId") placeId: String,
        @Param("reviewActionType") eventActionType: EventActionType,
        pageable: Pageable
    ): List<PointReviewEntity>
}
