package com.zanexess.track02;

import java.util.List;

public class TechnologyData {
   public static class Technology {
      private String id;
      private String url_picture;
      private String title;
      private String info;

      public Technology(String id, String url_picture, String title, String info) {
         this.id = id;
         this.url_picture = url_picture;
         this.title = title;
         this.info = info;
      }

      public String getUrl_picture() {
         return url_picture;
      }

      public String getTitle() {
         return title;
      }

      public String getInfo() {
         return info;
      }

      public String getId() {
         return id;
      }
   }

   private List<Technology> list;

   private static TechnologyData _instance;
   protected TechnologyData() {}

   public static void createInstance(List<Technology> initList) {
      if (null == _instance) {
         _instance = new TechnologyData();
         _instance.init(initList);
      }
   }

   static public TechnologyData instance() {
      return _instance;
   }

   public List<Technology> getImages() {
      return list;
   }

   public Technology getImage(int pos) {
      return list.get(pos);
   }

   private void init(List<Technology> initList) {
      this.list = initList;
   }
}
