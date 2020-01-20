package com.example.kotlinsub4.ui.tv.pojo

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.kotlinsub4.ui.tv.TVDao
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

@Entity(tableName = TVDao.TABLE_NAME)
class ResultsItem @Ignore constructor(`in`: Parcel) : Parcelable {
    constructor() : this(Parcel.obtain())

    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    var firstAirDate: String?

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var overview: String?

    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    var originalLanguage: String?

    @Ignore
    @SerializedName("genre_ids")
    var genreIds: List<Int>

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String?

    @Ignore
    @ColumnInfo(name = "origin_country")
    @SerializedName("origin_country")
    var originCountry: List<String>?

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    var backdropPath: String?

    @ColumnInfo(name = "original_name")
    @SerializedName("original_name")
    var originalName: String?

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    var popularity: Double

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var voteAverage: Double

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String?

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    var voteCount: Int

    override fun toString(): String {
        return "ResultsItem{" +
                "first_air_date = '" + firstAirDate + '\''.toString() +
                ",overview = '" + overview + '\''.toString() +
                ",original_language = '" + originalLanguage + '\''.toString() +
                ",genre_ids = '" + genreIds + '\''.toString() +
                ",poster_path = '" + posterPath + '\''.toString() +
                ",origin_country = '" + originCountry + '\''.toString() +
                ",backdrop_path = '" + backdropPath + '\''.toString() +
                ",original_name = '" + originalName + '\''.toString() +
                ",popularity = '" + popularity + '\''.toString() +
                ",vote_average = '" + voteAverage + '\''.toString() +
                ",name = '" + name + '\''.toString() +
                ",id = '" + id + '\''.toString() +
                ",vote_count = '" + voteCount + '\''.toString() +
                "}"
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.firstAirDate)
        dest.writeString(this.overview)
        dest.writeString(this.originalLanguage)
        dest.writeList(this.genreIds)
        dest.writeString(this.posterPath)
        dest.writeStringList(this.originCountry)
        dest.writeString(this.backdropPath)
        dest.writeString(this.originalName)
        dest.writeDouble(this.popularity)
        dest.writeDouble(this.voteAverage)
        dest.writeString(this.name)
        dest.writeInt(this.id)
        dest.writeInt(this.voteCount)
    }

    init {
        this.firstAirDate = `in`.readString()
        this.overview = `in`.readString()
        this.originalLanguage = `in`.readString()
        this.genreIds = ArrayList()
        `in`.readList(this.genreIds, Int::class.java.classLoader)
        this.posterPath = `in`.readString()
        this.originCountry = `in`.createStringArrayList()
        this.backdropPath = `in`.readString()
        this.originalName = `in`.readString()
        this.popularity = `in`.readDouble()
        this.voteAverage = `in`.readDouble()
        this.name = `in`.readString()
        this.id = `in`.readInt()
        this.voteCount = `in`.readInt()
    }

    private companion object CREATOR : Parcelable.Creator<ResultsItem> {
        override fun createFromParcel(parcel: Parcel): ResultsItem {
            return ResultsItem(parcel)
        }

        override fun newArray(size: Int): Array<ResultsItem?> {
            return arrayOfNulls(size)
        }
    }
}