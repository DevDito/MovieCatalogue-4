package com.example.kotlinsub4.ui.mv.pojo

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.kotlinsub4.ui.mv.MVDao
import com.google.gson.annotations.SerializedName

@Entity(tableName = MVDao.TABLE_NAME)
class ResultsItem @Ignore constructor (`in`: Parcel) : Parcelable {
    constructor() : this(Parcel.obtain())

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var overview: String?

    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    var originalLanguage: String?

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    var originalTitle: String?

    @ColumnInfo(name = "video")
    @SerializedName("video")
    var video: Boolean

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String?

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String?

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    var backdropPath: String?

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    var releaseDate: String?

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    var popularity: Double

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var voteAverage: Double

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int

    @ColumnInfo(name = "adult")
    @SerializedName("adult")
    var adult: Boolean

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    var voteCount: Int

    override fun toString(): String {
        return "ResultsItem{" +
                "overview = '" + overview + '\''.toString() +
                ",original_language = '" + originalLanguage + '\''.toString() +
                ",original_title = '" + originalTitle + '\''.toString() +
                ",video = '" + video + '\''.toString() +
                ",title = '" + title + '\''.toString() +
                ",poster_path = '" + posterPath + '\''.toString() +
                ",backdrop_path = '" + backdropPath + '\''.toString() +
                ",release_date = '" + releaseDate + '\''.toString() +
                ",popularity = '" + popularity + '\''.toString() +
                ",vote_average = '" + voteAverage + '\''.toString() +
                ",id = '" + id + '\''.toString() +
                ",adult = '" + adult + '\''.toString() +
                ",vote_count = '" + voteCount + '\''.toString() +
                "}"
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.overview)
        dest.writeString(this.originalLanguage)
        dest.writeString(this.originalTitle)
        dest.writeByte(if (this.video) 1.toByte() else 0.toByte())
        dest.writeString(this.title)
        dest.writeString(this.posterPath)
        dest.writeString(this.backdropPath)
        dest.writeString(this.releaseDate)
        dest.writeDouble(this.popularity)
        dest.writeDouble(this.voteAverage)
        dest.writeInt(this.id)
        dest.writeByte(if (this.adult) 1.toByte() else 0.toByte())
        dest.writeInt(this.voteCount)
    }

    init {
        this.overview = `in`.readString()
        this.originalLanguage = `in`.readString()
        this.originalTitle = `in`.readString()
        this.video = `in`.readByte().toInt() != 0
        this.title = `in`.readString()
        this.posterPath = `in`.readString()
        this.backdropPath = `in`.readString()
        this.releaseDate = `in`.readString()
        this.popularity = `in`.readDouble()
        this.voteAverage = `in`.readDouble()
        this.id = `in`.readInt()
        this.adult = `in`.readByte().toInt() != 0
        this.voteCount = `in`.readInt()
    }


    companion object CREATOR : Parcelable.Creator<ResultsItem> {
        override fun createFromParcel(parcel: Parcel): ResultsItem {
            return ResultsItem(parcel)
        }

        override fun newArray(size: Int): Array<ResultsItem?> {
            return arrayOfNulls(size)
        }
    }
}