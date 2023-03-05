package com.example.hw2

data class GradesModel(
    private var homeworks: ArrayList<Int> = arrayListOf(100, 100, 100, 100, 100),
    private var participation: Int = 100,
    private var presentation: Int = 100,
    private var midterm1: Int = 100,
    private var midterm2: Int = 100,
    private var finalProject: Int = 100,
) {
    fun setHomeworks(homeworks: String) {
        if (homeworks.isEmpty()) {
            this.homeworks = arrayListOf(100, 100, 100, 100, 100)
            return
        }
        this.homeworks = extractValues(homeworks)
    }

    fun setParticipation(participation: String) {
        if (participation.isEmpty()) {
            this.participation = 100
            return
        }
        this.participation = extractValue(participation)
    }

    fun setPresentation(presentation: String) {
        if (presentation.isEmpty()) {
            this.presentation = 100
            return
        }
        this.presentation = extractValue(presentation)
    }

    fun setMidterm1(midterm1: String) {
        if (midterm1.isEmpty()) {
            this.midterm1 = 100
            return
        }
        this.midterm1 = extractValue(midterm1)
    }

    fun setMidterm2(midterm2: String) {
        if (midterm2.isEmpty()) {
            this.midterm2 = 100
            return
        }
        this.midterm2 = extractValue(midterm2)
    }

    fun setFinalProject(finalProject: String) {
        if (finalProject.isEmpty()) {
            this.finalProject = 100
            return
        }
        this.finalProject = extractValue(finalProject)
    }

    private fun extractValue(value: String): Int {
        return if (value == "") {
            100
        } else {
            value.toInt()
        }
    }

    private fun extractValues(value: String): ArrayList<Int> {
        val arrayList = ArrayList(value.split(",").map { it.toInt() })

        for (i in arrayList.size..4) {
            arrayList.add(100)
        }
        return arrayList
    }

    fun calculateFinalGrade(): String {
        return (homeworks.average() * .2 + participation * .1 + presentation * .1 + midterm1 * .1 + midterm2 * .2 + finalProject * .3).toString()
    }
}