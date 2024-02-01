package com.vvh.coresv.service;

import com.google.zxing.WriterException;
import com.vvh.coresv.request.QRCodeRequest;

public interface QRCodeService {
    public String generatedQRCode(QRCodeRequest qrCodeRequest) throws WriterException;
}
