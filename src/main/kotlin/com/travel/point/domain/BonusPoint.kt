package com.travel.point.domain

data class BonusPoint(var score: Int = 0) {
    fun addScore(score: Int): Int {
        this.score += score
        return this.score
    }

    fun subtractScore(score: Int): Int {
        return if (this.score > score) {
            this.score - score
        } else {
            score - this.score
        }
    }
}
