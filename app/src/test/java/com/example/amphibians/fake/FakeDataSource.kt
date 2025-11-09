package com.example.amphibians.fake

import com.example.amphibians.model.AmphibianInfo

object FakeDataSource {
    const val nameOne = "name One"
    const val typeOne = "typeOne"
    const val descriptionOne = "descriptionOne"
    const val imageSourceOne = ""

    const val nameTwo = "name Two"
    const val typeTwo = "type Two"
    const val descriptionTwo = "description Two"
    const val imageSourceTwo = ""

    val amphibiansList = listOf(
        AmphibianInfo(
            name = nameOne,
            type = typeOne,
            description = descriptionOne,
            imageSource = imageSourceOne
        ),
        AmphibianInfo(
            name = nameTwo,
            type = typeTwo,
            description = descriptionTwo,
            imageSource = imageSourceTwo
        )
    )
}