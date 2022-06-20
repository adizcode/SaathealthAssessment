package com.github.adizcode.saathealthassessment.data.model

data class User(
    val firstName: String,
    val lastName: String,
    val mobileNum: String,
    var points: Int,
    var currentLevel: Int,
    var currentBadge: Int
) {
    fun incrementPoints() {
        points++

        if (points % 5 == 0) {
            currentLevel++

            if (currentLevel % 5 == 0) {
                currentBadge++
            }
        }
    }
}

val fakeUser = User(
    firstName = "Advitiay",
    lastName = "Anand",
    mobileNum = "931384134",
    points = 2,
    currentLevel = 0,
    currentBadge = 0,
)
