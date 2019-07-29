package com.bri.ojt.Util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bri.ojt.Model.Response.MutasiVAResponse;
import com.bri.ojt.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PDFHelper {

    //A4 size
    private static final int pageWidth = 2480;
    private static final int pageHeight = 3508;

    public static PdfDocument generateReport(Context context, List<MutasiVAResponse.DetailMutasi> dataList) {
        PdfDocument document = new PdfDocument();


        List<List<MutasiVAResponse.DetailMutasi>> dataSplitted = splitList(dataList, 25);
        PdfDocument.PageInfo pdfPage;

        for (int i = 0; i < dataSplitted.size(); i++) {

            pdfPage = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i+1).create();

            PdfDocument.Page page = document.startPage(pdfPage);
            Canvas canvas = page.getCanvas();

            //generate table header
            LinearLayout tableHeader = getTableHeader(context);

            int widthSpec = View.MeasureSpec.makeMeasureSpec(pageWidth-100, View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(200, View.MeasureSpec.AT_MOST);
            tableHeader.measure(widthSpec, heightSpec);

            tableHeader.layout(0,0, pageWidth-100, 200);


            canvas.save();
            canvas.translate(50, 10);

            tableHeader.draw(canvas);

            canvas.restore();


            //generate table column
            LinearLayout tableTitle = getTableTitle(context);

            int columnWidthSpec = View.MeasureSpec.makeMeasureSpec(pageWidth-100, View.MeasureSpec.EXACTLY);
            int columnHeightSpec = View.MeasureSpec.makeMeasureSpec(50, View.MeasureSpec.AT_MOST);
            tableTitle.measure(columnWidthSpec, columnHeightSpec);

            tableTitle.layout(0,0, pageWidth-100, 50);


            canvas.save();
            canvas.translate(50, 270);

            tableTitle.draw(canvas);

            canvas.restore();


            //generate row item
            LinearLayout tableRow = getTableItem(context, dataSplitted.get(i));
            int rowWidthSpec = View.MeasureSpec.makeMeasureSpec(pageWidth-100, View.MeasureSpec.EXACTLY);
            int rowHeightSpec = View.MeasureSpec.makeMeasureSpec(3100, View.MeasureSpec.AT_MOST);
            tableRow.measure(rowWidthSpec, rowHeightSpec);

            tableRow.layout(0,0, pageWidth-100, 3100);


            canvas.save();
            canvas.translate(50, 330);

            tableRow.draw(canvas);

            canvas.restore();

            //generate page number
            LinearLayout tableFooter = getTableFooter(context, i+1);
            int footerWidthSpec = View.MeasureSpec.makeMeasureSpec(pageWidth-100, View.MeasureSpec.EXACTLY);
            int footerHeightSpec = View.MeasureSpec.makeMeasureSpec(50, View.MeasureSpec.AT_MOST);
            tableFooter.measure(footerWidthSpec, footerHeightSpec);

            tableFooter.layout(0,0, pageWidth-100, 50);


            canvas.save();
            canvas.translate(50, 3300);

            tableFooter.draw(canvas);

            canvas.restore();


            document.finishPage(page);
        }





        return document;
    }

    private static LinearLayout getTableHeader(Context context) {
        LinearLayout parent = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parent.setLayoutParams(params);
        parent.setOrientation(LinearLayout.HORIZONTAL);
        parent.setGravity(Gravity.CENTER_VERTICAL);

        ImageView logo = new ImageView(context);
        logo.setImageResource(R.drawable.bri);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2);
        logo.setLayoutParams(params1);

        TextView reportTitle = new TextView(context);
        reportTitle.setText("MUTASI VIRTUAL ACCOUNT");
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4);
        reportTitle.setLayoutParams(params2);
        reportTitle.setTextColor(Color.BLACK);
        reportTitle.setTextSize(24f);
        reportTitle.setTypeface(null, Typeface.BOLD);
        reportTitle.setGravity(Gravity.CENTER);

        TextView reportDate = new TextView(context);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2);
        reportDate.setText(DateTimeHelper.getTimestamp());
        reportDate.setLayoutParams(params3);
        reportDate.setTextColor(Color.BLACK);
        reportDate.setTextSize(15f);
        reportDate.setTypeface(null, Typeface.BOLD);
        reportDate.setGravity(Gravity.END);

        parent.addView(logo);
        parent.addView(reportTitle);
        parent.addView(reportDate);


        return parent;
    }

    private static LinearLayout getTableTitle(Context context) {
        LinearLayout parent = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parent.setLayoutParams(params);
        parent.setOrientation(LinearLayout.VERTICAL);

        LinearLayout child1 = new LinearLayout(context);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        child1.setLayoutParams(params1);
        child1.setOrientation(LinearLayout.HORIZONTAL);

        View underline = new View(context);
        underline.setBackgroundColor(Color.BLACK);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 4);
        underline.setLayoutParams(params2);


        // Table Title

        TextView tglTrx = new TextView(context);
        tglTrx.setTypeface(null, Typeface.BOLD);
        tglTrx.setText("Tanggal Transaksi");
        tglTrx.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        tglTrx.setLayoutParams(params3);

        TextView keterangan = new TextView(context);
        keterangan.setTypeface(null, Typeface.BOLD);
        keterangan.setText("Keterangan");
        keterangan.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2);
        keterangan.setLayoutParams(params4);

        TextView debit = new TextView(context);
        debit.setTypeface(null, Typeface.BOLD);
        debit.setText("Debit");
        debit.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        debit.setLayoutParams(params5);
        debit.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView kredit = new TextView(context);
        kredit.setTypeface(null, Typeface.BOLD);
        kredit.setText("Kredit");
        kredit.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams params6 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        kredit.setLayoutParams(params6);
        kredit.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView saldoAkhir = new TextView(context);
        saldoAkhir.setTypeface(null, Typeface.BOLD);
        saldoAkhir.setText("Saldo Akhir");
        saldoAkhir.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams params7 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        saldoAkhir.setLayoutParams(params7);
        saldoAkhir.setGravity(Gravity.CENTER_HORIZONTAL);

        child1.addView(tglTrx);
        child1.addView(keterangan);
        child1.addView(debit);
        child1.addView(kredit);
        child1.addView(saldoAkhir);

        parent.addView(child1);
        parent.addView(underline);

        return parent;
    }

    private static LinearLayout getTableItem(Context context, List<MutasiVAResponse.DetailMutasi> dataList) {

        LinearLayout item = new LinearLayout(context);
        LinearLayout.LayoutParams paramItem = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        item.setLayoutParams(paramItem);
        item.setOrientation(LinearLayout.VERTICAL);

        for (MutasiVAResponse.DetailMutasi data : dataList) {
            LinearLayout parent = new LinearLayout(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            params.setMargins(0,0,0,15);
            parent.setPadding(10,10,10,10);
            parent.setLayoutParams(params);
            parent.setOrientation(LinearLayout.HORIZONTAL);
            parent.setBackgroundColor(context.getResources().getColor(R.color.grey400, context.getTheme()));

            LinearLayout.LayoutParams paramsWeight1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            LinearLayout.LayoutParams paramsWeight2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2);

            //Tgl transaksi
            TextView tglTrx = new TextView(context);
            tglTrx.setText(data.getTglTrxi());
            tglTrx.setTextColor(Color.BLACK);
            tglTrx.setLayoutParams(paramsWeight1);

            //Keterangan
            TextView keterangan = new TextView(context);
            keterangan.setText(data.getNomorSeqapp());
            keterangan.setTextColor(Color.BLACK);
            keterangan.setLayoutParams(paramsWeight2);

            //Debit
            TextView debit = new TextView(context);
            String debitValue = String.format(new Locale("id"), "IDR %,.2f", data.getAmountDebet());
            debit.setText(debitValue);
            debit.setGravity(Gravity.END);
            debit.setTextColor(Color.BLACK);
            debit.setLayoutParams(paramsWeight1);

            //Kredit
            TextView kredit = new TextView(context);
            String kreditValue = String.format(new Locale("id"), "IDR %,.2f", data.getAmountKredit());
            kredit.setText(kreditValue);
            kredit.setGravity(Gravity.END);
            kredit.setTextColor(Color.BLACK);
            kredit.setLayoutParams(paramsWeight1);

            //Saldo Akhir
            TextView saldoAkhir = new TextView(context);
            String saldoValue = String.format(new Locale("id"), "IDR %,.2f", data.getSaldoAkhir());
            saldoAkhir.setText(saldoValue);
            saldoAkhir.setGravity(Gravity.END);
            saldoAkhir.setTextColor(Color.BLACK);
            saldoAkhir.setLayoutParams(paramsWeight1);

            parent.addView(tglTrx);
            parent.addView(keterangan);
            parent.addView(debit);
            parent.addView(kredit);
            parent.addView(saldoAkhir);

            //add view to item
            item.addView(parent);
        }




        return item;
    }

    private static LinearLayout getTableFooter(Context context, int page) {
        LinearLayout parent = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parent.setLayoutParams(params);
        parent.setOrientation(LinearLayout.HORIZONTAL);
        parent.setGravity(Gravity.END);

        //page footer
        TextView pageNumber = new TextView(context);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        String paging = String.format(Locale.getDefault(), "page %d", page);
        pageNumber.setText(paging);
        pageNumber.setLayoutParams(params3);
        pageNumber.setTextColor(Color.BLACK);
        pageNumber.setTextSize(12f);
        pageNumber.setTypeface(null, Typeface.BOLD);
        pageNumber.setGravity(Gravity.END);

        parent.addView(pageNumber);


        return parent;
    }

    // chops a list into non-view sublists of length L
    private static <T> List<List<T>> splitList(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }

    private static List<MutasiVAResponse.DetailMutasi> generateList() {
        List<MutasiVAResponse.DetailMutasi> mutasiList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            mutasiList.add(new MutasiVAResponse.DetailMutasi("123 aksdakmsd asmdakmsd as damksdmaksmd amsdkasd amsd amksd amsd ams dma sdmak sdma smd akmsd amksd amksd ams damks dasdmas damksd asmd xsa","1231","12", 123, "123", "qwd", "ATM", "123", "asd", "asd", "asd", 12,1000000000000.99,12,12,"12","12","ad","12","asd","asd","asd"));
        }
        return mutasiList;
    }
}
