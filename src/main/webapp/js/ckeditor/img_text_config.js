/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

/*CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
};*/
/*
Copyright (c) 2003-2010, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	config.shiftEnterMode = CKEDITOR.ENTER_P;//shift+enter实现换段
	config.enterMode = CKEDITOR.ENTER_BR;//enter实现换行
	config.image_previewText=' '; //预览区域显示内容  
    config.filebrowserImageUploadUrl= path+"/activity/uploadEditor.do"; //要上传的controller
	config.autoUpdateElement = true;
	config.toolbar = 'img_text_Toolbar';

    config.toolbar_img_text_Toolbar =
     [
			{
				name : 'colors',
				items : [ 'Cut', 'Copy', 'TextColor', 'BGColor', 'Styles',
						'Format', 'Bold', 'Italic' ]
			},
			{
				name : 'insert',
				items : [ 'Image' ]
			},
			{
				name : 'paragraph',
				items : [ 'NumberedList', 'BulletedList', '-', 'Outdent',
						'Indent', '-', 'Blockquote',  '-',
						'JustifyLeft', 'JustifyCenter', 'JustifyRight',
						'JustifyBlock', '-', 'BidiLtr', 'BidiRtl' ]
			} ];
};
/*
config.toolbar = 'Full';
 
config.toolbar_Full =
[
	{ name: 'document', items : [ 'Source','-','Save','NewPage','DocProps','Preview','Print','-','Templates' ] },
	{ name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
	{ name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },
	{ name: 'forms', items : [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 
 
         'HiddenField' ] },
	'/',
	{ name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
	{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv','-
 
        ','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },
	{ name: 'links', items : [ 'Link','Unlink','Anchor' ] },
	{ name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe' ] },
	'/',
	{ name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },
	{ name: 'colors', items : [ 'TextColor','BGColor' ] },
	{ name: 'tools', items : [ 'Maximize', 'ShowBlocks','-','About' ] }
];
 
config.toolbar_Basic =
[
	['Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink','-','About']
];

*/

