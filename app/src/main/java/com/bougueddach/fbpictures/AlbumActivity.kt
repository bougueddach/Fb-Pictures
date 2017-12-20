package com.bougueddach.fbpictures

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView

class AlbumActivity : AppCompatActivity() {
    var bundle:Bundle=intent.extras
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        //fillPicturesGrid()
    }

    fun fillPicturesGrid() {

        var picturesGrid: GridView = findViewById<GridView>(R.id.picturesGridView)

        if (bundle!=null){

        }
        //var pircturesGR:PicturesGridResources=PicturesGridResources()
        //picturesGrid.adapter=pircturesGR
    }


 inner class PicturesGridResources : BaseAdapter() {

     override fun getItem(position: Int): Any {
         TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
         //return itemsList!![position]
     }

     override fun getItemId(position: Int): Long {
         //return position.toLong()
         TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
     }

     override fun getCount(): Int {
         //return itemsList!!.size
         TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
     }

     override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

         TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

         /*var view = layoutInflater.infle(R.layout.single_pictures_item, null)
         var image=view.findViewById<ImageView>(R.id.imageView_singlePicturesItem)
         return view */

     }

 }

}
