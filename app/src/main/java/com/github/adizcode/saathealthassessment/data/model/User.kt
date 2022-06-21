package com.github.adizcode.saathealthassessment.data.model

data class User(
    val uid: String,
    var firstName: String,
    var lastName: String,
    var mobileNum: String,
    var points: Long,
    var currentLevel: Long,
    var currentBadge: Long
) {
    constructor(uid: String, userMap: Map<String, Any>) : this(
        uid = uid,
        firstName = userMap["firstName"] as String,
        lastName = userMap["lastName"] as String,
        mobileNum = userMap["mobileNum"] as String,
        points = userMap["points"] as Long,
        currentLevel = userMap["currentLevel"] as Long,
        currentBadge = userMap["currentBadge"] as Long
    )

    fun incrementPoints() {
        points++

        if (points % 5 == 0L) {
            currentLevel++

            if (currentLevel % 5 == 0L) {
                currentBadge++
            }
        }
    }

    fun hasCredentialsSet(): Boolean =
        firstName.isNotBlank() && lastName.isNotBlank() && mobileNum.isNotBlank()

    fun toMap(): Map<String, Any> {
        return hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "mobileNum" to mobileNum,
            "points" to points,
            "currentLevel" to currentLevel,
            "currentBadge" to currentBadge,
        )
    }

}
