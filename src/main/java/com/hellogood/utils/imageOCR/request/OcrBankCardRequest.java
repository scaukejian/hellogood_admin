/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hellogood.utils.imageOCR.request;

import com.hellogood.utils.imageOCR.common_utils.CommonParamCheckUtils;
import com.hellogood.utils.imageOCR.exception.ParamException;

import java.io.File;

/**
 * OCR-银行卡识别
 */
public class OcrBankCardRequest extends AbstractBaseRequest {

    private boolean isUrl;
    private String url = "";
    private File image;

    public OcrBankCardRequest(String bucketName, String url) {
        super(bucketName);
        this.isUrl = true;
        this.url = url;
    }

    public OcrBankCardRequest(String bucketName, File image) {
        super(bucketName);
        this.isUrl = false;
        this.image = image;
    }

    @Override
    public void check_param() throws ParamException {
        super.check_param();
        if (isUrl) {
            CommonParamCheckUtils.AssertNotNull("url", url);
        } else {
            CommonParamCheckUtils.AssertNotNull("imageOCR content", image);
        }
    }

    public boolean isUrl() {
        return isUrl;
    }

    public String getUrl() {
        return url;
    }

    public File getImage() {
        return image;
    }

}
