package com.vvh.coresv.service;

import com.google.zxing.WriterException;
import com.vvh.coresv.dto.request.QRCodeRequest;

public interface QRCodeService {
    public String generatedQRCode(QRCodeRequest qrCodeRequest) throws WriterException;
}
