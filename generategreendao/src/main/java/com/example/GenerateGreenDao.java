package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GenerateGreenDao {
    public static void main(String [] args){
        Schema schema=new Schema(3,"com.moho.greendao");

        // 一条待上传记录
        Entity upload = schema.addEntity("Upload");
        //主键
        upload.addLongProperty("uploadId").primaryKey();
        upload.addStringProperty("fireDeviceGuid");
        upload.addStringProperty("fireDeviceGroupGuid");
        upload.addIntProperty("type");
        upload.addIntProperty("checkResult");
        upload.addStringProperty("description");
        upload.addStringProperty("video");
        upload.addStringProperty("checkTime");
        upload.addStringProperty("photoUrls");
        upload.addStringProperty("audioUrls");
        upload.addStringProperty("deviceName");
        upload.addStringProperty("code");
        upload.addStringProperty("location");
        upload.addStringProperty("deviceState");
        upload.addStringProperty("longitude");
        upload.addStringProperty("latitude");
        // 2018-4-18 17:00:32
        upload.addStringProperty("signTime");
        // 2018-7-16 17:00:26
        upload.addStringProperty("checkTaskGuid");
        // 2018-9-14 19:34:00
        upload.addStringProperty("department");


//        // 图片
//        Entity photo = schema.addEntity("Photo");
//        photo.addStringProperty("PhotoUrl");
//
//        // 音频
//        Entity audio = schema.addEntity("Audio");
//        audio.addStringProperty("AudioUrl");
//
//        //建立一对多关联
//        Property property = photo.addLongProperty("uploadId").getProperty();
//        photo.addToOne(upload, property);
//        upload.addToMany(photo,property).setName("uploadPhoto");
//
//        Property property2 = audio.addLongProperty("uploadId").getProperty();
//        audio.addToOne(upload, property2);
//        upload.addToMany(audio,property2).setName("uploadAudio");




        try {
            new DaoGenerator().generateAll(schema,"app/src/main/java");
//            new DaoGenerator().generateAll(schema,"generategreendao/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
