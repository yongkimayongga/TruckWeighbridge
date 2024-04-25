package com.truck.weighbridge.persistence

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_truck")
class Truck(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var driverName: String?,
    var licenseNumber: String?,
    var date: String?,
    var time: String?,
    var inboundTonnage: String?,
    var outboundTonnage: String?,
    var netWeight: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    // Method #2
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(driverName)
        parcel.writeString(licenseNumber)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(inboundTonnage)
        parcel.writeString(outboundTonnage)
        parcel.writeString(netWeight)
    }

    // Method #3
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Truck> {
        // Method #4
        override fun createFromParcel(parcel: Parcel): Truck {
            return Truck(parcel)
        }

        // Method #5
        override fun newArray(size: Int): Array<Truck?> {
            return arrayOfNulls(size)
        }
    }

    // Method #6
    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (driverName?.hashCode() ?: 0)
        result = 31 * result + (licenseNumber?.hashCode() ?: 0)
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + (time?.hashCode() ?: 0)
        result = 31 * result + (inboundTonnage?.hashCode() ?: 0)
        result = 31 * result + (outboundTonnage?.hashCode() ?: 0)
        result = 31 * result + (netWeight?.hashCode() ?: 0)
        return result
    }
}