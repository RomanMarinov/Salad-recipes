package com.dev_marinov.myrecipes;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.security.Policy;
import java.util.HashMap;

import static android.content.Context.MODE_MULTI_PROCESS;

public class Fragment_edit extends Fragment {

        ImageView img_back_frag_edit, img_star_menu_edit; // две картинки в меню (назад и добавить в избранное)
        ImageView img_big; // картинка из общего описания рецепта для циклической показа этапов приготовления
        TextView tv_name_recept; // название рецепта в меню
        TextView tv_opisanie; // описание рецепта наверху
        int position; // порядковый номер рецепта
        View frag;

        ConstraintLayout cl_top_menu_2; // верхнее меню fragment_edit
        ConstraintLayout dddddddddddd;
    ConstraintLayout cl_SUPER;

        String id_row_recept, name_recept, flag_fav, img, description; // id рецепта, название, статус, описание
        HashMap<Integer, cl_list_ingredienty> list_ingredienty; // массив меняющихся ингридиентов рецептов HashMap ключ, значение
        HashMap<Integer, cl_list_full> list_full; // массив меняющихся описаний рецептов HashMap ключ, значение

        LinearLayout ll_container_ingredient, ll_container_full; // создание двух контейнеров в макете Fragment_edit.xml

        // setParam вызыввается до создания фрагмента onCreateView
        public void setParam(String id_row_recept,
                             String name_recept,
                             String flag_fav,
                             String img,
                             String description,
                             HashMap<Integer, cl_list_ingredienty> list_ingredienty,
                             HashMap<Integer, cl_list_full> list_full, int position) {

            this.id_row_recept = id_row_recept;
            this.name_recept = name_recept;
            this.flag_fav = flag_fav;
            this.img = img;
            this.description = description;
            this.list_ingredienty = list_ingredienty;

            Log.e("Fragment_edit--> ","-----list_ingredienty.size()------" + list_ingredienty.size());
            this.list_full = list_full;
            this.position = position;
        }









//
//////////////////////////// 1
////
////
//    private static final boolean AUTO_HIDE = true; // Следует ли автоматически скрывать системный пользовательский интерфейс после
//    // {@link #AUTO_HIDE_DELAY_MILLIS} миллисекунды.
//
//    private static final int AUTO_HIDE_DELAY_MILLIS = 3000; // Если задано {@link #AUTO_HIDE}, количество миллисекунд ожидания после
//    // взаимодействие с пользователем перед тем, как скрыть пользовательский интерфейс системы.
//
//// Некоторым более старым устройствам требуется небольшая задержка между обновлениями виджетов пользовательского интерфейса.
//// и изменение статуса и панели навигации.
//    private static final int UI_ANIMATION_DELAY = 300;
//    private final Handler mHideHandler = new Handler();
//    private View tv_app;
//
//
//
//private final Runnable mHidePart2Runnable = new Runnable() {
//    @SuppressLint("InlinedApi")
//    @Override
//    public void run() {
//        // Отложенное удаление статуса и панели навигации
//        cl_SUPER.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE //
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // режим липкого погружения для игр, когда проведение пальцем не приведет с скрытию элементов экрана
//
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // скрывает панель навигации и при косании снова появляется
//                | View.SYSTEM_UI_FLAG_FULLSCREEN ); // // скрывает строку состояния и при косании снова появляется
//    }
//};
//
//    private final Runnable mShowPart2Runnable = new Runnable() {
//        @Override
//        public void run() {
//            cl_top_menu_2.setVisibility(View.VISIBLE);
//        }
//    };
//    private boolean visible_or_gone;
//    private final Runnable mHideRunnable = new Runnable() {
//        @Override
//        public void run() {
//            hide();
//        }
//    };
//
//// Сенсорный прослушиватель, используемый для элементов управления пользовательского интерфейса в макете,
//// чтобы отложить скрытие системного пользовательского интерфейса. Это сделано для предотвращения резкого
//// поведения элементов управления при взаимодействии с пользовательским интерфейсом действий.
//    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (AUTO_HIDE) {
//                delayedHide(AUTO_HIDE_DELAY_MILLIS);
//            }
//            return false;
//        }
//    };
//    /////////////////////// 2












    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // создание frag с помощью класса LayoutInflater и его метода inflate. На вход layout файл, его родитель (не Activity) и false.
        // (Если false – то создаваемый View просто получает LayoutParams от root, но его дочерним элементом не становится.)
        frag = inflater.inflate(R.layout.fragment_edit, container, false);
        cl_SUPER = frag.findViewById(R.id.cl_SUPER);
        dddddddddddd = frag.findViewById(R.id.dddddddddddd);
        // далее все элементы
        tv_name_recept = frag.findViewById(R.id.tv_name_recept);


        tv_name_recept.setText(name_recept);
        tv_opisanie = frag.findViewById(R.id.tv_opisanie);
        tv_opisanie.setText(Html.fromHtml(description)); // вывод текста из description

        img_big = frag.findViewById(R.id.img_big); // картинка из общего описания рецепта для циклической показа этапов приготовления
        cl_top_menu_2 = frag.findViewById(R.id.cl_top_menu_2); // верхнее меню fragment_edit

        ll_container_ingredient = frag.findViewById(R.id.ll_container_ingredient); // контейнер с описанием ингредиентов
        ll_container_full = frag.findViewById(R.id.ll_container_full); // контейнер с описанием приготовления

        img_star_menu_edit = frag.findViewById(R.id.img_star_menu_edit); // нажатие в меню в fragment_edit добавить/убрать избранное
        img_star_menu_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((MainActivity)getActivity()).loadSettingString(id_row_recept, "0").equals("0")) {
                    Log.e("Fragment_edit--> ","if (flag_fav.equals(\"0\"))");
                    img_star_menu_edit.setBackground(getResources().getDrawable(R.drawable.star_on));
                    flag_fav = "1"; // при нажатии установиться 1 (избранное)
                    ((MainActivity)getActivity()).saveSettingString(id_row_recept, flag_fav); // записать статус

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    Fragment_fav fragment_fav = (Fragment_fav) fragmentManager.findFragmentById(R.id.ll_frag_fav);
                    if (fragment_fav != null)
                    {
                        fragment_fav.update_list_fav();
                        fragment_fav.adapter_list_recept.notifyDataSetChanged();
                    }
                }
                else {
                    img_star_menu_edit.setBackground(getResources().getDrawable(R.drawable.star_off));
                    flag_fav = "0";
                    Log.e("Fragment_edit--> ","if (flag_fav.equals(\"1\"))");
                    ((MainActivity)getActivity()).saveSettingString(id_row_recept, flag_fav); // записать статус

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    Fragment_fav fragment_fav = (Fragment_fav) fragmentManager.findFragmentById(R.id.ll_frag_fav);
                    if (fragment_fav != null)
                    {
                        fragment_fav.update_list_fav();
                        fragment_fav.adapter_list_recept.notifyDataSetChanged();
                    }
                }

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment_home fragment_home = (Fragment_home) fragmentManager.findFragmentById(R.id.ll_frag_home);
                if (fragment_home != null) {
                   fragment_home.list_recept.get(position).fav = flag_fav;
                    Log.e("Fragment_edit-->  ","pos = " + position);
                }
            }
        });

// нажатие переход из fragment_edit во fragment_home или fragment_fav
        img_back_frag_edit = frag.findViewById(R.id.img_back_frag_edit);
        img_back_frag_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed(); // кнопка назад
            }
        });

// ГРАДИЕНТ СТРОКИ СОСТОЯНИЯ И ЧЕРНЫЙ БАР НАВИГАЦИИ
        Window window = getActivity().getWindow();
        Drawable background = getActivity().getResources().getDrawable(R.drawable.gradient);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS Флаг, указывающий, что это Окно отвечает за отрисовку фона для системных полос.
        // Если установлено, системные панели отображаются с прозрачным фоном, а соответствующие области в этом окне заполняются цветами,
        // указанными в Window#getStatusBarColor()и Window#getNavigationBarColor().
        window.setStatusBarColor(getActivity().getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getActivity().getResources().getColor(android.R.color.black));
        window.setBackgroundDrawable(background);





//
//////////////////////////////// 1
//        visible_or_gone = true;
//
//        //Настройте взаимодействие с пользователем, чтобы вручную отображать или скрывать пользовательский интерфейс системы.
//        cl_SUPER.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggle();
//            }
//        });
//////////////////////////////// 2

        return frag;
    }



//    /////////////////////////// 1
//
//    private void toggle() {
//        if (visible_or_gone) {
//            hide(); // СПРЯТАТЬ
//        } else {
//            show(); // ПОКАЗАТЬ
//
//        }
//    }
//
//    private void hide() { // МЕТОД СПРЯТАТЬ
//        cl_top_menu_2.setVisibility(View.GONE);
//        visible_or_gone = false;
//
//        //Запланируйте запуск, чтобы удалить статус и панель навигации после задержки
//        mHideHandler.removeCallbacks(mShowPart2Runnable);
//        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
//    }
//
//    @SuppressLint("InlinedApi")
//    private void show() { // МЕТОД ПОКАЗАТЬ
//        // Показать системную панель
//
////            dddddddddddd.setFitsSystemWindows(false);
//
//        Window window = getActivity().getWindow();
////        Drawable background = getActivity().getResources().getDrawable(R.drawable.gradient);
////        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
////        window.setStatusBarColor(getActivity().getResources().getColor(android.R.color.transparent));
////        window.setBackgroundDrawable(background);
//
//
////        Window window = getActivity().getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
//
//
//
//        cl_SUPER.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//        visible_or_gone = true;
//
//
//
//
//        // Запланировать запуск для отображения элементов пользовательского интерфейса после задержки
//        mHideHandler.removeCallbacks(mHidePart2Runnable);
//        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
//    }
//
//    // Планирует вызов функции hide () с задержкой в ​​миллисекундах, отменяя все ранее запланированные вызовы.
//    private void delayedHide(int delayMillis) {
//        mHideHandler.removeCallbacks(mHideRunnable);
//        mHideHandler.postDelayed(mHideRunnable, delayMillis);
//    }
//
///////////////////////////// 2
    @Override
    public void onStart() {
        super.onStart();
        UIinit();
    }

    // заполнение контента, полного описания рецепта
    public void UIinit() { // для вращения телеыона
        Log.e("UIinit","-UIinit-вызывается");
        if (((MainActivity)getActivity()).loadSettingString(id_row_recept,"0").equals("1")) //  // установка на каждый list элемент статус fav рецепта
            {
            img_star_menu_edit.setBackground(getResources().getDrawable(R.drawable.star_on));
            }
        else {
            img_star_menu_edit.setBackground(getResources().getDrawable(R.drawable.star_off));
        }

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        // networkPolicy(NetworkPolicy.NO_STORE) - не кеширует на устройстве
                                                                                // было height 300
        Picasso.get().load("http://dev-marinov.ru" + img).networkPolicy(NetworkPolicy.NO_STORE).resize(width, 0).centerCrop(Gravity.TOP).into(img_big);

        Log.e("img","-img-" + img);
        Log.e("Fragment_edit-->  ","list_ingredinl.size();" + list_ingredienty.size());

                ll_container_ingredient.removeAllViews(); // удлаить все детеей
                for (int i = 0; i < list_ingredienty.size(); i++ ) {

                String name = list_ingredienty.get(i).name;
                String value = list_ingredienty.get(i).value;

                Log.e("Fragment_edit-->  ","" + name);

                ConstraintLayout constraintLayout = new ConstraintLayout(frag.getContext());
                // new LinearLayout.LayoutParams() потому что родитель ll_container_ingredient - линейнный лайаут
                constraintLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                TextView textView = new TextView(frag.getContext());
                // new ConstraintLayout.LayoutParams потому что родитель constraintLayout - констрейнтр лайаут
                ConstraintLayout.LayoutParams param  = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                param.topToTop = 0;
                param.leftToLeft = 0;
                textView.setPadding(0,5,0,0);
                textView.setLayoutParams(param);
                textView.setId(1000 + i);
                textView.setText(name);

                // textView2 для заполнения текста описания приготовления в ll_container_ingredient
                TextView textView2 = new TextView(frag.getContext());
                // new ConstraintLayout.LayoutParams потому что родитель constraintLayout - констрейнтр лайаут
                ConstraintLayout.LayoutParams param2  = new ConstraintLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                param2.topToTop = 0;
                param2.leftToRight = 1000 + i;
                param2.rightToRight = 0;
                textView2.setPadding(0,5,0,0);
                textView2.setLayoutParams(param2);
                textView2.setText(value);
                textView2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                constraintLayout.addView(textView); // констрейнтр лайаут добавил textview
                constraintLayout.addView(textView2); // констрейнтр лайаут добавил textview

                ll_container_ingredient.addView(constraintLayout);
            }

            ll_container_full.removeAllViews();

        for (int i = 0; i < list_full.size(); i++ ) {
            String img = list_full.get(i).img;
            String value = list_full.get(i).value;
            // new_img для заполнения картинок в ll_container_full
            ImageView new_img = new ImageView(frag.getContext());
            new_img.setPadding(16,20,16,14);

            // надутый newTextView, в созданном newtextview.xml (чтобы применить стить программно для newtextview)
            TextView newTextView = (TextView)getLayoutInflater().inflate(R.layout.newtextview_style, null);
            newTextView.setPadding(16,0,16,16);
            newTextView.setText(Html.fromHtml(value));

            ll_container_full.addView(new_img);

            Picasso.get().load("http://dev-marinov.ru"+img).resize(width,0).centerCrop(Gravity.TOP).into(new_img);

            ll_container_full.addView(newTextView);
        }

    }

}