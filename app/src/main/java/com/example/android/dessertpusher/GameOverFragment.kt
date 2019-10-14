package com.example.android.dessertpusher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.dessertpusher.databinding.FragmentGameoverBinding

class GameOverFragment : Fragment() {
    private lateinit var binding: FragmentGameoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gameover, container, false)

        val timesClicked = GameOverFragmentArgs.fromBundle(arguments!!).timesClicked
        binding.lblGameOverTimesClicked.text = timesClicked.toString()

        val secondsPassed = timesClicked / GameOverFragmentArgs.fromBundle(arguments!!).secondsPassed
        binding.lblGameOverClicksPerSecond.text = (timesClicked.toFloat() / secondsPassed).toString()

        return binding.root
    }

}