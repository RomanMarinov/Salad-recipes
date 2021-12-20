package com.dev_marinov.myrecipes;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;

public class Fragment_fav extends Fragment {
    View frag;
    TextView tv_list_recept_fav_empty;// textview c сообщем "ПУСТО"
   // ConstraintLayout cl_list_recept_fav_empty; // контейнер родитель для TextView tv_list_recept_fav_empty
    ImageView img_back_frag_fav; // кнопка назад в меню
    RecyclerView rv_list_recept_fav; // контейнер для прокручивания списка

    HashMap<Integer, cl_list_recept> list_recept = new HashMap();  // HashMap для хранения всего списка рецептов
    HashMap<Integer, cl_list_recept> list_recept_fav = new HashMap(); // HashMap для хранения рецептов избранное
    Adapter_list_recept adapter_list_recept;


////////////////////////// 1
//
ConstraintLayout cl_menu_fav;
//
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
//    ////////////////////////////// 2




    // setParam для хранения данных ключ значение
    public void setParam( HashMap<Integer, cl_list_recept> list_recept) {
        this.list_recept = list_recept;
        list_recept_fav.clear(); // очистка списка избранное
    }

    public void update_list_fav() { // метод получения списка избранное

        list_recept_fav.clear(); // очистка списка избранное чтобы заполнить заново
        int count_one = -1; // счетчик для салатов избранное (1)

        for (int i = 0; i < list_recept.size(); i++) { // list_recept.size() хранит общее кол-во рецептов
            try {
                 if (((MainActivity)getActivity()).loadSettingString(list_recept.get(i).id_row,"0").equals("1")) //  // установка на каждый list элемент статус fav рецепта
                {
                    count_one++;
                    // count_one - порядковый номер, начиная с нуля, list_recept.get(i) - рецепт
                    list_recept_fav.put(count_one, list_recept.get(i));
                  }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        if (0 == list_recept_fav.size()) {
            tv_list_recept_fav_empty.setVisibility(View.VISIBLE); // показать ConstraintLayout и TextView со словом "Пусто"
            tv_list_recept_fav_empty.setText("Пусто :-(");
            rv_list_recept_fav.setVisibility(View.GONE); // закрыть RecycleView
        }
        else
        {
            tv_list_recept_fav_empty.setVisibility(View.GONE); // показать ConstraintLayout и TextView со словом "Пусто"
            tv_list_recept_fav_empty.setText("Пусто");
            rv_list_recept_fav.setVisibility(View.VISIBLE); // закрыть RecycleView
        }
}

///////////////////////// 1
//private final Runnable mHidePart2Runnable = new Runnable() {
//    @SuppressLint("InlinedApi")
//    @Override
//    public void run() {
//        // Отложенное удаление статуса и панели навигации
//        tv_list_recept_fav_empty.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
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
//            cl_menu_fav.setVisibility(View.VISIBLE);
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
        Log.e("Fragment_fav-->  "," СРАБОТАЛО onCreateView()");
        frag = inflater.inflate(R.layout.fragment_fav, container, false); // инициализация fragment_fav

        Window window = getActivity().getWindow(); // установка градиента меню
        Drawable drawable = getContext().getDrawable(R.drawable.gradient);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getActivity().getResources().getColor(android.R.color.transparent));

        window.setNavigationBarColor(getActivity().getResources().getColor(android.R.color.black));
        window.setBackgroundDrawable(drawable);






        cl_menu_fav = frag.findViewById(R.id.cl_menu_fav);



        rv_list_recept_fav = frag.findViewById(R.id.rv_list_recept_fav); // инициализация recycleview

        // для сортировки рецептов со статусом избранное (с цифрой 1)
        tv_list_recept_fav_empty = frag.findViewById(R.id.tv_list_recept_fav_empty); // textview c сообщем "ПУСТО"
        //cl_list_recept_fav_empty = frag.findViewById(R.id.cl_list_recept_fav_empty); // контейнер для TextView tv_list_recept_fav_empty

        update_list_fav();

        adapter_list_recept = new Adapter_list_recept(frag.getContext(), list_recept_fav);
        rv_list_recept_fav.setLayoutManager(new LinearLayoutManager(frag.getContext(),LinearLayoutManager.VERTICAL,false));
        rv_list_recept_fav.setAdapter(adapter_list_recept);

        // КНОПКА img_back_frag_fav возвращает во FRAGMENT_HOME
        img_back_frag_fav = frag.findViewById(R.id.img_back_frag_fav);
        img_back_frag_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


//////////////////////////////// 1
//        visible_or_gone = true;
//
//        //Настройте взаимодействие с пользователем, чтобы вручную отображать или скрывать пользовательский интерфейс системы.
//        tv_list_recept_fav_empty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggle();
//            }
//        });
//////////////////////////////// 2

        return frag;
    }

///////////////////////////// 1
//
//
//
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
//        cl_menu_fav.setVisibility(View.GONE);
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
//        tv_list_recept_fav_empty.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//        visible_or_gone = true;
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


}