package com.myselamat;

public abstract class QRCodeFoundListener {
    abstract void onQRCodeFound(String qrCode);
    abstract void qrCodeNotFound();
}
