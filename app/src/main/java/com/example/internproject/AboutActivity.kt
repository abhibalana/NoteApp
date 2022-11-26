package com.example.internproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        aboutContent.text="1. Your credentials are stored for the security purpose. \n" +
                "2. In future credentials are used to syn your data with cloud. \n" +
                "3. News in application is provided by NewsApi.org.\n" +
                "4. Don't Uninstall app as all data now is stored locally."
    }
}