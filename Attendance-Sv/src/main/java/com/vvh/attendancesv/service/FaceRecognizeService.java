package com.vvh.attendancesv.service;

import java.io.ByteArrayOutputStream;

public interface FaceRecognizeService extends Runnable{
    public String recognize(ByteArrayOutputStream byteArrayOutputStream);
}
