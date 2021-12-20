package com.dev_marinov.myrecipes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;


// Адаптер отвечает за извлечение данных из набора данных и за создание объектов View на основе этих данных
class Adapter_list_recept extends RecyclerView.Adapter<Adapter_list_recept.HolderListRecept> {

    Context context; // context объект, который предоставляет доступ к базовым функциям приложения
    HashMap<Integer, cl_list_recept> list; // HashMap массива всех рецептов ключ - Integer, значение - объект

    public Adapter_list_recept(Context context, HashMap<Integer, cl_list_recept> list) {
        this.context = context;
        this.list = list;
        Log.e("Adapter_list_recept-->","list.size() ="+list.size());
    }

    @NonNull
    @Override
    // создает новый объект ViewHolder всякий раз, когда RecyclerView нуждается в этом. Это тот момент, когда создаётся layout строки списка,
    // передается объекту ViewHolder, и каждый дочерний view-компонент может быть найден и сохранен
    // ViewGroup-это родительское представление RecyclerView, которое будет содержать ячейку, которую вы собираетесь создать и
    // родительский элемент используется во время процесса инфляции макета, поэтому вы можете видеть, как он передается вызову inflate
    // int viewType если у вас есть различные типы ячеек в вашем списке. Например, если у вас есть ячейка заголовка и ячейка сведений.
    // Вы можете использовать viewType, чтобы убедиться, что вы раздуваете правильный файл макета для каждого из этих двух типов ячеек
    public HolderListRecept onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // создание view с помощью класса LayoutInflater и его метода inflate. На вход layout файл, его родитель и false.
        // (Если false – то создаваемый View просто получает LayoutParams от root, но его дочерним элементом не становится.)
        // LayoutInflater обычно используется для парсинга xml не являющихся layout Activity. Например при отрисовке Fragments
        View view = LayoutInflater.from(context).inflate(R.layout.rv_list_recept, parent, false);
        HolderListRecept holderListRecept = new HolderListRecept(view);
        return holderListRecept;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderListRecept holder, int position) {
        holder.tv_recept_name.setText(list.get(position).name); // установка на каждый list элемент названия рецепта
      Log.e("picasso","download "+ "http://dev-marinov.ru" + list.get(position).img);
        Picasso.get().load("http://dev-marinov.ru" + list.get(position).img).networkPolicy(NetworkPolicy.NO_CACHE).into(holder.recept_image); // установка на каждый list элемент картинки рецепта
        holder.recept_image.invalidate();
        if (((MainActivity)context).loadSettingString(list.get(position).id_row,"0").equals("1")) //  // установка на каждый list элемент статус fav рецепта
        {
            Log.e("Adapter_list_recept--> "," (MainActivity)context).loadSettingString(list.get(position).id_row == " + list.get(position).id_row);
            Log.e("Adapter_list_recept--> ",".equals(\"1\")---list.size = " + list.size());
            Log.e("Adapter_list_recept--> ","onBindViewHolder ++избранное++ " + list.get(position).name + " и id = " + list.get(position).id_row + " и статус = " + list.get(position).fav);
            holder.recept_image_star.setBackground(context.getResources().getDrawable(R.drawable.start_on_yellow));
        }
        else
        {
            Log.e("Adapter_list_recept--> ",".equals(\"0\")---list.size = " + list.size());
            Log.e("Adapter_list_recept--> ","onBindViewHolder --не избранное-- " + list.get(position).name + " и id = " + list.get(position).id_row + " и статус = " + list.get(position).fav);
            holder.recept_image_star.setBackground(context.getResources().getDrawable(R.drawable.star_off));
        }

        Log.e("Adapter_list_recept--> ", "-----------------------ADAPTER------------------");

        for (int i = 0; i < list.size(); i++)
        {
            Log.e("Adapter_list_recept--> ","i="+i+ " - name="+ list.get(i).name  + " - fav="+list.get(i).fav);
        }

         final int sPosition = position;
        // нажатие на стрелку переход из fragment_home в fragment_edit и установка setParam
        holder.recept_image_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Adapter_list_recept--> ", "holder CLICK position" + sPosition);
                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager(); // получить доступ к фрагментам
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); // добавить фрагмента в стек
                Fragment_edit fragment_edit = new Fragment_edit();
                Log.e("Adapter_list_recept--> ","list_ingredienty = "  + list.get(sPosition).list_ingredienty.size());
                fragment_edit.setParam(list.get(sPosition).id_row, list.get(sPosition).name, list.get(sPosition).fav, list.get(sPosition).img,
                        list.get(sPosition).description, list.get(sPosition).list_ingredienty, list.get(sPosition).list_full, sPosition);

                Log.e("Adapter_list_recept--> ","list.get(position).fav =" + list.get(sPosition).fav);

                fragmentTransaction.replace(R.id.ll_frag_edit, fragment_edit);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                ((MainActivity)context).ll_frag_edit.setVisibility(View.VISIBLE);
            }
        });
    }

    // onBindViewHolder - будет вызываться столько раз, сколько данных находяться в recycleview в момент прокрутки
    // onBindViewHolder считывает данные из rv_list_recept.xml






    @Override
    public int getItemCount() { // метод получает общее кол-во рецептов в массиве list
        return list.size();
    }

    class HolderListRecept extends RecyclerView.ViewHolder { // во вложенном классе HolderListRecept

        ImageView recept_image, recept_image_star, recept_image_arrow;
        TextView tv_recept_name;
        ConstraintLayout cl_row_list_recept;
        ImageView img_star_menu_home; // звезда в fragment_home для перехода во fragment_fav

        public HolderListRecept(@NonNull View itemView) {
            super(itemView);

            recept_image = itemView.findViewById(R.id.recept_image); // главная картинка рецепта в rv_list_recept
            recept_image_star = itemView.findViewById(R.id.recept_image_star); // желтая звездочка отображения статуса рецепта в rv_list_recept
            recept_image_arrow = itemView.findViewById(R.id.recept_image_arrow); // картинка стрелки перехода в описание рецепта в rv_list_recept
            tv_recept_name = itemView.findViewById(R.id.tv_recept_name); // название рецепта в rv_list_recept
            cl_row_list_recept = itemView.findViewById(R.id.cl_row_list_recept); // контейнер ConstraintLayout для кадого рецепта в rv_list_recept
            img_star_menu_home = itemView.findViewById(R.id.img_star_menu_home); // звездочка избранное во fragment_home в меню
        }
    }


}
