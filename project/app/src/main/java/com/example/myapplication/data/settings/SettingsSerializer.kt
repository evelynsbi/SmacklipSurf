package com.example.myapplication.data.settings

import androidx.datastore.core.Serializer
import com.example.myapplication.Settings
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

class SettingsSerializer: Serializer<Settings> {
    override val defaultValue: Settings
        get() = Settings.getDefaultInstance().toBuilder().setTest(0.0).build()

    override suspend fun readFrom(input: InputStream): Settings {
        return try {
            Settings.parseFrom(input)
        }catch (e: InvalidProtocolBufferException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Settings, output: OutputStream) {
        t.writeTo(output)
    }
}