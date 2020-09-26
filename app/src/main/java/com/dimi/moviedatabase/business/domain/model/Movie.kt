package com.dimi.moviedatabase.business.domain.model

import android.os.Parcelable
import com.dimi.moviedatabase.business.domain.state.MediaType
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Movie(
    override var id: Long,
    override var title: String,
    override var popularity: Double,
    override var voteCount: Int,
    override var releaseDate: Date? = null,
    override var voteAverage: Float,
    override var overview: String,
    var tagLine: String?,
    override var posterPath: String?,
    override var backdropPath: String?,
    override var genres: List<Int>? = null,
    override var castList: List<Person>? = null,
    var budget: Int? = null,
    var revenue: Long? = null,
    override var status: String? = null,
    override var homepage: String? = null,
    override var originalTitle: String? = null,
    override var runtime: Int? = null,
    var imdbId: String? = null,
    override var character: String? = null,
    var actorId: Int? = null,
    var order: Int? = null
) : Parcelable,
    Media(
        id,
        title,
        popularity,
        voteCount,
        voteAverage,
        overview,
        posterPath,
        backdropPath,
        genres,
        castList,
        runtime,
        status,
        originalTitle,
        homepage,
        character,
        releaseDate,
        MediaType.MOVIE
    ) {

    override fun toString(): String {
        return "Movie(id=$id, title='$title', popularity=$popularity, voteCount=$voteCount, releaseDate=$releaseDate, voteAverage=$voteAverage, overview='$overview', tagLine=$tagLine, posterPath=$posterPath, backdropPath=$backdropPath, genres=$genres, castList=$castList, budget=$budget, revenue=$revenue, status=$status, homepage=$homepage, originalTitle=$originalTitle, runtime=$runtime, imdbId=$imdbId, character=$character, actorId=$actorId)"
    }
}