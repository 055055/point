package com.travel.point.service

import com.travel.point.domain.User
import com.travel.point.service.param.PointRequest
import com.travel.point.type.EventActionType
import com.travel.point.type.PointEventType
import org.springframework.stereotype.Service

@Service
class PointAdapter(
        private val pointFactory: PointFactory
) {

    suspend fun calculatePoint(request: PointRequest) {
        when (request.pointEventType) {
            PointEventType.REVIEW -> {
                when (request.review.actionType) {
                    EventActionType.ADD -> pointFactory.getPointService(request.pointEventType)?.add(request)
                    EventActionType.MOD -> pointFactory.getPointService(request.pointEventType)?.modify(request)
                    EventActionType.DELETE -> pointFactory.getPointService(request.pointEventType)?.delete(request)
                }
            }
        }
    }

    suspend fun getPoint(user: User) = pointFactory.getPointService(PointEventType.REVIEW)?.getPoint(user)
}
