package com.example.wemi.review


data class ReviewModel(
    val id: String,
    val review: String,
    val score: Double
){
    constructor():this("","",0.0)
}