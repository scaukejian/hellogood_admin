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
 *
 * @author serenazhao 人脸识别请求
 */
public class FaceIdentifyRequest extends AbstractBaseRequest {
        //是否是url
        private boolean isUrl = true;
        
    	// url
	private String url = "";
        
	// 图片内容列表
        private File image =null;
        
        //要查询的组Id
         private String groupId = "";
              
        public FaceIdentifyRequest(String bucketName, String groupId, String url) {
		super(bucketName);
		this.isUrl = true;
                this.groupId = groupId;
                this.url = url;
	}
 
        public FaceIdentifyRequest(String bucketName, String groupId, String name, File image) {
		super(bucketName);
		this.isUrl = false;
                this.groupId = groupId;
                this.image = image;             
	}
        
        public boolean isUrl() {
            return isUrl;
        }
        
        public String getUrl() {
            return url;
        }
        
        public String getGroupId() {
            return groupId;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public File getImage() {
            return image;
        }

        public void setImage(File image) {
            this.image = image;
        }

	@Override
	public void check_param() throws ParamException {
		super.check_param();
                if(isUrl){
                    CommonParamCheckUtils.AssertNotNull("url", url);
                }else{
                    CommonParamCheckUtils.AssertNotNull("imageOCR content", image);
                }
	}
}
