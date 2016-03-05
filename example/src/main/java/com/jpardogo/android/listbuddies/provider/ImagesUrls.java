package com.jpardogo.android.listbuddies.provider;

import android.content.Context;
import android.net.Uri;

import com.jpardogo.android.listbuddies.Utils.PhotoUtils;
import com.jpardogo.android.listbuddies.beans.Photo;
import com.jpardogo.android.listbuddies.beans.PhotoFloder;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.XMLFormatter;

import bolts.Task;
import bolts.TaskCompletionSource;

public class ImagesUrls {

    public static String[] imageUrls_left = new String[]{
//            "file:///storage/emulated/0/DCIM/file621250696198.jpg",
//            "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg",
//            "https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s1024/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg",
//            "https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s1024/Another%252520Rockaway%252520Sunset.jpg",
//            "https://lh3.googleusercontent.com/--L0Km39l5J8/URquXHGcdNI/AAAAAAAAAbs/3ZrSJNrSomQ/s1024/Antelope%252520Butte.jpg",
//            "https://lh6.googleusercontent.com/-8HO-4vIFnlw/URquZnsFgtI/AAAAAAAAAbs/WT8jViTF7vw/s1024/Antelope%252520Hallway.jpg",
//            "https://lh4.googleusercontent.com/-WIuWgVcU3Qw/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s1024/Antelope%252520Walls.jpg",
//            "https://lh6.googleusercontent.com/-UBmLbPELvoQ/URqucCdv0kI/AAAAAAAAAbs/IdNhr2VQoQs/s1024/Apre%2525CC%252580s%252520la%252520Pluie.jpg"
    };

    public static String[] imageUrls_right = new String[]{
//            "https://lh3.googleusercontent.com/-s-AFpvgSeew/URquc6dF-JI/AAAAAAAAAbs/Mt3xNGRUd68/s1024/Backlit%252520Cloud.jpg",
//            "https://lh5.googleusercontent.com/-bvmif9a9YOQ/URquea3heHI/AAAAAAAAAbs/rcr6wyeQtAo/s1024/Bee%252520and%252520Flower.jpg",
//            "https://lh5.googleusercontent.com/-n7mdm7I7FGs/URqueT_BT-I/AAAAAAAAAbs/9MYmXlmpSAo/s1024/Bonzai%252520Rock%252520Sunset.jpg",
//            "https://lh6.googleusercontent.com/-4CN4X4t0M1k/URqufPozWzI/AAAAAAAAAbs/8wK41lg1KPs/s1024/Caterpillar.jpg",
//            "https://lh3.googleusercontent.com/-rrFnVC8xQEg/URqufdrLBaI/AAAAAAAAAbs/s69WYy_fl1E/s1024/Chess.jpg"
    };

    public static Task<List<Photo>> fetchPhotosFromGallery(Context context) {
        Map<String, PhotoFloder> photos = PhotoUtils.getPhotos(context);

        List<Photo> photoList = ImagesUrls.getPhotoList(photos);

        final TaskCompletionSource<List<Photo>> tcs = new TaskCompletionSource<>();
        if (photos.size() == 0) {
            tcs.setError(new NullPointerException(""));
        } else {
            tcs.setResult(photoList);
        }
        return tcs.getTask();
    }

    private static List<Photo> getPhotoList(Map<String, PhotoFloder> photos) {
        List<Photo> photoList = new LinkedList<>();

        for(String key: photos.keySet()){
            PhotoFloder folder = photos.get(key);
            String name = folder.getName();
            List<Photo> list = folder.getPhotoList();
            photoList.addAll(list);
        }

        return photoList;
    }

    public static Task<Void> splitPhotos(List<Photo> photos) {
        List<String> imageUrls_left = new LinkedList<>();
        List<String> imageUrls_right = new LinkedList<>();
        for (int i = 0; i < photos.size(); i++) {
            String path = "file://" + (photos.get(i).getPath());
            if (i % 2 == 0) {
                imageUrls_left.add(path);
            } else {
                imageUrls_right.add(path);
            }
        }
        ImagesUrls.imageUrls_left = imageUrls_left.toArray(new String[imageUrls_left.size()]);
        ImagesUrls.imageUrls_right = imageUrls_right.toArray(new String[imageUrls_right.size()]);

        return Task.forResult(null);
    }
}
