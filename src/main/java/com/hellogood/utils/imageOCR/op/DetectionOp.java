/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hellogood.utils.imageOCR.op;

import com.hellogood.utils.imageOCR.ClientConfig;
import com.hellogood.utils.imageOCR.common_utils.CommonCodecUtils;
import com.hellogood.utils.imageOCR.common_utils.CommonFileUtils;
import com.hellogood.utils.imageOCR.exception.AbstractImageException;
import com.hellogood.utils.imageOCR.http.*;
import com.hellogood.utils.imageOCR.request.*;
import com.hellogood.utils.imageOCR.sign.Credentials;
import com.hellogood.utils.imageOCR.sign.Sign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.hellogood.utils.imageOCR.ClientConfig.*;

/**
 *
 * @author jusisli 此类封装了图片识别操作
 */
public class DetectionOp extends BaseOp {
    private static final Logger LOG = LoggerFactory.getLogger(DetectionOp.class);

    public DetectionOp(ClientConfig config, Credentials cred, AbstractImageHttpClient client) {
        super(config, cred, client);
    }
    
    /**
     * 黄图识别请求
     * 
     * @param request 黄图识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String pornDetect(PornDetectRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getDetectionPorn();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.setMethod(HttpMethod.POST);
        if (request.isUrl()) {
            httpRequest.addParam(RequestBodyKey.URL_LIST, (request.getUrlList())); 
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON); 
        } else {         
            httpRequest.setImageList(request.getImageList());
            httpRequest.setKeyList(request.getKeyList());
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
        /**
     * 标签识别请求
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String tagDetect(TagDetectRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getDetectionTag();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON); 
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
        } else {
            byte[] fileContentByte = null;
            try {
                fileContentByte = CommonFileUtils.getFileContentByte(request.getImage().getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String image = CommonCodecUtils.Base64Encode(fileContentByte);  
            httpRequest.addParam(RequestBodyKey.IMAGE, image);                 
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 身份证识别请求
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String idcardDetect(IdcardDetectRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getDetectionIdcard();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.CARD_TYPE, String.valueOf(request.getCardType()));
        
        httpRequest.setMethod(HttpMethod.POST);
        if (request.isUrl()) {
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON); 
            httpRequest.addParam(RequestBodyKey.URL_LIST, (request.getUrlList())); 
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            httpRequest.setImageList(request.getImageList());
            httpRequest.setKeyList(request.getKeyList());
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
     /**
     * 名片识别请求
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String namecardDetect(NamecardDetectRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudOcrDomain() + this.config.getDetectionNamecard();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.setMethod(HttpMethod.POST);
        
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.RET_IMAGE, String.valueOf(request.getRetImage()));              
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL_LIST, (request.getUrlList())); 
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON); 
        } else {
            httpRequest.setImageList(request.getImageList());
            httpRequest.setKeyList(request.getKeyList());
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
     /**
     * 名片识别请求
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String generalOcr(GeneralOcrRequest request) throws AbstractImageException {
        request.check_param();

        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudOcrDomain()+ OCR_GENERAL;

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        if (request.isUrl()) {
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            httpRequest.setImage(request.getImage());
        }
        return httpClient.sendHttpRequest(httpRequest);

    }
     /**
     * OCR-行驶证驾驶证识别
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String ocrDrivingLicence(OcrDrivingLicenceRequest request) throws AbstractImageException {
        request.check_param();

        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudOcrDomain()+ OCR_DRIVINGLICENCE;

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam("type", request.getType());
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            httpRequest.setImage(request.getImage());
        }
        return httpClient.sendHttpRequest(httpRequest);

    }
     /**
     * OCR-营业执照识别
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String ocrBizLicense(OcrBizLicenseRequest request) throws AbstractImageException {
        request.check_param();

        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudOcrDomain()+ OCR_BIZLICENSE;

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            httpRequest.setImage(request.getImage());
        }
        return httpClient.sendHttpRequest(httpRequest);

    }
     /**
     * OCR-银行卡识别
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String ocrBankCard(OcrBankCardRequest request) throws AbstractImageException {
        request.check_param();

        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudOcrDomain()+ OCR_BANKCARD;

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            httpRequest.setImage(request.getImage());
        }
        return httpClient.sendHttpRequest(httpRequest);

    }
     /**
     * OCR-车牌号识别
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String ocrPlate(OcrPlateRequest request) throws AbstractImageException {
        request.check_param();

        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudOcrDomain()+ OCR_PLATE;

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            httpRequest.setImage(request.getImage());
        }
        return httpClient.sendHttpRequest(httpRequest);

    }
    
    /**
     * 人脸检测请求
     * 
     * @param request 人脸检测请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceDetect(FaceDetectRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getDetectionFace();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.setMethod(HttpMethod.POST);
        
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.MODE, (request.getMode()));        
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
        } else {
            httpRequest.setImage(request.getImage());
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 人脸定位请求
     * 
     * @param request 人脸定位请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceShape(FaceShapeRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceShape();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.MODE, request.getMode());
        
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
        } else {
            httpRequest.setImage(request.getImage());
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 个体创建请求
     * 
     * @param request 个体创建请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceNewPerson(FaceNewPersonRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceNewPerson();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, request.getPersonId());
        httpRequest.addParam(RequestBodyKey.PERSON_NAME, request.getPersonName());        
        httpRequest.addParam(RequestBodyKey.TAG, request.getPersonTag());
        httpRequest.setMethod(HttpMethod.POST);
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.addParam(RequestBodyKey.GROUP_IDS, request.getGroupIds());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
        } else {
            int index;
            String[] groupIds = request.getGroupIds();
            for (index = 0;index < groupIds.length; index++) {
                String key =  String.format("group_ids[%d]", index);
                String data = groupIds[index];
                httpRequest.addParam(key, data); 
            } 
            httpRequest.setImage(request.getImage());
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 个体删除请求
     * 
     * @param request 个体删除请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceDelPerson(FaceDelPersonRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceDelPerson();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, request.getPersonId());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
      /**
     * 增加人脸请求
     * 
     * @param request 增加人脸请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceAddFace(FaceAddFaceRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceAddFace();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, String.valueOf(request.getPersonId()));
        httpRequest.addParam(RequestBodyKey.TAG, String.valueOf(request.getPersonTag()));        
        httpRequest.setMethod(HttpMethod.POST);       
        if (request.isUrl()) {
            httpRequest.addParam(RequestBodyKey.URLS, (request.getUrlList()));  
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON); 
        } else {
            httpRequest.setKeyList(request.getKeyList());
            httpRequest.setImageList(request.getImageList());
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
     /**
     * 人脸删除请求
     * 
     * @param request 人脸删除请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceDelFace(FaceDelFaceRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceDelFace();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, request.getPersonId());
        httpRequest.addParam(RequestBodyKey.FACE_IDS, request.getFaceIds());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
     /**
     * 人脸设置信息请求
     * 
     * @param request 人脸设置信息请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceSetInfo(FaceSetInfoRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceSetInfo();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, request.getPersonId());
        httpRequest.addParam(RequestBodyKey.PERSON_NAME, request.getPersonName());
        httpRequest.addParam(RequestBodyKey.TAG, request.getPersonTag());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 人脸获取信息请求
     * 
     * @param request 人脸获取信息请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceGetInfo(FaceGetInfoRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceGetInfo();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, request.getPersonId());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);      
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 获取组列表请求
     * 
     * @param request 获取组列表请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceGetGroupIds(FaceGetGroupIdsRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceGetGroupIdsInfo();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     *  获取人列表请求
     * 
     * @param request  获取人列表请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceGetPersonIds(FaceGetPersonIdsRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceGetPersonIdsInfo();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.GROUP_ID, request.getGroupId());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
     /**
     *  获取人脸列表请求
     * 
     * @param request  获取人脸列表请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceGetFaceIds(FaceGetFaceIdsRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceGetFaceIdsInfo();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, request.getPersonId());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     *  获取人脸信息请求
     * 
     * @param request  获取人脸信息请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceGetFaceInfo(FaceGetFaceInfoRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceGetFaceInfo();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.FACE_ID, request.getFaceId());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 人脸识别请求
     * 
     * @param request 人脸识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceIdentify(FaceIdentifyRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceIdentify();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.GROUP_ID, (request.getGroupId()));
        httpRequest.setMethod(HttpMethod.POST);
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
        } else {
            httpRequest.setImage(request.getImage());
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
     /**
     * 人脸验证请求
     * 
     * @param request 人脸验证请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceVerify(FaceVerifyRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceVerify();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, (request.getPersonId()));
        httpRequest.setMethod(HttpMethod.POST);
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
        } else {
            httpRequest.setImage(request.getImage());
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    /**
     * 人脸对比请求
     * 
     * @param request 人脸对比请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceCompare(FaceCompareRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceCompare();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());  
        httpRequest.setMethod(HttpMethod.POST);
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URLA, (request.getUrlA()));
            httpRequest.addParam(RequestBodyKey.URLB, (request.getUrlB()));
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
        } else {
            httpRequest.setImageList(request.getImageList());
            httpRequest.setKeyList(request.getKeyList());
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 身份证识别对比接口
     * 
     * @param request 身份证识别对比接口参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceIdCardCompare(FaceIdCardCompareRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceIdcardCompare();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.IDCARDNUMBER, (request.getIdcardNumber()));
        httpRequest.addParam(RequestBodyKey.IDCARDNAME, (request.getIdcardName()));
        httpRequest.addParam(RequestBodyKey.SESSONID, request.getSessionId());
        
        httpRequest.setMethod(HttpMethod.POST);
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
        } else {
            httpRequest.setImage(request.getImage());
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     *  获取验证码请求
     * 
     * @param request  获取验证码请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceLiveGetFour(FaceLiveGetFourRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceLiveGetFour();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        if ( (request.getSeq()!=null) && (request.getSeq()).trim().length() != 0 ) {
            httpRequest.addParam(RequestBodyKey.SEQ, request.getSeq());
        }
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 人脸识别请求
     * 
     * @param request 人脸识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceIdCardLiveDetectFour(FaceIdCardLiveDetectFourRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceIdCardLiveDetectFour();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.IDCARDNUMBER, (request.getIdcardNumber()));
        httpRequest.addParam(RequestBodyKey.IDCARDNAME, (request.getIdcardName()));
        httpRequest.addParam(RequestBodyKey.VALIDATE_DATA, (request.getValidate()));
        if ( (request.getSeq()!=null) && (request.getSeq()).trim().length() != 0 ) {
            httpRequest.addParam(RequestBodyKey.SEQ, request.getSeq());
        }
        httpRequest.setImage(request.getVideo());
        httpRequest.setImageKey("video");
        httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        httpRequest.setMethod(HttpMethod.POST);  
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 检测接口请求
     * 
     * @param request 检测接口请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceLiveDetectFour(FaceLiveDetectFourRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceLiveDetectFour();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.VALIDATE_DATA, request.getValidate());
        httpRequest.addParam(RequestBodyKey.COMPARE_FLAG, request.getCompareFlag());
        if ( (request.getSeq()!=null) && (request.getSeq()).trim().length() != 0 ) {
            httpRequest.addParam(RequestBodyKey.SEQ, request.getSeq());
        }
          
        httpRequest.setImageList(request.getImageList());
        httpRequest.setKeyList(request.getKeyList());
        httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
   
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    
}
