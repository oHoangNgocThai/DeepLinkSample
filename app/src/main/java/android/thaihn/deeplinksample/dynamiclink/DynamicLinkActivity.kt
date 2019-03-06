package android.thaihn.deeplinksample.dynamiclink

import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.deeplinksample.R
import android.thaihn.deeplinksample.databinding.ActivityDynamicLinkBinding
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

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

        // Share link
        dynamicLinkBinding.btnShareLink.setOnClickListener {
            onShareClicked()
        }
    }

    fun generateContentLink(): Uri {
        val baseUrl = Uri.parse("https://thaihn.vn/invite?id=1111&name=NgocThai")
        val domain = "https://thaihn.page.link"

        val link = FirebaseDynamicLinks.getInstance()
            .createDynamicLink()
            .setLink(baseUrl)
            .setDomainUriPrefix(domain)
            .setIosParameters(DynamicLink.IosParameters.Builder("android.thaihn.deeplinksample").build())
            .setAndroidParameters(DynamicLink.AndroidParameters.Builder("android.thaihn.deeplinksample").build())
            .buildDynamicLink()

        return link.uri
    }

    private fun onShareClicked() {
        val link = generateContentLink()

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, link.toString())

        startActivity(Intent.createChooser(intent, "Share Link"))
    }
}
