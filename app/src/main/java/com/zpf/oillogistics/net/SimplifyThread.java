package com.zpf.oillogistics.net;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/19.
 * <p>
 * 线程封装
 */

public class SimplifyThread {

    private String url;
    private HashMap<String, String> map;
    // 线程(无key)
    private ThreadRunnable threadRunnable;
    // 线程(有key)
    private KeyThreadRunnable keyThreadRunnable;
 // 线程(get)
    private GetThreadRunnable getThreadRunnable;    private KeyGetThreadRunnable getKeyThreadRunnable;
    public SimplifyThread(String url, HashMap<String, String> map) {
        this.url = url;
        this.map = map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }

    // 无key
    public void setOnNoKeyResultListener(final OnResultListener onResultListener) {
        threadRunnable = new ThreadRunnable(onResultListener);
        SingletonPool.INSTANCE.getThreadPool().execute(threadRunnable);
    }

    // 无key重复调用
    public void againNokeStart() {
        SingletonPool.INSTANCE.getThreadPool().execute(threadRunnable);
    }

    // 有key
    public void setOnKeyResultListener(final OnResultListener onResultListener) {
        keyThreadRunnable = new KeyThreadRunnable(onResultListener);
        SingletonPool.INSTANCE.getThreadPool().execute(keyThreadRunnable);
    }

    public void setGetKeyResultListener(final OnResultListener onResultListener) {
        getKeyThreadRunnable = new KeyGetThreadRunnable(onResultListener);
        SingletonPool.INSTANCE.getThreadPool().execute(getKeyThreadRunnable);
    }

    // 有key重复调用
    public void againkeStart() {
        SingletonPool.INSTANCE.getThreadPool().execute(keyThreadRunnable);
    }

    // 有key
    public void setOnGetResultListener(final OnResultListener onResultListener) {
        getThreadRunnable = new GetThreadRunnable(onResultListener);
        SingletonPool.INSTANCE.getThreadPool().execute(getThreadRunnable);
    }

    // 有key重复调用
    public void againGetStart() {
        SingletonPool.INSTANCE.getThreadPool().execute(getThreadRunnable);
    }


    // 无key
    private class ThreadRunnable implements Runnable {

        private OnResultListener onResultListener;

        public ThreadRunnable(OnResultListener onResultListener) {
            this.onResultListener = onResultListener;
        }

        @Override
        public void run() {
            try {
                String res = new Request().noKeyRequest(map, url);
                if (res.length() < 4) {
                    onResultListener.errorBody(ErrorCode.getCodeCompare(res));
                } else {
                    onResultListener.resultBody(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                onResultListener.errorBody(ErrorCode.getCodeCompare(e.toString()));
            }
        }
    }
    // 有key
    private class KeyThreadRunnable implements Runnable {

        private OnResultListener onResultListener;

        public KeyThreadRunnable(OnResultListener onResultListener) {
            this.onResultListener = onResultListener;
        }

        @Override
        public void run() {
            try {
                String res = new Request().keyRequest(map, url);
                if (res.length() < 4) {
                    onResultListener.errorBody(ErrorCode.getCodeCompare(res));
                } else {
                    onResultListener.resultBody(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                onResultListener.errorBody(ErrorCode.getCodeCompare(e.toString()));
            }
        }
    }

// get有key
    private class KeyGetThreadRunnable implements Runnable {

        private OnResultListener onResultListener;

        public KeyGetThreadRunnable(OnResultListener onResultListener) {
            this.onResultListener = onResultListener;
        }

        @Override
        public void run() {
            try {
                String res = new Request().keyGetRequest(map, url);
                if (res.length() < 4) {
                    onResultListener.errorBody(ErrorCode.getCodeCompare(res));
                } else {
                    onResultListener.resultBody(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                onResultListener.errorBody(ErrorCode.getCodeCompare(e.toString()));
            }
        }
    }private class GetThreadRunnable implements Runnable {

        private OnResultListener onResultListener;

        public GetThreadRunnable(OnResultListener onResultListener) {
            this.onResultListener = onResultListener;
        }

        @Override
        public void run() {
            try {
                String res = new Request().getRequest(map, url);
                if (res.length() < 4) {
                    onResultListener.errorBody(ErrorCode.getCodeCompare(res));
                } else {
                    onResultListener.resultBody(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                onResultListener.errorBody(ErrorCode.getCodeCompare(e.toString()));
            }
        }
    }    // 接口
    public interface OnResultListener {
        void resultBody(String res);

        void errorBody(String error);
    }
}
