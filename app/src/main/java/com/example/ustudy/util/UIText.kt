package com.example.ustudy.util

import androidx.annotation.StringRes

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = emptyArray()
    ) : UiText()

    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> UStudyApplication.getApplicationContext().getString(id, args)
        }
    }
}