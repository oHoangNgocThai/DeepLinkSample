package android.thaihn.deeplinksample.dynamiclink

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.deeplinksample.databinding.ActivityDynamicLinkBinding
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import android.thaihn.deeplinksample.R
import android.util.Log
import java.lang.StringBuilder

class DynamicLinkActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "TTT"

        private const val PARAMETER_NAME = "name"
    }

    private lateinit var dynamicLinkBinding: ActivityDynamicLinkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dynamicLinkBinding = DataBindingUtil.setContentView(this, R.layout.activity_dynamic_link)

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(this) {
                    it?.link?.let { uri ->
                        val name = uri.getQueryParameter(PARAMETER_NAME)
                        val path = StringBuilder()
                        uri.pathSegments?.forEach { it ->
                            path.append(it)
                        }
                        dynamicLinkBinding.tvId.text = "name:$name - id:$path"
                    }
                }
                .addOnFailureListener(this) {
                    it.printStackTrace()
                    dynamicLinkBinding.tvId.text = it.message
                }
    }
}
