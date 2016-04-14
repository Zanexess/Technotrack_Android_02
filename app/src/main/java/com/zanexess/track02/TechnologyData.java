package com.zanexess.track02;

public class TechnologyData {
   public static class Image {
      private String id;
      private String url_picture;
      private String title;
      private String info;

      public Image(String url_picture) {
         this.url_picture = url_picture;
      }

      public Image(String id, String url_picture, String title, String info) {
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
}
