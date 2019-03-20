package com.necistudio.vigerpdf.manage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;

import com.necistudio.vigerpdf.VigerPDF;

import org.vudroid.core.DecodeServiceBase;
import org.vudroid.core.codec.CodecPage;
import org.vudroid.pdfdroid.codec.PdfContext;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Vim on 1/31/2017.
 */

public class RenderingPDF extends AsyncTask<Void, String, ArrayList<Bitmap>> {
    private File file;
    private Context context;
    private int pageCount, pageData, type;

    public RenderingPDF(Context context, File file, int type) {
        this.file = file;
        this.context = context;
        this.type = type;
    }

    @Override
    protected ArrayList<Bitmap> doInBackground(Void... params) {
        try {
            ArrayList<Bitmap> uris = new ArrayList<>();
            DecodeServiceBase decodeService = new DecodeServiceBase(new PdfContext());
            decodeService.setContentResolver(context.getContentResolver());
            decodeService.open(Uri.fromFile(file));
            pageCount = decodeService.getPageCount();

            for (int i = 0; i < pageCount; i++) {
                pageData = i;
                if (isCancelled()) {
                    break;
                }
                CodecPage page = decodeService.getPage(i);
                RectF rectF = new RectF(0, 0, 1, 1);
                Bitmap bitmap = page.renderBitmap(decodeService.getPageWidth(i), decodeService.getPageHeight(i), rectF);
                try {
                    uris.add(bitmap);
                } catch (Exception e) {
                }
                publishProgress("" + (int) ((i * 100) / pageCount));
            }
            if (type == 1) {
                file.delete();
            }

            return uris;

        } catch (Exception ignored) {

        }
        return null;

    }

    @Override
    public void onPostExecute(ArrayList<Bitmap> uris) {
        try {
            VigerPDF.setData(uris);
        } catch (Exception ignored) {

        }
    }
}
