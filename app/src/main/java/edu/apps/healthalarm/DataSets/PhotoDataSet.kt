@file:Suppress("PackageName")

package edu.apps.healthalarm.DataSets

import com.example.healthalarm.R
import edu.apps.healthalarm.Models.ViewpagerModel
import java.util.*

object PhotoDataSet {
    @JvmStatic
    val photos: List<ViewpagerModel>
        get() {
            val photos: MutableList<ViewpagerModel>
            photos = ArrayList()

            photos.add(ViewpagerModel(R.drawable.fresh))
            photos.add(ViewpagerModel(R.drawable.breathe))
            photos.add(ViewpagerModel(R.drawable.live))
            photos.add(ViewpagerModel(R.drawable.leg))
            photos.add(ViewpagerModel(R.drawable.relax))
            photos.add(ViewpagerModel(R.drawable.headace))
            photos.add(ViewpagerModel(R.drawable.taketime))

            return photos
        }
}