(function(){
	/**
	 * @param config{
	 *            renderTo     : '要追加到dom节点的id', 
	 *            width        : '表格宽度，可为百分比',
	 *            prefix	   : '版本前缀，防止版本号重复'
	 *            multiHeaders : '多行表头'
	 *            warnCall     : function(obj){}  预警颜色变化
	 *            checkboxCall : function(obj){}
	 *            checkboxs   : {	
	 *            				  checkbox  : 为true则显示数据行checkbox，默认false,
	 *          				  checkAll  : 为true则显示头部全选checkbox，默认false,
	 *                            dataIndex : value取值对应数据映射},
	 *            columns    : [
	 * 		                    {
     *                             name         :  列名称,str
     *                             dataIndex    :  列名称对应的数据映射,str
     *                             width    	:  列宽可这百分比或像数(像数时带上单位px),str
     *                             align        :  对名称的对齐方式 (默认center),str
	 *                             funHandler   :  显示要执行的函数(如字符串的截取)
	 *                             renderer     :  自定义列
	 *                             rendererCall :  自定义列点击事件回调 
	 *                             overflow		:  如果列超出行距，Tip框提示 (boolean)
	 *                             tipSize:		:  字数超过多少显示Tip（overflow为 [true] 时此配置有效） (int)
	 *                             tipAlign		:  Tip 显示的方向 (默认为bottom,left,right) (string)
     *                             }
     *                            ]
	 *       }
	 * 
	 * 回调事件中添加的元素如果要Tip显示，需要添加 class='_gird_overflow_tip' tipSize='20' tipAlign='left'
     * @author fb
	 */
	window.hellogood.ui.grid= function(config){
		var cfgs = {
				renderTo 	 : 'body',
				width        : '100%',
				checkboxs    : {checkbox:false,checkAll : false,dataIndex : 'id'},
				columns   	 : [],
				prefix		 : '',
				multiHeaders : false
		};
		
		$.extend(cfgs,config);
		

		var render = null;
		var tbBody = null;
		var tabel = null;
		var version = null;
	   
	    
	    var init = function(){
	    	version = cfgs.prefix.concat("version".concat(new Date().getTime()));
	    	render = $('#' + cfgs.renderTo);
	    	createGridHtml();
	    	createGridHeader();
	    	return this;
	    };
		
	    //创建整体框架
	    var createGridHtml = function(){
	    	render.html('<table width="'+cfgs.width+'"  id="'+getId("jqGrid_table")+'"  class="table table-thead table-hover table-border">' +
	    			'<thead id="'+getId('jqGrid_header')+'"> </thead>'+
	    			'<tbody id="'+getId('jqGrid_body')+'"> '+
	    			'</tbody>'+
	    		'</table>').hide();
		
			tbBody = $('#' + getId('jqGrid_body'));
			tabel = $('#' + getId('jqGrid_table'));
	    };
	    
	    //创建表格头列
	    var createGridHeader = function(){
	    	var $header = $('#'+getId('jqGrid_header'));
	    	
	    	//是否显示全选checkbox
	    	if(cfgs.checkboxs.checkbox) {
	    		if(cfgs.checkboxs.checkAll) {
		    		$header.append('<th><input type="checkbox" id="'+getId('checkAll')+'"></th>');
		    		$('#' + getId('checkAll')).unbind('click').bind('click',function(e){
		    			if (this.checked) {
		    				$('input[id^="'+getId('checkbox_')+'"]').attr("checked",true);
		    			} else {
		    				$('input[id^="'+getId('checkbox_')+'"]').attr("checked",false);
		    			}
		    			
		    			if (cfgs.checkboxCall) {
		    				cfgs.checkboxCall.call(null,getCheckDatas());
		    			}
		    		});
		    	} else {
		    		$header.append('<th">序号</th>');
		    	}
	    	}
	    	
	    	$.each(cfgs.columns, function(i, colum){
	    		colum.width = colum.width ? colum.width : "auto";
	    		colum.align = colum.align ? colum.align : 'center';
	    		var cssStyle = ' style="width:{0};text-align:{1}"'.replace('{0}',colum.width).replace('{1}',colum.align);
    			if(cfgs.multiHeaders){
    				createMultiHeaders($header, colum);
    			}else{
        			$header.append('<th '+cssStyle+'>'+colum.name+'</th>');
    			}
	    	});
	    };
	    
	    //多行表头
	    var lines = 2;
	    var createMultiHeaders = function($header, colum){
	    	if(colum.children == null || colum.children.length == 0){
    			$header.append('<th '+cssStyle+'>'+colum.name+'</th>');
    			$header.atrr("rowspan", lines);
	    	}else{
	    		$header.append("</tr>");
    			$header.append('<th '+cssStyle+'>'+colum.name+'</th>');
    			$header.atrr("cols", column.children.length);
    			$tr = $("<tr></tr>");
    			$header.append($tr);
	    		var childColumns = colum.children;
	    		$.each(childColumns, function(i, childColum){
	    			childColum.width = childColum.width ? childColum.width : "auto";
	    			childColum.align = childColum.align ? childColum.align : 'center';
		    		var cssStyle = ' style="width:{0};text-align:{1}"'.replace('{0}',childColum.width).replace('{1}',childColum.align);
		    		$tr.append('<th '+cssStyle+'>'+childColum.name+'</th>');
	    		});
	    	}
	    };
	    
	    //添加数据
	    var createRowData = function(index,rowData){
	    	var rowHtml = '<tr id="'+getId('tr_'+index)+'">';
	    	var rendererColum = {};
	    	
	    	if (cfgs.checkboxs.checkbox || cfgs.checkboxs.checkAll) {
				rowHtml += '<td><input type="checkbox" id="'
						+ getId('checkbox_'+index)
						+ '" value="'
						+ getRowDate(rowData, cfgs.checkboxs.dataIndex || 'id')
						+ '"></td>';
			}
	    	
	    	$.each(cfgs.columns, function(i, colum){
	    		var formatData;
	    		var tipClass = '';
	    		
	    		if (colum.funHandler){
	    			formatData =  colum.funHandler.call(null, rowData);
	    		} else {
	    			if (colum.renderer) {
	    				rendererColum[getId('renderer_') + i + '_' + index]= colum.rendererCall;
	    				var redererData = colum.renderer.call(null,rowData);
	    				tipClass = createTip(colum);
	    				formatData = '<span "'+tipClass+'" id="'+ getId('renderer_') + i + '_' + index +'">' + redererData + '</span>';
	    			} else {
	    				var tempDate = getRowDate(rowData, colum.dataIndex);
	    				formatData = (tempDate || (tempDate == 0 ? tempDate : '')) + '';
	    				tipClass = createTip(colum);
	    				formatData = '<span '+tipClass+'>'+formatData+'</span>';
	    			}
	    		}
	    		
	    		var tdAlign = colum.align?' style="text-align:'+colum.align+'"':'"style="text-align:center"';
	    		rowHtml += '<td ' + tdAlign+' >'+formatData+'</td>';
	    	});
	    	
	    	rowHtml += '</tr>';
	    	
	    	tbBody.append(rowHtml);
	    	
	    	$('#'+ getId('checkbox_'+index)).data('data',rowData);
	    	
	    	//预警颜色变化
	    	if (cfgs.warnCall) {
	    		cfgs.warnCall.call(null,{data:rowData, callFun : function(color){
	    			$('#'+ getId('tr_'+index)).css('color', color || 'red');
	    		}});
	    	}
	    	
	    	for(var _id in rendererColum) {
	    		addRowEventListen(_id, rendererColum[_id], rowData);
	    	}
	    	
	    	return rowHtml;
	    };
	    
	    //生成Tip提示框。
	    var createTip = function(colum){
	    	
    		var columnShowSize = 20;
    		var tipClass = '';
    		var tipAlign = 'center';

    		if(typeof(colum.overflow)!='undefined' && colum.overflow==true){
    		
    			if(colum.tipSize){
    				columnShowSize = colum.tipSize;
    			}else{
    				if(/^\d+$/.test(colum.width))
    				columnShowSize = parseInt(colum.width) / 2+5;
    			}
    			tipAlign = colum.tipAlign? colum.tipAlign:tipAlign;
	    		tipClass = ' class="_gird_overflow_tip" tipSize="'+columnShowSize+'" tipAlign="'+tipAlign+'" ';
    		}
			return tipClass;
	    };
	    
	    var getRowDate = function(rowDatas,dataIndex) {
	    	dataIndex = dataIndex || '';
	    	var indexs = dataIndex.split('\.');
	    	var rowDate = rowDatas;
	    	
	    	if (indexs.length > 1) {
	    		for (var i = 0 ; i < indexs.length; i++) {
	    			if (!rowDate) {
	    				continue;
	    			}
	    			
	    			rowDate = rowDate[indexs[i]];
	    		}
	    	} else {
	    		rowDate = rowDatas[dataIndex];
	    	}
	    	
	    	return rowDate;
	    };
	    
	    var addRowEventListen = function(id, callFun, rowData){
	    	if (callFun) {
	    		$('#' + id).find('a,img,button').unbind('click').bind('click',function(e){
	    			callFun.call(null, {id:this.id, data:rowData});
					 
					 return stopDefault(e);
				 });
	    	}
	    };
	    
		var addDatas = function(datas) {
			tbBody = $('#' + getId('jqGrid_body'));
			tabel = $('#' + getId('jqGrid_table'));
			clearData();
			render.show();
			if (!datas.rowDatas) {
				tbBody.html('<tr><td align="center" style="color:red;font-weight:bold;" colspan="' + cfgs.columns.length+1 + '">暂无数据</td></tr>');
				return this;
			}
			
			var gridRowDatas =datas.jsonReader ? jsonResolver(datas.jsonReader, datas.rowDatas) : datas.rowDatas;
			var rowsHmtl = '';
			
			$.each(gridRowDatas, function(i, rowData) {
				rowsHmtl += createRowData(i, rowData);
			});
			
			if (rowsHmtl) {
				//checkbox回调事件
				$('input[id^="'+getId('checkbox_')+'"]').unbind('click').bind('click',function(e){
	    			if (cfgs.checkboxCall) {
	    				cfgs.checkboxCall.call(null,getCheckDatas());
	    			}
	    		});
			} else {
				tbBody.html('<tr><td align="center" style="color:red;font-weight:bold;" colspan="' + cfgs.columns.length+1 + '">暂无数据</td></tr>');
			}
			gridTip();
			return this;
		};
		
		//获取所有选中的checkbox值 
		var getselecValues = function(){
			var checkboxs =  $("input[id^='"+getId('checkbox_')+"']:checked");
			var values = [];
			
			$.each(checkboxs, function(i, _checkbox){
				values.push(_checkbox.value);
			});
			
			return values;
		};
		
		var clearData = function(){
	    	tbBody.html('');
	    	// 去掉勾选框
	    	$('#' + getId('checkAll')).attr("checked",false);
	    };
	    
	    var getCheckDatas = function() {
	    	var datas = [];
	    	
	    	$('input[id^="'+getId('checkbox_')+'"]:checked').each(function(){
	    		datas.push($(this).data('data'));
	    	});
	    	
	    	return datas;
	    };
		
        var stopDefault = function(e){
            if(e && e.stopPropagation) {  
            	e.stopPropagation();
	        } else {
	            //否则，我们需要使用IE的方式来取消事件冒泡   
     		    e.cancelBubble = true;  
	        }
	        	        
	        if(e && e.preventDefault) {  
	        	e.preventDefault();
	        } else {  
	           e.returnValue = false;   
	        } 
	        
	        return false;
        };
        
        var getId = function(srcId){
			return version.concat(srcId);
		};
		
		var mergeCells = function(cols) {
			if (cols) {
				for (var i = cols.length - 1; cols[i] != undefined; i--) { 
					mergeCell(cols[i]); 
				} 
			} else {
				for (var i = 0 ; i <  cfgs.columns.length ; i++) { 
					mergeCell(i); 
				} 
			}
			
			dispose(); 
		};

		
		var mergeCell = function(colIndex) {
			var $table = tabel;
			$table.data('col-content', ''); // 存放单元格内容
			$table.data('col-rowspan', 1); // 存放计算的rowspan值 默认为1
			$table.data('col-td', $()); // 存放发现的第一个与前一行比较结果不同td(jQuery封装过的),
										// 默认一个"空"的jquery对象
			$table.data('trNum', $('tbody tr', $table).length); // 要处理表格的总行数,
																// 用于最后一行做特殊处理时进行判断之用
			// 我们对每一行数据进行"扫面"处理 关键是定位col-td, 和其对应的rowspan
			$('tbody tr', $table).each(
					function(index) {
						// td:eq中的colIndex即列索引
						var $td = $('td:eq(' + colIndex + ')', this);
						// 取出单元格的当前内容
						var currentContent = $td.html();
						// 第一次时走此分支
						if ($table.data('col-content') == '') {
							$table.data('col-content', currentContent);
							$table.data('col-td', $td);
						} else {
							// 上一行与当前行内容相同
							if ($table.data('col-content') == currentContent) {
								// 上一行与当前行内容相同则col-rowspan累加, 保存新值
								var rowspan = $table.data('col-rowspan') + 1;
								$table.data('col-rowspan', rowspan);
								// 值得注意的是 如果用了$td.remove()就会对其他列的处理造成影响
								$td.hide();
								// 最后一行的情况比较特殊一点
								// 比如最后2行 td中的内容是一样的,
								// 那么到最后一行就应该把此时的col-td里保存的td设置rowspan
								if (++index == $table.data('trNum'))
									$table.data('col-td').attr('rowspan',
											$table.data('col-rowspan'));
							} else { // 上一行与当前行内容不同
								// col-rowspan默认为1, 如果统计出的col-rowspan没有变化, 不处理
								if ($table.data('col-rowspan') != 1) {
									$table.data('col-td').attr('rowspan',
											$table.data('col-rowspan'));
								}
								// 保存第一次出现不同内容的td, 和其内容, 重置col-rowspan
								$table.data('col-td', $td);
								$table.data('col-content', $td.html());
								$table.data('col-rowspan', 1);
							}
						}
					});
		};

		var dispose = function() { 
			var $table = tabel;
			$table.removeData(); 
		};
		
		// 重置头
		var resetColumn = function(columns){
			cfgs.columns = columns;
			init();
		};
		
		// 重置头
		var showCheckBoxAndResetColumn = function(checkboxs, columns){
			cfgs.checkboxs = checkboxs;
			cfgs.columns = columns;
			init();
		};
		
		//为超长表格行添加Tip提示,样式标记为_gird_overflow_tip 的为需要添加Tip。
		var gridTip = function(){
			$('._gird_overflow_tip').each(function(){
				var $this = $(this);
				var val = $this.html();
				var showSize = $this.attr("tipSize")? parseInt($this.attr("tipSize")):20;
				
				if(showSize>0){
					var cleanValue = val.replace(/<[^>].*?>/g,'');
					var showValue = cleanValue; 
					//if(getByteLen(showValue)>showSize){
					if(showValue.length>showSize){
						showValue = showValue.substring(0,showSize) + "...";
						$this.attr('title',cleanValue);
						$this.html(val.replace(cleanValue,showValue));
					}
				}
				
				var align = $this.attr("tipAlign");
				var tip_cfgs = "";
				var defaultCfg = {
					className: 'tip-yellowsimple',
					showTimeout: 100,
					offsetY: 5,
					offsetX: 5,
					alignX: 'center',
					alignY: "bottom",
					alignTo: 'target'
				};
				if(align=="left"){
					tip_cfgs = {alignX: 'left',alignY: 'center'};
				}else if(align=="right"){
					tip_cfgs = {alignX: 'right',alignY: 'center'};
				}
				
				$.extend(defaultCfg, tip_cfgs);
				//$this.poshytip(defaultCfg);
				$this.removeClass('_gird_overflow_tip');
			});
		};
		
		return {
			addDatas       : addDatas,
			clearData      : clearData,
			mergeCell      : mergeCells,
			getselecValues : getselecValues,
			resetColumn    : resetColumn,
			showCheckBoxAndResetColumn   : showCheckBoxAndResetColumn,
			init           : init,
			getCheckDatas  : getCheckDatas   //获取当前选择的数据列
		};
	};
})();