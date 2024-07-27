package com.example.ghtk_intern_week2.model.network.response

import com.google.gson.annotations.SerializedName

/*
{
  "success":true,
  "message":"",
  "full_name":"Nguyen Quang Chinh",
  "position":"Top Expert",
  "history":[
    {
      "title":"2 năm làm việc",
      "is_up":true
    },
    {
      "title":"3 năm làm việc",
      "is_up":true
    },
    {
      "title":"4 năm làm việc",
      "is_up":true
    },
    {
      "title":"Đề suất lên vị trí Top Expert",
      "is_up":true
    },
    {
      "title":"Giáng chức xuống làm dân thường",
      "is_up":false
    }
  ]
}
 */
data class Response(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("history")
    val history: List<History>
)
