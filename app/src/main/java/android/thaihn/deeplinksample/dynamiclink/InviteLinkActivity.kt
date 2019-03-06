package android.thaihn.deeplinksample.dynamiclink

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.deeplinksample.databinding.ActivityInviteLinkBinding
import android.util.Log
import com.google.firebase.appinvite.FirebaseAppInvite
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import android.thaihn.deeplinksample.R

class InviteLinkActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "InviteLinkActivity"
    }

    private lateinit var inviteLinkBinding: ActivityInviteLinkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inviteLinkBinding = DataBindingUtil.setContentView(this, R.layout.activity_invite_link)

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(this) { data ->

                    val deepLink = data.link
                    val invite = FirebaseAppInvite.getInvitation(data)
                    if (invite != null) {
                        Log.d(TAG, "invite ${invite.toString()}")
                        val id = invite.invitationId
                        inviteLinkBinding.tvId.text = id
                    }
                }
                .addOnFailureListener(this) {
                    it.printStackTrace()
                    inviteLinkBinding.tvId.text = it.toString()
                }
    }
}
