package com.yangshuai.module.video.bean;

import java.util.List;

public class PlatformlVideoBean {

     private List<RecordsBean> records;
     private Integer total;
     private Integer size;
     private Integer current;
     private List<?> orders;
     private Boolean optimizeCountSql;
     private Boolean hitCount;
     private Boolean searchCount;
     private Integer pages;

     public List<RecordsBean> getRecords() {
          return records;
     }

     public void setRecords(List<RecordsBean> records) {
          this.records = records;
     }

     public Integer getTotal() {
          return total;
     }

     public void setTotal(Integer total) {
          this.total = total;
     }

     public Integer getSize() {
          return size;
     }

     public void setSize(Integer size) {
          this.size = size;
     }

     public Integer getCurrent() {
          return current;
     }

     public void setCurrent(Integer current) {
          this.current = current;
     }

     public List<?> getOrders() {
          return orders;
     }

     public void setOrders(List<?> orders) {
          this.orders = orders;
     }

     public Boolean getOptimizeCountSql() {
          return optimizeCountSql;
     }

     public void setOptimizeCountSql(Boolean optimizeCountSql) {
          this.optimizeCountSql = optimizeCountSql;
     }

     public Boolean getHitCount() {
          return hitCount;
     }

     public void setHitCount(Boolean hitCount) {
          this.hitCount = hitCount;
     }

     public Boolean getSearchCount() {
          return searchCount;
     }

     public void setSearchCount(Boolean searchCount) {
          this.searchCount = searchCount;
     }

     public Integer getPages() {
          return pages;
     }

     public void setPages(Integer pages) {
          this.pages = pages;
     }

     public static class RecordsBean {
          private String id;
          private String title;
          private String articleSource;
          private String pageRecommend;
          private String merchName;
          private String authorTime;
          private String cover;
          private String imgId;
          private String imgIds;
          private String fileId;
          private String fileIds;
          private String serviceType;
          private String publishType;
          private String createTime;
          private String nickName;
          private String avatarUrl;
          private String videoUrl;
          private String articleType;
          public RecordsBean(){

          }
          public RecordsBean(String id, String title, String articleSource, String pageRecommend, String merchName, String authorTime, String cover, String imgId, String imgIds, String fileId, String fileIds, String serviceType, String publishType, String createTime, String nickName, String avatarUrl, String videoUrl, String articleType) {
               this.id = id;
               this.title = title;
               this.articleSource = articleSource;
               this.pageRecommend = pageRecommend;
               this.merchName = merchName;
               this.authorTime = authorTime;
               this.cover = cover;
               this.imgId = imgId;
               this.imgIds = imgIds;
               this.fileId = fileId;
               this.fileIds = fileIds;
               this.serviceType = serviceType;
               this.publishType = publishType;
               this.createTime = createTime;
               this.nickName = nickName;
               this.avatarUrl = avatarUrl;
               this.videoUrl = videoUrl;
               this.articleType = articleType;
          }

          public String getId() {
               return id;
          }

          public void setId(String id) {
               this.id = id;
          }

          public String getTitle() {
               return title;
          }

          public void setTitle(String title) {
               this.title = title;
          }

          public String getArticleSource() {
               return articleSource;
          }

          public void setArticleSource(String articleSource) {
               this.articleSource = articleSource;
          }

          public String getPageRecommend() {
               return pageRecommend;
          }

          public void setPageRecommend(String pageRecommend) {
               this.pageRecommend = pageRecommend;
          }

          public String getMerchName() {
               return merchName;
          }

          public void setMerchName(String merchName) {
               this.merchName = merchName;
          }

          public String getAuthorTime() {
               return authorTime;
          }

          public void setAuthorTime(String authorTime) {
               this.authorTime = authorTime;
          }

          public String getCover() {
               return cover;
          }

          public void setCover(String cover) {
               this.cover = cover;
          }

          public String getImgId() {
               return imgId;
          }

          public void setImgId(String imgId) {
               this.imgId = imgId;
          }

          public String getImgIds() {
               return imgIds;
          }

          public void setImgIds(String imgIds) {
               this.imgIds = imgIds;
          }

          public String getFileId() {
               return fileId;
          }

          public void setFileId(String fileId) {
               this.fileId = fileId;
          }

          public String getFileIds() {
               return fileIds;
          }

          public void setFileIds(String fileIds) {
               this.fileIds = fileIds;
          }

          public String getServiceType() {
               return serviceType;
          }

          public void setServiceType(String serviceType) {
               this.serviceType = serviceType;
          }

          public String getPublishType() {
               return publishType;
          }

          public void setPublishType(String publishType) {
               this.publishType = publishType;
          }

          public String getCreateTime() {
               return createTime;
          }

          public void setCreateTime(String createTime) {
               this.createTime = createTime;
          }

          public String getNickName() {
               return nickName;
          }

          public void setNickName(String nickName) {
               this.nickName = nickName;
          }

          public String getAvatarUrl() {
               return avatarUrl;
          }

          public void setAvatarUrl(String avatarUrl) {
               this.avatarUrl = avatarUrl;
          }

          public String getVideoUrl() {
               return videoUrl;
          }

          public void setVideoUrl(String videoUrl) {
               this.videoUrl = videoUrl;
          }

          public String getArticleType() {
               return articleType;
          }

          public void setArticleType(String articleType) {
               this.articleType = articleType;
          }
     }

     @Override
     public String toString() {
          return "PlatformlVideoBean{" +
                  "records=" + records +
                  ", total=" + total +
                  ", size=" + size +
                  ", current=" + current +
                  ", orders=" + orders +
                  ", optimizeCountSql=" + optimizeCountSql +
                  ", hitCount=" + hitCount +
                  ", searchCount=" + searchCount +
                  ", pages=" + pages +
                  '}';
     }
}
