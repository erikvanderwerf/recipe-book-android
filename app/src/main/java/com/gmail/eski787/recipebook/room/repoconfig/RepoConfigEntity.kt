package com.gmail.eski787.recipebook.room.repoconfig

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo_configs")
data class RepoConfigEntity(
    @PrimaryKey(autoGenerate = true) val rowId: Int,
    @ColumnInfo(name = "type") val type: Int,
    @ColumnInfo(name = "blob", typeAffinity = ColumnInfo.BLOB) val blob: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RepoConfigEntity

        if (rowId != other.rowId) return false
        if (type != other.type) return false
        if (!blob.contentEquals(other.blob)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rowId
        result = 31 * result + type
        result = 31 * result + blob.contentHashCode()
        return result
    }
}