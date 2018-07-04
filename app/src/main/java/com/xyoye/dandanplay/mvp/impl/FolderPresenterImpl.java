package com.xyoye.dandanplay.mvp.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.xyoye.core.base.BaseMvpPresenter;
import com.xyoye.core.db.DataBaseInfo;
import com.xyoye.core.db.DataBaseManager;
import com.xyoye.core.rx.Lifeful;
import com.xyoye.dandanplay.bean.VideoBean;
import com.xyoye.dandanplay.event.SaveCurrentEvent;
import com.xyoye.dandanplay.mvp.presenter.FolderPresenter;
import com.xyoye.dandanplay.mvp.view.FolderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YE on 2018/6/30 0030.
 */


public class FolderPresenterImpl extends BaseMvpPresenter<FolderView> implements FolderPresenter {

    public FolderPresenterImpl(FolderView view, Lifeful lifeful) {
        super(view, lifeful);
    }

    @Override
    public void init() {
        refreshVideos();
    }

    @Override
    public void process(Bundle savedInstanceState) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void refreshVideos() {
        String folderPath = getView().getFolderPath();
        List<VideoBean> videoBeans = getVideoList(folderPath);
        getView().refreshAdapter(videoBeans);
    }

    @Override
    public void updateDanmu(String danmuPath, String[] whereArgs) {
        SQLiteDatabase sqLiteDatabase = DataBaseManager.getInstance().getSQLiteDatabase();
        String whereCase = DataBaseInfo.getFieldNames()[2][1]+" =? AND "+ DataBaseInfo.getFieldNames()[2][2]+" =? ";
        ContentValues values = new ContentValues();
        values.put(DataBaseInfo.getFieldNames()[2][3],danmuPath);
        sqLiteDatabase.update(DataBaseInfo.getTableNames()[2],values,whereCase,whereArgs);
    }

    @Override
    public void updateCurrent(SaveCurrentEvent event) {
        SQLiteDatabase sqLiteDatabase = DataBaseManager.getInstance().getSQLiteDatabase();
        String whereCase = DataBaseInfo.getFieldNames()[2][1]+" =? AND "+ DataBaseInfo.getFieldNames()[2][2]+" =? ";
        ContentValues values = new ContentValues();
        values.put(DataBaseInfo.getFieldNames()[2][4], event.getCurrentPosition());
        sqLiteDatabase.update(DataBaseInfo.getTableNames()[2], values, whereCase, new String[]{event.getFolderPath(), event.getVideoName()});
    }


    private List<VideoBean> getVideoList(String folderPath){
        List<VideoBean> videoBeans = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = DataBaseManager.getInstance().getSQLiteDatabase();
        String sql = "SELECT * FROM file WHERE folder_path = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql ,new String[]{folderPath});
        while (cursor.moveToNext()){
            String fileName = cursor.getString(2);
            String filePath = cursor.getString(1) + fileName;
            String danmuPath = cursor.getString(3);
            int currentPosition = cursor.getInt(4);
            videoBeans.add(new VideoBean(fileName, filePath, danmuPath, currentPosition));
        }
        cursor.close();
        return videoBeans;
    }
}
