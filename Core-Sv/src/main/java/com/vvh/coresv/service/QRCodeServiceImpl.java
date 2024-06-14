package com.vvh.coresv.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.vvh.coresv.dto.request.QRCodeRequest;
import com.vvh.coresv.utils.JWTProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QRCodeServiceImpl implements QRCodeService{

    @Value("core_url")
    private String url;

    private final JWTProvider jwtProvider;

    public QRCodeServiceImpl(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String generatedQRCode(QRCodeRequest request){

        String data = url + "/" +
                request.getMeetingId() + "/" +
                jwtProvider.generatedToken(request.getMeetingId(), request.getExpireTime());

        return "data: image/png;base64," +
                new String(Base64.getEncoder().encode(
                        createQRCode(
                                data,
                                request.getWidth(),
                                request.getHeight())
                .toByteArray()));
    }

    private ByteArrayOutputStream createQRCode(String data, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        } catch (IOException | WriterException e) {
            throw new RuntimeException(e);
        }
        return byteArrayOutputStream;
    }
}
