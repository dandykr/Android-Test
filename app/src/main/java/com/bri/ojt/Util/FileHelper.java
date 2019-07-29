package com.bri.ojt.Util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;

import com.bri.ojt.Model.Response.RestResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileHelper {

    //Get Image Path
    public static String saveFile(Bitmap bitmap) {
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"BRICashCard");
        if (!pictureFileDir.exists()) {
            if (!pictureFileDir.mkdirs()) {
                return null;
            }
            return null;
        }
        String filename = pictureFileDir.getPath() +File.separator+ "BRICashCard" + System.currentTimeMillis()+".jpg";
        File pictureFile = new File(filename);
        try {
            if (!pictureFile.createNewFile()) {
                return null;
            }
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pictureFile.getAbsolutePath();
    }

    public static String saveFilePdf(Bitmap bitmap) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        canvas.drawBitmap(bitmap, 0,0, null);
        document.finishPage(page);

        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"BRICashCard");
        if (!pictureFileDir.exists()) {
            if (!pictureFileDir.mkdirs()) {
                return null;
            }
            return null;
        }

        String filename = pictureFileDir.getPath() +File.separator+ "BRICashCard" + System.currentTimeMillis()+".pdf";
        File pictureFile = new File(filename);
        try {
            if (!pictureFile.createNewFile()) {
                return null;
            }
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            document.writeTo(oStream);
            document.close();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pictureFile.getAbsolutePath();
    }

    public static String savePDFFile(PdfDocument document) {
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"BRICashCard");
        if (!pictureFileDir.exists()) {
            if (!pictureFileDir.mkdirs()) {
                return null;
            }
            return null;
        }

        String filename = pictureFileDir.getPath() +File.separator+ "BRICashCard" + System.currentTimeMillis()+".pdf";
        File pictureFile = new File(filename);
        try {
            if (!pictureFile.createNewFile()) {
                return null;
            }
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            document.writeTo(oStream);
            document.close();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pictureFile.getAbsolutePath();
    }
}
