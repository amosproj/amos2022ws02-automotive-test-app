package com.amos.infotaimos.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecordDetailsItem(
    var id: String="",
    var eventName: String,
    var vehiclePropertyId: String,
    var value: String,
    var time: String
)