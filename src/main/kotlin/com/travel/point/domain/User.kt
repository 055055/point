package com.travel.point.domain

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class User(@Column(name = "userId")var id: String)
