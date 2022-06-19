package com.travel.point.store

import com.travel.point.domain.Point
import com.travel.point.store.entity.PointEntity
import com.travel.point.store.entity.PointHistoryEntity
import com.travel.point.store.entity.PointHistoryRepository
import com.travel.point.store.entity.PointRepository
import com.travel.point.type.ActionType
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PointStoreImpl(
    private val pointRepository: PointRepository,
    private val pointHistoryRepository: PointHistoryRepository
) : PointStore {

    @Transactional
    override fun addPoint(point: Point) {
        val pointEntity = pointRepository.findByUserId(point.user.id).orElse(
            PointEntity(user = point.user)
        )

       pointEntity.addPoint(point = point.point)

        pointHistoryRepository.save(
            PointHistoryEntity(
                pointEntity = pointEntity,
                actionType = ActionType.ADD,
                point = point.point
            )
        )
    }
}
