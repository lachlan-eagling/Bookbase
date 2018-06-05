package com.bookbase.app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

import com.bookbase.app.addBook.AddBookBarcodeContract;
import com.bookbase.app.mainscreen.HomeScreen;
import com.bookbase.app.model.api.BooksApiCallback;
import com.bookbase.app.model.api.GoogleBooksApi;
import com.bookbase.app.model.entity.Book;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.util.List;
import java.util.UUID;

public class BarcodeService {

    private BarcodeDetector detector;

    public BarcodeService() {
        // TODO: Inject context.
        detector = new BarcodeDetector.Builder(HomeScreen.context)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
    }

    public void decodeBarcode(String image, final AddBookBarcodeContract presenterCallback) {
        Bitmap barcodeImage = BitmapFactory.decodeFile(image);

        if (detector.isOperational() && barcodeImage != null) {
            Frame frame = new Frame.Builder().setBitmap(barcodeImage).build();
            SparseArray<Barcode> barcodes = detector.detect(frame);
            Barcode code;
            if (barcodes.size() > 0) {
                code = barcodes.valueAt(0);

                UUID requestId = UUID.randomUUID();
                BooksApiCallback callback = new BooksApiCallback() {
                    @Override
                    public void onError() {
                        presenterCallback.onBarcodeError();
                    }

                    @Override
                    public void onComplete(List<Book> books) {
                        if (!books.isEmpty()) {
                            presenterCallback.returnBook(books.get(0));
                        }
                    }

                    @Override
                    public void inProgress() {
                        presenterCallback.barcodeProcessing();
                    }
                };
                GoogleBooksApi.queryByIsbn(code.rawValue, requestId, callback);
            }
        }
    }
}
