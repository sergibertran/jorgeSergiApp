package com.example.jorgesergiapp.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Usuario(
    var usuario: String?,
    var password: String?,
    var foto: String?,
    var email: String?,
    var tipoUsuario: String?

) : Parcelable {
    constructor(parcel: Parcel) : this(
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(usuario)
        parcel.writeString(password)
        parcel.writeString(foto)
        parcel.writeString(email)
        parcel.writeString(tipoUsuario)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }


}