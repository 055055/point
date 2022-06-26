package com.travel.point.web.controller

import com.travel.point.domain.User
import com.travel.point.service.PointAdapter
import com.travel.point.web.param.PointRequest
import org.springframework.web.bind.annotation.*

@RequestMapping("/events")
@RestController
class PointController(
    private val pointAdapter: PointAdapter
) {

    @PostMapping
    fun calculatePoint(@RequestBody request: PointRequest) {
        pointAdapter.calculatePoint(request.convertToChannelDto())
    }

    @GetMapping("/point/{userId}")
    fun getPoint(@PathVariable userId: String) =
        pointAdapter.getPoint(User(userId))?.convertToPointResponse()
}
