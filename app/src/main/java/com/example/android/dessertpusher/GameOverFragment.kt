package com.example.android.dessertpusher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.dessertpusher.databinding.FragmentGameoverBinding

class GameOverFragment : Fragment() {
    private lateinit var binding: FragmentGameoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // We can't use the normal pop behaviour when pressing the back button because this brings us
        // to a [DessertPusherFragment] that's already been "used".
        // Instead we overwrite the back button and introduce a kind of circular navigation where
        // you continuously go from fragment A to B to A to ... as described here:
        // https://developer.android.com/guide/navigation/navigation-custom-back#implement_custom_back_navigation
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(GameOverFragmentDirections.actionGameOverFragmentToDessertPusherFragment())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gameover, container, false)

        val timesClicked = GameOverFragmentArgs.fromBundle(arguments!!).timesClicked
        binding.lblGameOverTimesClicked.text = timesClicked.toString()

        val secondsPassed = GameOverFragmentArgs.fromBundle(arguments!!).secondsPassed
        val clicksPerSecond = timesClicked.toFloat() / secondsPassed
        binding.lblGameOverClicksPerSecond.text = clicksPerSecond.toString()

        return binding.root
    }

}