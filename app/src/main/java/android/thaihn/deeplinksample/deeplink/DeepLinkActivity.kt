package android.thaihn.deeplinksample.deeplink

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.deeplinksample.R
import android.thaihn.deeplinksample.databinding.ActivityDeeplinkBinding

class DeepLinkActivity : AppCompatActivity() {

    companion object {
        private const val PARAMETER_ID = "id"
        private const val PARAMETER_NAME = "name"
    }

    private lateinit var deeplinkBinding: ActivityDeeplinkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deeplinkBinding = DataBindingUtil.setContentView(this, R.layout.activity_deeplink)

        newIntent(intent)
    }

    private fun newIntent(intent: Intent) {
        val action = intent.action
        intent.data?.let {
            val id = it.getQueryParameter(PARAMETER_ID)
            val name = it.getQueryParameter(PARAMETER_NAME)
            val text = "$id /n $name"
            deeplinkBinding.tvId.text = text
        }
    }
}
