package com.travel.point.domain

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Place(
    @Column(name = "placeId", nullable = false)
    var id: String
    )
