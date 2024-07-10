package com.example.musicplayer.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.musicplayer.Idragon_album_layout
import com.example.musicplayer.Profile
import com.example.musicplayer.R
import com.example.musicplayer.SignUp
import com.example.musicplayer.charliePuth_album_activity
import com.example.musicplayer.coffeeZ_album_layout
import com.example.musicplayer.databinding.ActivityLogInBinding
import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.edsheeran_album_layout
import com.example.musicplayer.fragment.SearchFragment.Companion.dummy
import com.example.musicplayer.lofi_album_layout
import com.example.musicplayer.model.flag
import com.example.musicplayer.oneD_album_layout
import com.example.musicplayer.released_album_layout
import com.example.musicplayer.shawnM_album_layout
import com.example.musicplayer.weekend_album_layout
import com.google.android.material.button.MaterialButton



class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val binding1: ActivityLogInBinding by lazy {
        ActivityLogInBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            dummy = 2
        }
    private lateinit var button8: MaterialButton

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            binding = FragmentHomeBinding.inflate(inflater, container, false)
                binding.button8.setOnClickListener{
                    flag=1
                    val intent = Intent(requireContext(), lofi_album_layout::class.java)
                    startActivity(intent)
                }
            binding.button2.setOnClickListener{
                    flag=2
                    val intent = Intent(requireContext(), oneD_album_layout::class.java)
                    startActivity(intent)
                }
            binding.button3.setOnClickListener{
                    flag=3
                    val intent = Intent(requireContext(), coffeeZ_album_layout::class.java)
                    startActivity(intent)
                }
             binding.button4.setOnClickListener{
                    flag=4
                    val intent = Intent(requireContext(), edsheeran_album_layout::class.java)
                    startActivity(intent)
                }
            binding.button9.setOnClickListener{
                    flag=5
                    val intent = Intent(requireContext(), released_album_layout::class.java)
                    startActivity(intent)
                }
            binding.button7.setOnClickListener{
                    flag=6
                    val intent = Intent(requireContext(), Idragon_album_layout::class.java)
                    startActivity(intent)
                }

            //Sign Out
            binding.imageButton7.setOnClickListener{
                signoutFlag=1
                val intent = Intent(requireContext(), SignUp::class.java)
                val activity: FragmentActivity = requireActivity()
                activity.finish()
                startActivity(intent)


            }

            //Profile Button
            binding.imageButton4.setOnClickListener{
                val intent = Intent(requireContext(), Profile::class.java)
                startActivity(intent)
            }

            return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image_list = ArrayList<SlideModel>()
        image_list.add(SlideModel(R.drawable.charli_puth_banner_1, ScaleTypes.FIT))
        image_list.add(SlideModel(R.drawable.weekend_banner_3, ScaleTypes.FIT))
        image_list.add(SlideModel(R.drawable.shawn_mendes_banner_2, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(image_list)
        imageSlider.setImageList(image_list, ScaleTypes.FIT)


        imageSlider.setItemClickListener(object: ItemClickListener{
            override fun doubleClick(position: Int) {
                val itemPosition = image_list[position]
                if(position==0){
                    flag=7
                    val intent = Intent(requireContext(), charliePuth_album_activity::class.java)
                    startActivity(intent)
                }
                if(position==1){
                    flag=8
                    val intent = Intent(requireContext(), weekend_album_layout::class.java)
                    startActivity(intent)
                }
                if(position==2){
                    flag=9
                    val intent = Intent(requireContext(), shawnM_album_layout::class.java)
                    startActivity(intent)
                }
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = image_list[position]
                if(position==0){
                    flag=7
                    val intent = Intent(requireContext(), charliePuth_album_activity::class.java)
                    startActivity(intent)
                }
                if(position==1){
                    flag=8
                    val intent = Intent(requireContext(), weekend_album_layout::class.java)
                    startActivity(intent)
                }
                if(position==2){
                    flag=9
                    val intent = Intent(requireContext(), shawnM_album_layout::class.java)
                    startActivity(intent)
                }
            }
        })
    }

    companion object {
        var signoutFlag = 0
    }

}