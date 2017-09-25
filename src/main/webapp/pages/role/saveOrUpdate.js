util.hellogoodAjax({
            url: "",
          //  data: _data,
            succFun: function (json) {
                if (json.errorMsg) {
                    $.Prompt(json.errorMsg);
                    return;
                }
                roleGrid.addDatas({
                    rowDatas: json.dataList
                });
                pageTool.render(json.total, page);
            }
        });