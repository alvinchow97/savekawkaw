package com.example.debit

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(private val context: Context) {

    private val INTRO = "intro"
    private val TELNO = "telno"
    private val PASSWORD = "password"
    private val NICKNAME = "nickname"
    private val OCCUPATION = "occupation"
    private val BIRTHDAY = "birthday"
    private val app_prefs: SharedPreferences

    init {
        app_prefs = context.getSharedPreferences(
            "shared",
            Context.MODE_PRIVATE
        )
    }

    fun putIsLogin(loginorout: Boolean) {
        val edit = app_prefs.edit()
        edit.putBoolean(INTRO, loginorout)
        edit.commit()
    }

    fun getIsLogin(): Boolean {
        return app_prefs.getBoolean(INTRO, false)
    }

    fun putTelNo(loginorout: String) {
        val edit = app_prefs.edit()
        edit.putString(TELNO, loginorout)
        edit.commit()
    }

    fun getTelNo(): String? {
        return app_prefs.getString(TELNO, "")
    }

    fun putPassword(loginorout: String) {
        val edit = app_prefs.edit()
        edit.putString(PASSWORD, loginorout)
        edit.commit()
    }

    fun getPassword(): String? {
        return app_prefs.getString(PASSWORD, "")
    }

    fun putNickname(loginorout: String) {
        val edit = app_prefs.edit()
        edit.putString(NICKNAME, loginorout)
        edit.commit()
    }

    fun getNickname(): String? {
        return app_prefs.getString(NICKNAME, "")
    }

    fun putOccupation(loginorout: String) {
        val edit = app_prefs.edit()
        edit.putString(OCCUPATION, loginorout)
        edit.commit()
    }

    fun getOccupation(): String? {
        return app_prefs.getString(OCCUPATION, "")
    }

    fun putBirthday(loginorout: String) {
        val edit = app_prefs.edit()
        edit.putString(BIRTHDAY, loginorout)
        edit.commit()
    }

    fun getBirthday(): String? {
        return app_prefs.getString(BIRTHDAY, "")
    }
}