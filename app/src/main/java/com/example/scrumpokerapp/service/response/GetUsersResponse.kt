package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.User
import com.google.gson.annotations.SerializedName

data class GetUsersResponse (@SerializedName("user") val value : User = User())