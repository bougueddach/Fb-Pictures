package com.bougueddach.fbpictures

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bougueddach.fbpictures.model.Album
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var loginButton: LoginButton? = null
    var callbackManager: CallbackManager? = null
    var albums: ArrayList<Album>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FacebookSdk.sdkInitialize(applicationContext)
        setContentView(R.layout.activity_main)
        connectToFB()
    }

    fun connectToFB() {
        callbackManager = CallbackManager.Factory.create()
        loginButton = findViewById<LoginButton>(R.id.login_button)
        loginButton!!.setReadPermissions("user_photos")
        loginButton!!.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Toast.makeText(applicationContext, "Logging in ... ", Toast.LENGTH_SHORT).show()
                connectionStatus.text = "Login successfully to : ${loginResult.accessToken.userId}  "

                var graphRequest = GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/${loginResult.accessToken.userId}/albums",
                        null,
                        HttpMethod.GET,
                        object : GraphRequest.Callback {
                            override fun onCompleted(response: GraphResponse?) {
                                if (response != null) {
                                    val JSONOBJECT: JSONObject = response.jsonObject
                                    try {
                                        val albumsArray: JSONArray = JSONOBJECT.getJSONArray("data")
                                        val mapper: ObjectMapper = ObjectMapper()
                                        albums = mapper.readValue(albumsArray.toString(), object : TypeReference<ArrayList<Album>>() {})
                                        Toast.makeText(applicationContext, "albums retreived", Toast.LENGTH_SHORT).show()
                                    } catch (e: JSONException) {
                                        e.printStackTrace()
                                        Toast.makeText(applicationContext, "couldnt get albums", Toast.LENGTH_SHORT).show()
                                    }
                                    fillAlbumsGrid()
                                }
                            }
                        }).executeAsync()
                connectionStatus.text = "Here is you're albums list: "
            }

            override fun onCancel() {
                connectionStatus.text = "login canceled"
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
    }

    fun fillAlbumsGrid() {
        var AlbumsGrid: GridView = findViewById(R.id.albumsGridView)
        var albumsGR: AlbumsGridResources = AlbumsGridResources(albums)
        AlbumsGrid.adapter = albumsGR
        AlbumsGrid.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var intent: Intent = Intent(this@MainActivity, AlbumActivity::class.java)
                intent.putExtra("Albums pictures", albums!![albumsGridView.getItemIdAtPosition(position).toInt()].images)
                startActivity(intent)
            }
        }
    }

    inner class AlbumsGridResources(var itemsList: List<Album>? = albums) : BaseAdapter() {

        override fun getItem(position: Int): String? {
            return itemsList!![position].name
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return itemsList!!.size
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var inflater: LayoutInflater = layoutInflater
            var view = layoutInflater.inflate(R.layout.single_album_item, null)
            var titleView = view.findViewById<TextView>(R.id.titleView_singleAlbumItem)
            var dateView = view.findViewById<TextView>(R.id.dateView_singleAlbumItem)

            titleView.text = "Title : " + itemsList!![position].name.toString()
            dateView.text = "created on : " + itemsList!![position].created_time.toString()
            return view
        }

    }
}
