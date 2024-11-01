package dltcore

public typealias IdInt = Int
public typealias IdString = String

public fun IdString.asIntValue(): Int {
    if (this.length > 4) {
        throw IllegalArgumentException("AppId '$this' may not be longer than 4 bytes")
    }

    val data = this.toByteArray(Charsets.ISO_8859_1)
    var value = 0
    for (i in data.indices) {
        value = value or ((data[i].toInt() and 0xFF) shl ((3 - i) * 8))
    }
    return value
}

public fun IdInt.asStringValue(): String =
    String(
        byteArrayOf(
            if (this and 0xFF000000.toInt() == 0) ' '.code.toByte() else (this and 0xFF000000.toInt() shr 24).toByte(),
            if (this and 0xFF0000 == 0) ' '.code.toByte() else (this and 0xFF0000 shr 16).toByte(),
            if (this and 0xFF00 == 0) ' '.code.toByte() else (this and 0xFF00 shr 8).toByte(),
            if (this and 0xFF == 0) ' '.code.toByte() else (this and 0xFF).toByte(),
        ), Charsets.US_ASCII
    ).trim()
