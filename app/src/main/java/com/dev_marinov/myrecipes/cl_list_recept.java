package com.dev_marinov.myrecipes;

import java.util.HashMap;

class cl_list_recept { // класс для хранения данных

    String id_row, name, img, fav, description;
    HashMap<Integer, cl_list_ingredienty> list_ingredienty= new HashMap();
    HashMap<Integer, cl_list_full> list_full = new HashMap();

    public cl_list_recept(String id_row,
                          String name,
                          String img,
                          String fav,
                          String description,
                          HashMap<Integer, cl_list_ingredienty> list_ingredienty,
                          HashMap<Integer, cl_list_full> list_full) {
        this.id_row = id_row;
        this.name = name;
        this.img = img;
        this.fav = fav;
        this.description = description;
        this.list_ingredienty = list_ingredienty;
        this.list_full = list_full;
    }
}
