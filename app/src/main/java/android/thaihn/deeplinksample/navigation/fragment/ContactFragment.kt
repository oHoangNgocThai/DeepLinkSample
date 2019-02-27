package android.thaihn.deeplinksample.navigation.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.thaihn.deeplinksample.databinding.FragmentContactBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.thaihn.deeplinksample.R
import androidx.navigation.fragment.NavHostFragment

class ContactFragment : Fragment() {

    private lateinit var contactBinding: FragmentContactBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contactBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false)
        return contactBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getData()

        contactBinding.btnOpenDetail.setOnClickListener {
            val action = ContactFragmentDirections.openDetail("AAA")
            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    private fun getData() {
        arguments?.let {
            val id = ContactFragmentArgs.fromBundle(it).id
            val name = ContactFragmentArgs.fromBundle(it).name

            val text = "$id -- $name"
            contactBinding.tvContact.text = text
        }
    }
}
