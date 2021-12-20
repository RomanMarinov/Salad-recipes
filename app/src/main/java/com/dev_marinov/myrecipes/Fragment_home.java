package com.dev_marinov.myrecipes;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class Fragment_home extends Fragment {

    View frag;
    HashMap<Integer, cl_list_recept> list_recept = new HashMap(); // здесь записываются и храняться все данные рецептов
    RecyclerView rv_list_recept;
    Adapter_list_recept adapter_list_recept;
    ImageView img_star_menu_home;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        frag = inflater.inflate(R.layout.fragment_home, container, false);
        rv_list_recept = frag.findViewById(R.id.rv_list_recept);

        // добавляет растояние между items в rv_list_recept
        rv_list_recept.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        adapter_list_recept = new Adapter_list_recept(getContext(), list_recept); //
        rv_list_recept.setLayoutManager(new LinearLayoutManager(frag.getContext(),LinearLayoutManager.VERTICAL,false));
        rv_list_recept.setAdapter(adapter_list_recept);

        getListRecept();

        img_star_menu_home = frag.findViewById(R.id.img_star_menu_home);




        // ГРАДИЕНТ СТРОКИ СОСТОЯНИЯ И ЧЕРНЫЙ БАР НАВИГАЦИИ

        Window window = getActivity().getWindow();
        Drawable background = getResources().getDrawable(R.drawable.gradient);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS Флаг, указывающий, что это Окно отвечает за отрисовку фона для системных полос.
        // Если установлено, системные панели отображаются с прозрачным фоном, а соответствующие области в этом окне заполняются цветами,
        // указанными в Window#getStatusBarColor()и Window#getNavigationBarColor().
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.black));
        window.setBackgroundDrawable(background);

        return frag;
    }

    public void getListRecept() {
        Log.e("FRAG_HOME","-getListRecept-");
        // AsyncHttpClient (AHC) - это библиотека, построенная на основе Netty ,
        // с целью простого выполнения HTTP-запросов и асинхронной обработки ответов.
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(0); // время ожидания, и через которое система отменит соединение.
        asyncHttpClient.setMaxRetriesAndTimeout(0,0); // устанавливает максимальное количество повторных попыток и тайм-аут между этими попытками
        asyncHttpClient.setResponseTimeout(0); // устновить время ожидания
        asyncHttpClient.setMaxConnections(1000); // Определяет максимальное число одновременных подключений к серверу БД
        asyncHttpClient.get("http://dev-marinov.ru/recept/list_recept.php", new JsonHttpResponseHandler() {
            @Override
            // onSuccess() удачное получение ответа на запрос
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
//                Array
//                        (
//                        [1] => Array
//                        (
//                        [id_row] => 1
//                        [name] => Салат Мимоза
//            [img] => /upload/recept/01_mimoza_1.jpg
//                        [description] => Рецепт салат мимоза, главными ингредиентами в котором служат консервы из рыбы, куриные яйца лук и заправка из майонеза. Название блюда полностью отражает его вид - салат изображает в себе цветы мимозы на снежной поверхности.
//            [ingredienty] => Array
//                        (
//                        [Рыбные консервы в масле] => 200 гр
//                        [Картофель] => 300 гр
//                        [Морковь] => 200 гр
//                        [Лук] => 100 гр
//                        [Сыр] => 150 гр
//                        [Майонез] => 250 гр
//                        [Яйца] => 4 шт
//                        [Зелень] => 50 гр
//                )
//
//            [recept_full] => Array
//                        (
//                        [/upload/recept/full/01/01_mimoza_2.jpg] => 1. Первым делом нужно отварить картофель, морковь и яйца. Отваренные продукты остудить и почистить. На мелкой тёрке натереть картошку, морковку, белки, желтки и сыр (всё в отдельных мисочках или тарелках). В салатницу выложить рыбу с маслом и размять вилкой по всей поверхности дна посуды. Сверху смазать небольшим количеством майонеза.
//                    [/upload/recept/full/01/01_mimoza_3.jpg] => 2. На рыбу сверху разложить натёртые белки, смазать майонезом.
//                    [/upload/recept/full/01/01_mimoza_5.jpg] => 3. Лук мелко нарезать, залить кипятком и дать настояться в течении 5-10 минут для удаления горечи. Сверху на морковь положить лук, на лук - картофель, немножко посолить и смазать майонезом.
//                    [/upload/recept/full/01/01_mimoza_4.jpg] => 4. Сверху на белки выложить натёртую морковь и опять смазать небольшим слоем майонеза.
//                    [/upload/recept/full/01/01_mimoza_6.jpg] => 5. Сверху мелко натереть сыр и смазать майонезом.
//                    [/upload/recept/full/01/01_mimoza_7.jpg] => 6. Последний слой - украшение салата натертыми желтками и зеленью. Перед подачей на стол дать настояться и пропитаться в холодном месте в течение 2 часов. Приятного аппетита!
//                )
//
//        )
//
//    [2] => Array
//                        (
//                        [id_row] => 2
//                        [name] => Салат Селедка под шубой
//            [img] => /upload/recept/02_seld_1.jpg
//                        [description] => Признанный набор ингредиентов для селедки под шубой, состоит из соленой сельди, отварных яиц и отварных же овощей — морковь, картошка, свекла. ... Филе сельди отделяют от костей и мелко нарезают. Овощи и яйца также нарезают или натирают, а картошку иногда и в пюре измельчают
//            [ingredienty] => Array
//                        (
//                        [Сельдь] => 1 шт
//                        [Свекла крупная] => 1 шт
//                        [Морковь] => 5 шт
//                        [Картофель] => 6 шт
//                        [Лук репчатый] => 1 шт
//                        [Лук зеленый] => 1 пучок
//                        [Майонез ] => по вкусу
//                )
//
//            [recept_full] => Array
//                        (
//                        [/upload/recept/full/02/02_seld_2.jpg] => 1. Подготовьте продукты для салата. Отварите все овощи (кроме лука), остудите их и очистите. Сельдь разделайте на филе, удалите максимально косточки, кожу, хребет, порежьте кубиками небольшого размера.
//                    [/upload/recept/full/02/02_seld_3.jpg] => 2. Собираем салат. Возьмите удобное большое блюдо. Первым слоем выложите тертый картофель, сделайте майонезную сеточку.
//                    [/upload/recept/full/02/02_seld_4.jpg] => 3. На картофель уложите слой порезанной сельди.
//                    [/upload/recept/full/02/02_seld_5.jpg] => 4. На сельдь сверху распределите мелко порезанный репчатый и зеленый лук.
//                    [/upload/recept/full/02/02_seld_6.jpg] => 5. Далее — морковный слой и майонезная сеточка. Соль и перец используйте по желанию.
//                    [/upload/recept/full/02/02_seld_7.jpg] => 6. Последний слой - свекла. Украсьте майонезной сеточкой салат.
//                    [/upload/recept/full/02/02_seld_8.jpg] => 7. Оформите салат зеленым луком, розочками из моркови. Настаивайте салат не менее 15 минут в холодильнике. Приятного аппетита!
//                )
//
//        )
//
//
//
//)
                list_recept.clear(); // очистить список

                Iterator<String> keys = response.keys();
                Log.e("FRAG_HOME"," ==== " + keys);
                int i = -1;
                // С помощью метода hasNext() можно узнать, есть ли следующий элемент, и не достигнут ли конец коллекции.
                // И если элементы еще имеются, то hasNext() вернет значение true. Метод hasNext() следует вызывать перед методом next(),
                // так как при достижении конца коллекции метод next() выбрасывает исключение NoSuchElementException.
                 while (keys.hasNext()) {
                    HashMap<Integer, cl_list_ingredienty> list_ingredinety = new HashMap();
                    HashMap<Integer, cl_list_full> list_full = new HashMap();
                    list_ingredinety.clear(); // очистка массива для чтобы не дублировались данные для адаптера
                    list_full.clear(); // очистка массива для чтобы не дублировались данные для адаптера
                    i++;
                    // next() можно получить следующий элемент
                    String key = keys.next(); // в key - передается общее кол-во рецептов
                    Log.e("FRAG_HOME"," ==внутри== " + key);
                    try { // передача данных в переменные
                        // получить и записать в String id_row по очереди общее кол-во рецептов из базыданных по ключу "id_row"
                        String id_row = response.getJSONObject(key).getString("id_row");
                        // загрузить по умолчанию в настройки статуса избранное "0" для каждого рецепта
                        String flag_fav = ((MainActivity)getActivity()).loadSettingString(id_row, "0");
                        Log.e("id_row_flag_fav","///////////" + "id_row =" + id_row+ "flag_fav =" + flag_fav);
                        // получить и записать в String name по очереди общее кол-во названий рецептов из базыданных по ключу "name"
                        String name = response.getJSONObject(key).getString("name");
                        Log.e("String_name"," --- " + name);
                        // получить и записать в String img (главной картинки) по очереди общее кол-во картинок рецептов из базыданных по ключу "img"
                        String img = response.getJSONObject(key).getString("img");
                        Log.e("String_img"," --- " + img);
                        // получить и записать в String description по очереди общее кол-во описаний рецептов из базыданных по ключу "description"
                        String description = response.getJSONObject(key).getString("description");
                        Log.e("String_description"," --- " + description);

                        try {
                        // получить и записать в JSONObject ingredienty
                        JSONObject ingredienty = response.getJSONObject(key).getJSONObject("ingredienty");

                        Iterator<String> keys_ingredienty = ingredienty.keys();
                        int i_2 = -1;
                        while (keys_ingredienty.hasNext()) {
                            i_2 ++;
                            String name_ingredient = keys_ingredienty.next(); // здесь читается и записывается название ингдиента из базы
                            String value_ingredient = ingredienty.getString(name_ingredient); // здесь читается и записывается кол-во ингдиента из базы
                            Log.e("name_ingredient","" + name_ingredient);
                            Log.e("value_ingredient","" + value_ingredient);
                            list_ingredinety.put(i_2, new cl_list_ingredienty(name_ingredient, value_ingredient));
                                }
                            }
                        catch (Exception e) {
                                Log.e("EXEPTION ingredienty","-ingredienty-" + e);
                            }

                        try {
                            // получение json данных по ключу recept_full и запись в файл
                        JSONObject recept_full = response.getJSONObject(key).getJSONObject("recept_full");
                        // конвертация ключей типа json в string
                        Iterator<String> keys_full = recept_full.keys();
                        int i_3 = -1;
                        while (keys_full.hasNext()) {
                            i_3++;
                            String img_full = keys_full.next();  // здесь читается и записывается картинки рецепта из базы
                            String value_full = recept_full.getString(img_full);   // здесь читается и записывается описания картинок рецепта из базы
                            Log.e("img_full","" + img_full);
                            Log.e("value_full","" + value_full);
                            list_full.put(i_3, new cl_list_full(img_full, value_full));
                                }
                            }
                        catch (Exception e) {
                            Log.e("EXEPTION recept_full","-recept_full-" + e);
                            }

                        Log.e("img",""+img);
                        Log.e("list_ingredinety",""+list_ingredinety.size());

                        // записываю все полученные данные в HashMap<Integer, cl_list_recept> list_recept
                        list_recept.put(i, new cl_list_recept(id_row, name, img, flag_fav, description, list_ingredinety, list_full));
                        }

                    catch (JSONException e) {
                        Log.e("EXEPTION main","-main-" + e);
                        }
                }

                adapter_list_recept.notifyDataSetChanged(); // обновляет адаптер

                // setTag позволяет получить данные типа object
                img_star_menu_home.setTag(list_recept);
                // нажатие на звездочку в меню fragment_home избранное и ПЕРЕХОД ВО FRAGMENT_FAV
                img_star_menu_home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // HashMap<Integer, cl_list_recept> list - это копия HashMap<Integer, cl_list_recept> list_recept
                        // getTag - позволяет вернуть данные типа object
                        HashMap<Integer, cl_list_recept> list = (HashMap<Integer, cl_list_recept>) view.getTag();

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();  // получить доступ к фрагментам
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); // добавить фрагмент в стек
                        Fragment_fav fragment_fav = new Fragment_fav(); // создание экземпляра фрагмента в который нужен переход
                        fragment_fav.setParam(list); // установка параметров li????????????????????????????
                        Log.e("Fragment_home-->"," переход во fragment_fav = ONCLICK size = "+list.size());
                        fragmentTransaction.replace(R.id.ll_frag_fav, fragment_fav); // замена fragment_home на fragment_fav
                        // addToBackStack(null) транзакция замены сохраняется в заднем стеке, поэтому пользователь может отменить транзакцию и
                        // вернуть предыдущий фрагмент, нажав кнопку «Назад».
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit(); // асихнронный метод фиксации транзакции

                        ((MainActivity)getActivity()).ll_frag_fav.setVisibility(View.VISIBLE); // сделать ll_frag_fav видимым
                    }
                });

            }

            @Override
            // onFailure() неудачное получение ответа на запрос
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("log","onFailure = "+responseString);
            }

            @Override
            // onFailure() неудачное получение ответа на запрос
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("log","onFailure (errorResponse) = "+errorResponse);

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                getListRecept();
                            }
                        });
                        cancel();
                    }
                },3000,3000);
            }
        });

    }

}