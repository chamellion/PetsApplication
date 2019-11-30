package com.example.petsapplication;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PetExecutor {

    private static final Object LOCK = new Object();
    private final Executor diskIO;
    private final Executor mMainThread;
    private final Executor networkIO;
    private static PetExecutor sInstance;

    private PetExecutor(Executor diskIO, Executor mMainThread, Executor networkIO) {
        this.diskIO = diskIO;
        this.mMainThread = mMainThread;
        this.networkIO = networkIO;
    }

    public static PetExecutor getInstance(){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new PetExecutor(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor DiskIO() {
        return diskIO;
    }

    public Executor MainThread() {
        return mMainThread;
    }

    public Executor NetworkIO() {
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor{

        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}