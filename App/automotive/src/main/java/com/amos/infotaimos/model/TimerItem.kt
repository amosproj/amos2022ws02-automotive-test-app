package com.amos.infotaimos.model

class TimerItem()  {
    var actionId: String = ""
    private var start: String = ""
    private var end: String = ""
    val time: String
        get() = "$start - $end"
    var duration: String = ""
    var description: String = ""

    constructor(actionId: String, start: String, end: String, duration: String, description: String) : this() {
        this.actionId = actionId
        this.start = start
        this.end = end
        this.duration = duration
        this.description = description
    }

}