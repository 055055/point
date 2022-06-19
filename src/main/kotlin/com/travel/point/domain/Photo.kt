package com.travel.point.domain

import javax.persistence.ElementCollection
import javax.persistence.Embeddable

@Embeddable
data class Photo(@ElementCollection var ids: List<String>)
