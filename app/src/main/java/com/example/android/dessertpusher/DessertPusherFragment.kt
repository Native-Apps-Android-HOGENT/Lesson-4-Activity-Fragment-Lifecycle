package com.example.android.dessertpusher

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.dessertpusher.databinding.FragmentDessertpusherBinding
import com.example.android.dessertpusher.domain.DessertShop


/** onSaveInstanceState Bundle Keys **/
private const val KEY_DESSERTSHOP = "shop_key"

class DessertPusherFragment : Fragment() {

    private lateinit var dessertTimer: DessertTimer

    // Contains all the views
    private lateinit var binding: FragmentDessertpusherBinding

    private lateinit var dessertShop: DessertShop

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup dessertTimer, passing in the lifecycle
        dessertTimer = DessertTimer(this.lifecycle)

        // If there is a savedInstanceState bundle, then you're "restarting" the activity
        // If there isn't a bundle, then it's a "fresh" start
        if (savedInstanceState != null) {
            // Get all the game state information from the bundle, set it
            dessertShop = savedInstanceState.getParcelable(KEY_DESSERTSHOP)!!
        } else {
            dessertShop = DessertShop()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Use Data Binding to get reference to the views
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dessertpusher, container, false)

        binding.dessertButton.setOnClickListener {
            onDessertClicked()
        }

        // Set the TextViews to the right values
        binding.dessertShop = dessertShop

        return binding.root
    }

    private fun updateUI() {
        binding.invalidateAll()
    }

    /**
     * Updates the score when the dessert is clicked. Possibly shows a new dessert.
     */
    private fun onDessertClicked() {
        dessertShop.onDessertSold()
        updateUI()
    }


    /**
     * Menu methods
     */
    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
                .setText(getString(R.string.share_text, dessertShop.dessertsSold, dessertShop.revenue))
                .setType("text/plain")
                .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(requireContext(), getString(R.string.sharing_not_available),
                    Toast.LENGTH_LONG).show()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Called when the user navigates away from the app but might come back
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(KEY_DESSERTSHOP, dessertShop)
        super.onSaveInstanceState(outState)
    }
}