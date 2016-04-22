package com.zanexess.track02;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.lang.ref.WeakReference;

// Серая заглушка, которая отрисовается, пока файл еще не загружен
// Привязана к AsyncTask
public class DownloadDrawable extends ColorDrawable {
    private final WeakReference<LoadImageTask> _loadTaskWeak;

    public DownloadDrawable(LoadImageTask loadTask) {
        super(Color.GRAY);
        _loadTaskWeak = new WeakReference<>(loadTask);
    }

    public LoadImageTask getTask() {
        return _loadTaskWeak.get();
    }
}

