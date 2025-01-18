package ru.taxcom.feedback.storage

import android.content.Context
import com.fasterxml.uuid.Generators
import ru.taxcom.taxcomkit.uuid.UuidStorage
import javax.inject.Inject

class UuidStorageImpl @Inject constructor(
    context: Context
) : UuidStorage {
    private val sharedPreferences =
        context.getSharedPreferences(PREFERENCE_UUID_NAME, Context.MODE_PRIVATE)

    override fun getUuid(): String = sharedPreferences.getString(UUID_KEY, "") ?: ""

    override fun saveUuid() {
        val uuid = Generators.timeBasedGenerator().generate()
        sharedPreferences.edit().putString(UUID_KEY, uuid.toString()).apply()
    }

    companion object {
        private const val PREFERENCE_UUID_NAME = "PREFERENCE_UUID_NAME"
        private const val UUID_KEY = "uuid_key"
    }
}