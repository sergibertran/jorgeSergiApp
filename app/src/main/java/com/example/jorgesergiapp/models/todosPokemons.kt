package com.example.jorgesergiapp.models

import android.os.Parcel
import android.os.Parcelable

data class todosPokemons(
    val id: String?,
    val descripcion: String?,
    val nombre: String?,
    val foto: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(descripcion)
        parcel.writeString(nombre)
        parcel.writeString(foto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<todosPokemons> {
        override fun createFromParcel(parcel: Parcel): todosPokemons {
            return todosPokemons(parcel)
        }

        override fun newArray(size: Int): Array<todosPokemons?> {
            return arrayOfNulls(size)
        }
    }
}
