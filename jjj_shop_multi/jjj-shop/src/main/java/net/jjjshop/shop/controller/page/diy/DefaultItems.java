package net.jjjshop.shop.controller.page.diy;

import com.alibaba.fastjson.JSONObject;
import net.jjjshop.shop.controller.page.diy.items.*;

public class DefaultItems implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    public static JSONObject getDefaultItems(String imagePath){
        JSONObject items = new JSONObject();
        items.put("banner", new Banner().getItem());
        items.put("imageSingle", new ImageSingle().getItem());
        items.put("navBar", new NavBar(imagePath).getItem());
        items.put("blank", new Blank().getItem());
        items.put("guide", new Guide().getItem());
        items.put("video", new Video(imagePath).getItem());
        items.put("article", new Article(imagePath).getItem());
        items.put("special", new Special(imagePath).getItem());
        items.put("notice", new Notice(imagePath).getItem());
        items.put("richText", new RichText().getItem());
        items.put("window", new Window(imagePath).getItem());
        items.put("product", new Product(imagePath).getItem());
        items.put("store", new Store(imagePath).getItem());
        items.put("service", new Service(imagePath).getItem());
        items.put("title", new Title().getItem());
        return items;
    }
}
