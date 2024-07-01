package com.example.jorgesergiapp.models

import android.os.Parcel
import android.os.Parcelable

data class todosPokemonObject(

    val descripcion: String?,
    val nombre: String?,
    val foto: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(descripcion)
        parcel.writeString(nombre)
        parcel.writeString(foto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<todosPokemonObject> {
        override fun createFromParcel(parcel: Parcel): todosPokemonObject {
            return todosPokemonObject(parcel)
        }

        override fun newArray(size: Int): Array<todosPokemonObject?> {
            return arrayOfNulls(size)
        }
    }
}
