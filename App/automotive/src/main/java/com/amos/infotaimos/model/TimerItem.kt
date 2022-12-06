package com.amos.infotaimos.model

class TimerItem() {
    var actionId: String = ""
    var start: String = ""
    var end: String = ""
    val time: String
        get() = "$start" + if (end != "") " - $end" else ""
    private val duration: String
        get() {
            val totalSeconds = getSeconds(end) - getSeconds(start)
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60
            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
    var actionIdAndDuration: String = ""
        get() = "$actionId - $duration"
    var description: String = ""

    constructor(actionId: String, start: String, end: String, description: String) : this() {
        this.actionId = actionId
        this.start = start
        this.end = end
        this.description = description
    }
}

private fun getSeconds(time: String): Int {
    val timeArray = time.split(":")
    val hours = timeArray[0].toInt()
    val minutes = timeArray[1].toInt()
    val seconds = timeArray[2].toInt()
    return hours * 3600 + minutes * 60 + seconds
}
