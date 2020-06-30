package com.baturin.goldenmonkey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.a_m.*

class MA : AppCompatActivity() {
    private var pic: ArrayList<ArrayList<Pix>> = Picture.pic
    private lateinit var cA: CA

    private var  sel = -1
    private var f = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_m)
        cA = CA(pic)
        field.adapter = cA
        zero.setOnClickListener { sel = 0 }
        one.setOnClickListener { sel = 1 }
        two.setOnClickListener { sel = 2 }
        three.setOnClickListener { sel = 3 }
        four.setOnClickListener { sel = 4 }
        five.setOnClickListener { sel = 5 }
        six.setOnClickListener { sel = 6 }
        clear.setOnClickListener { sel = 7 }
        field.setOnItemClickListener { _, _, i, _ ->
            if (sel==-1){
                Toast.makeText(this, "Вы не выбрали цвет", Toast.LENGTH_SHORT).show()
            }
            else {
                cC(i/20, i%20)
            }
        }
        btn_check.setOnClickListener {
            if (field.isEnabled){
                for (i in 0..19){
                    for (j in 0..19){
                        if (Picture.pic[i][j].setColor != Picture.pic[i][j].color){
                            Toast.makeText(this, "Неверно", Toast.LENGTH_SHORT).show()
                            f = false
                            break
                        }
                    }
                    if (!f) break
                }
                if (f){
                    showWin()
                }
                f = true
            }
            else {
                hideWin()
                clearField()
            }
        }
        cA.updA()
    }

    private fun clearField(){
        for (i in 0..19)
            for (j in 0..19){
                pic[i][j].setColor = -1
                pic[i][j].pressed = false
            }
        cA.updA()
    }

    private fun cC(i: Int, j: Int){
        if (sel==7){
            pic[i][j].pressed = false
            pic[i][j].setColor = -1
        } else {
            pic[i][j].pressed = true
            //Log.e("qwerty", "$sel")
            pic[i][j].setColor = sel
        }
        cA.updA()
    }

    private fun showWin(){
        field.isEnabled =false
        colors.isEnabled = false
        yw.visibility = View.VISIBLE
        btn_check.text = "Заново"
    }

    private fun hideWin(){
        yw.visibility = View.GONE
        field.isEnabled =true
        colors.isEnabled = true
        btn_check.text = "Проверить"
    }
}