package com.monk.androidtest.kotlinxc

import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
  @SerializedName("total_count") val total: Int = 0,
  @SerializedName("items") val items: List<Repo> = emptyList()
) {
  data class Repo(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String?,
    @SerializedName("html_url") val url: String,
    @SerializedName("stargazers_count") val stars: Int,
    @SerializedName("forks_count") val forks: Int,
    @SerializedName("language") val language: String?
  ) {
    override fun toString(): String {
      return "Repo(id=$id, name='$name', fullName='$fullName', description=$description, url='$url', stars=$stars, forks=$forks, language=$language)"
    }
  }

  override fun toString(): String {
    return "RepoSearchResponse(total=$total, items=$items)"
  }


}
