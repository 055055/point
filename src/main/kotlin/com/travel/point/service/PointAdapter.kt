package com.travel.point.service

import com.travel.point.domain.User
import com.travel.point.service.param.PointChannelDto
import com.travel.point.type.EventActionType
import com.travel.point.type.PointEventType
import com.travel.point.type.PointType
import org.springframework.stereotype.Service

@Service
class PointAdapter(
    private val pointFactory: PointFactory
) {

    fun calculatePoint(request: PointChannelDto) {
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

    fun getPoint(user: User) = pointFactory.getPointService(PointEventType.REVIEW)?.getPoint(user)
}
