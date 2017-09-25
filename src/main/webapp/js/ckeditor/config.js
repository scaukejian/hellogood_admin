/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.image_previewText=' '; //预览区域显示内容  
    config.filebrowserImageUploadUrl= path+"/activity/uploadEditor.do"; //要上传的controller
    config.shiftEnterMode = CKEDITOR.ENTER_P;//shift+enter实现换段
    config.enterMode = CKEDITOR.ENTER_BR;//enter实现换行

    config.extraPlugins += (CKEDITOR.config.extraPlugins ? ',lineheight' : 'lineheight');
};
