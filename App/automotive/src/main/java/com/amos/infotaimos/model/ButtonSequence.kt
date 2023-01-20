package com.amos.infotaimos.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ButtonSequence(val id: String, val sequence: List<Int>)