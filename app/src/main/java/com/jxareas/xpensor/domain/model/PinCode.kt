package com.jxareas.xpensor.domain.model

@JvmInline
value class PinCode(val code: String) {
    companion object {
        const val EMPTY_CODE = ""
        const val MAX_CODE_LENGTH = 4
    }
}