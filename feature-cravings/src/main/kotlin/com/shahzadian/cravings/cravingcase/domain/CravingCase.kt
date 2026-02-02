package com.shahzadian.cravings.cravingcase.domain

class CravingCase(
    val strength: CravingStrength,
    val caseMillis: Long,
)

enum class CravingStrength {
    LOW,
    MEDIUM,
    STRONG
}
