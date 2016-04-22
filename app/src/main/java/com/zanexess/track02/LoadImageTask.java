package com.zanexess.track02;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

// Класс для загрузки изображений через AsyncTask
// У каждой задачи есть свой уникальный идентификатор
public class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {
    private final WeakReference<ImageView> _weakIv;
    private final WeakReference<Context> _context;
    private final String _name;
    private int _imageSize;
    private final boolean _withCalculation;

    public LoadImageTask(Context context, ImageView iv, String name, Fragment fragment, boolean withCalculation) {
        super();
        _weakIv = new WeakReference<>(iv);
        _context = new WeakReference<>(context);
        _name = name;
        _withCalculation = withCalculation;
        _imageSize = updateImageSize(fragment.getActivity().getResources().getDisplayMetrics());
    }

    public String get_name() {
        return _name;
    }

    //Адаптируем изображения под нужный размер
    public static int updateImageSize(DisplayMetrics dm) {
        int h = dm.heightPixels;
        int w = dm.widthPixels;

        if (w > h) {
            int tmp = w;
            w = h;
            h = tmp;
        }
        return (int)(Math.min(h * 0.3f, w * 0.3f) + 0.5f);
    }

    protected Bitmap decodeFile(File file) {
        try {
            InputStream is = new FileInputStream(file);
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, opt);
            // Возможность пересчета изображения в оптимальный размер.
            // Регулируется по флагу, в зависимости от фрагмента, из которого вызывается
            if (_withCalculation) {
                int sc = calculateInSampleSize(opt, _imageSize, _imageSize);
                opt.inSampleSize = sc;
            }
            opt.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, opt);

            if (bitmap == null) {
                return null;
            }

            Log.d("LOAD_IMAGE", " name = " + _name + " w = " + bitmap.getWidth() + " h = " + bitmap.getHeight());
            return bitmap;
        } catch (IOException e) {
            //Log.e("LoadImageTask", "LoadImageTask.LoadBitmap IOException " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            Context context = _context.get();
            Bitmap bitmap;
            File file;
            if (context != null) {
                file = new File(context.getCacheDir(), _name.replace("/", ""));
                bitmap = decodeFile(file);
                if (null == bitmap ) {
                    URL url = new URL(_name);
                    InputStream is = url.openConnection().getInputStream();
                    OutputStream os = new FileOutputStream(file);
                    Utils.CopyStream(is, os);
                    os.close();
                    bitmap = decodeFile(file);
                }
                return bitmap;
            }
        } catch (IOException e) {
            //Log.e("LoadImageTask", "LoadImageTask.LoadBitmap IOException " + e.getMessage(), e);
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled())
            bitmap = null;
        Bitmap bm = LRUCache.instance().getBitmapFromMemCache(_name);

        //Если изображение еще не встречалось - кладем в кэш
        if (bm == null && bitmap != null) {
            LRUCache.instance().addBitmapToMemoryCache(_name, bitmap);
            bm = bitmap;
        }
        ImageView iv = _weakIv.get();
        if (iv != null && this == getBitmapDownloaderTask(iv)) {
            iv.setImageBitmap(bm);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv.getLayoutParams();
            if (_withCalculation) {
                params.width = _imageSize;
                params.height = _imageSize;
            }
            params.gravity = Gravity.CENTER_HORIZONTAL;
            iv.setLayoutParams(params);

        }
    }

    // Подгружаем изображение эффективным образом.
    // http://developer.android.com/intl/ru/training/displaying-bitmaps/load-bitmap.html
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static void loadBitmap(Context context, String name, ImageView iv, Fragment fragment, boolean _withCalculation) {
        final Bitmap bm = LRUCache.instance().getBitmapFromMemCache(name);
        if (null != bm) {
            cancelDownload(name, iv);
            iv.setImageBitmap(bm);
        } else {
            LoadImageTask lt = new LoadImageTask(context, iv, name, fragment,  _withCalculation);
            DownloadDrawable dd = new DownloadDrawable(lt);
            iv.setImageDrawable(dd);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                lt.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                lt.execute();
        }
    }

    // Жестко прерываем загрузку, если она не нужна.
    private static void cancelDownload(String key, ImageView imageView) {
        LoadImageTask task = getBitmapDownloaderTask(imageView);
        if (null != task) {
            String bitKey = task.get_name();
            if ((bitKey == null) || (!bitKey.equals(key))) {
                task.cancel(true);
            }
        }
    }

    public static LoadImageTask getBitmapDownloaderTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadDrawable) {
                DownloadDrawable dd = (DownloadDrawable)drawable;
                return dd.getTask();
            }
        }
        return null;
    }
}