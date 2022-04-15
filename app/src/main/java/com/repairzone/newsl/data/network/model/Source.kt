package com.repairzone.newsl.data.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Source(
    @SerialName("id") var id: String? = null,
    @SerialName("name") var name: String? = null
)
