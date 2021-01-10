package io.minoro75.test_123

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.core.text.HtmlCompat
import dagger.hilt.android.AndroidEntryPoint
import io.minoro75.test_123.ui.main.MainFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_activity)

	}
}