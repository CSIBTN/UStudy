package com.example.ustudy.util

object IDHandler {
    private var currentPossibleId = 1
    fun getId() = currentPossibleId++
}