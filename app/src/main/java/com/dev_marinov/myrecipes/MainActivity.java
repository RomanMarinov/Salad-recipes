package com.dev_marinov.myrecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    LinearLayout ll_frag_edit, ll_frag_fav; // два скрытых фрагмента в макете activity
    SharedPreferences sharedPreferences; // хранилище данных
  //   HashMap<Integer, cl_list_recept> list_recept = new HashMap(); // здесь з данныеаписываются и храняться все рецептов



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      //  ImageView img_view = findViewById(R.id.img_view);
        ///////////////////
       // Picasso.get().load("http://dev-marinov.ru/upload/recept/02_seld_1.jpg?877").into(img_view);
        ///////////////////////

        //Glide.with(this).load("http://dev-marinov.ru/upload/recept/02_seld_1.jpg").into(img_view);




        ll_frag_edit = findViewById(R.id.ll_frag_edit);
        ll_frag_fav = findViewById(R.id.ll_frag_fav);

// ГРАДИЕНТ СТРОКИ СОСТОЯНИЯ И ЧЕРНЫЙ БАР НАВИГАЦИИ
//// ПОКА ОТКЛЮЧИЛ
//        Window window = getWindow();
//        Drawable background = getResources().getDrawable(R.drawable.gradient);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        // FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS Флаг, указывающий, что это Окно отвечает за отрисовку фона для системных полос.
//        // Если установлено, системные панели отображаются с прозрачным фоном, а соответствующие области в этом окне заполняются цветами,
//        // указанными в Window#getStatusBarColor()и Window#getNavigationBarColor().
//        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
//        window.setNavigationBarColor(getResources().getColor(android.R.color.black));
//        window.setBackgroundDrawable(background);

        FragmentManager fragmentManager_backstack = getSupportFragmentManager(); // доступ к фрагментам
        fragmentManager_backstack.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() { // установка нажатия назад по стеку
            @Override
            public void onBackStackChanged() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                // нажатие кнопки назад в баре навигации для FRAGMENT_HOME
                Fragment_home fragment_home = (Fragment_home) fragmentManager.findFragmentById(R.id.ll_frag_home);
                if (fragment_home != null) {
                    if (fragmentManager.getBackStackEntryCount() == 0) {
                        Log.e("MainActivity --> ","fragmentManager_backstack - fragment_home!");
                        fragment_home.adapter_list_recept.notifyDataSetChanged(); // обновляет адаптер
                    }
                }
                // нажатие кнопки назад в баре навигации для FRAGMENT_FAV
                Fragment_fav  fragment_fav = (Fragment_fav) fragmentManager.findFragmentById(R.id.ll_frag_fav);
                if (fragment_fav != null) {
                    if (fragmentManager.getBackStackEntryCount() != 0) {
                        Log.e("MainActivity --> ","fragmentManager_backstack - fragment_fav!");
                        fragment_fav.adapter_list_recept.notifyDataSetChanged(); // обновляет адаптер
                    }
                }
            }
        });

        // создание класса fragmentManager c getSupportFragmentManager();
        // Он может выполнять действия с фрагментами (добавление, удаление, замена)
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // вставил фрагмент в лин ll_frag_home
        Fragment_home fragment_home = new Fragment_home();
        // замена фрагмента на fragment_home
        fragmentTransaction.replace(R.id.ll_frag_home, fragment_home); // замена на ll_frag_home
        fragmentTransaction.commitNow(); // commitNow выполнение транзакции "синхронно" т.е. последовательно
    }







    // считывает файл
    public String loadSettingString(String key,String default_value) {
        // List_contact - имя файла, MODE_MULTI_PROCESS - доступ для всех процессов
        sharedPreferences = getSharedPreferences("List_favorite", MODE_MULTI_PROCESS);
        return sharedPreferences.getString(key, default_value);
    }

    // сохраняет в файл
    public void saveSettingString(String key, String value) {
        // List_contact - имя файла куда будут сохраняться данные, MODE_MULTI_PROCESS - доступ для всех процессов
        sharedPreferences = getSharedPreferences("List_favorite", MODE_MULTI_PROCESS);
        SharedPreferences.Editor ed = sharedPreferences.edit(); // edit() - редактирование файлов
        ed.putString(key, value); // добавляем ключ и его значение

        if (ed.commit()) // сохранить файл
        {
            //успешно записано данные в файл
        }
        else
        {
            //ошибка при записи
            Toast.makeText(this, "Write error", Toast.LENGTH_SHORT).show();
        }
    }

}