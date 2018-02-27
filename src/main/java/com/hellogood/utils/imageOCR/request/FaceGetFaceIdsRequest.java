/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hellogood.utils.imageOCR.request;

import com.hellogood.utils.imageOCR.common_utils.CommonParamCheckUtils;
import com.hellogood.utils.imageOCR.exception.ParamException;

/**
 *
 * @author serenazhao  获取人脸列表
 */
public class FaceGetFaceIdsRequest extends AbstractBaseRequest {
        
        //要查询的个体Id
        private String personId;
        
        public FaceGetFaceIdsRequest(String bucketName, String personId) {
		super(bucketName);
                this.personId = personId;
	}
        
        public String getPersonId() {
                return personId;              
        }

	@Override
	public void check_param() throws ParamException {
		super.check_param();
                CommonParamCheckUtils.AssertNotNull("personId", personId);
	}
}
