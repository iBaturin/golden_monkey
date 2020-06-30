package com.baturin.goldenmonkey

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.pixel.view.*

class CA (_cList: ArrayList<ArrayList<Pix>>): BaseAdapter() {

    private val colors = arrayListOf(
        R.drawable.str0,
        R.drawable.str1,
        R.drawable.str2,
        R.drawable.str3,
        R.drawable.str4,
        R.drawable.str5,
        R.drawable.str6
    )

    private var cList = _cList
    override fun getCount(): Int =  cList.size*cList.size
    override fun getItem(p0: Int): Any = cList[p0]
    override fun getItemId(p0: Int): Long = p0.toLong()
    fun updA(){notifyDataSetChanged()}

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val pix = this.cList[p0 / 20][p0 % 20]
        val inf = p2?.context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inf.inflate(R.layout.pixel, null)
        val digit = v.findViewById<TextView>(R.id.digit)
        if (!pix.pressed){
            v.pixel.setBackgroundResource(R.drawable.str0)
            digit.text = pix.color.toString()
        } else {
            v.pixel.setBackgroundResource(colors[pix.setColor])
            digit.text = ""
        }
        return v
    }

}