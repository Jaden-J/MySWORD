/*
    Variable filter is a global variable which is declared in JSP page.
 */

function initialPosterList() {
    var sourceData = new Array();
    $.ajax({async: false, type: "post", url: "KnowledgeSharing!listPoster", dataType: "json",
        complete: function (response, returnType) {
            if(response.responseJSON == null || response.responseJSON == "") {
                post("/${mysword_path}/error", "_self", {error: response.responseText});
            } else {
                sourceData = response.responseJSON.posterArray;
            }
        }
    })
    $('#poster').jqxDropDownList({ source: sourceData, selectedIndex: 0, width: 125, height: 25, theme: theme });
}

function showKnowledgeSharingList(isAdmin) {
    $("#table").jqxDataTable('clear');
    var source =
    {
        async: false,
        type : 'post',
        url: "KnowledgeSharing!listKnowledgeSharing",
        datatype: "json",
        datafields: [
            { name: 'share_Id', type: 'string' },
            { name: 'from_Address', type: 'string' },
            { name: 'to_Address', type: 'string' },
            { name: 'cc_Address', type: 'string' },
            { name: 'bcc_Address', type: 'string' },
            { name: 'subject', type: 'string' },
            { name: 'sendDate', type: 'string' },
            { name: 'contentType', type: 'string' },
            { name: 'approval', type: 'string' },
            { name: 'approved_By', type: 'string'},
            { name: 'approved_Date', type: 'string'},
            { name: 'poster', type: 'string' },
            { name: 'post_Date', type: 'string' },
            { name: 'filename', type: 'string' },
            { name: 'lasteditby', type: 'string' },
            { name: 'lasteditdt', type: 'string' }
        ],
        data: {approval: filter.approval,
            share_Id: filter.share_Id,
            subject: filter.subject,
            poster: filter.poster,
            content: filter.content,
            startTime: filter.startTime,
            endTime: filter.endTime},
        id: 'share_Id',
        root: 'knowledgeList',
        updateRow: function (rowID, rowData, commit) {
            $.ajax({
                type : 'post',
                url : 'KnowledgeSharing!updateKnowledgeSharing',
                dataType: 'json',
                data: { share_Id: rowID, approval: rowData.approval },
                async: false,
                complete: function(response, returnType) {
                    if(response.responseJSON == null || response.responseJSON == "") {
                        commit(false);
                        post("/${mysword_path}/error", "_self", {error: response.responseText});
                    } else {
                        rowData.approved_By = response.responseJSON.knowledgeList[0].approved_By;
                        rowData.approved_Date = response.responseJSON.knowledgeList[0].approved_Date;
                        commit(true);
                    }
                }
            });
        },
        deleteRow: function (rowID, commit) {
            $.ajax({
                type : 'post',
                url : 'KnowledgeSharing!deleteKnowledgeSharing',
                dataType: 'json',
                data: { share_Id: rowID },
                async: false,
                complete: function(response, returnType) {
                    if(response.responseJSON == null || response.responseJSON == "") {
                        commit(false);
                        post("/${sessionScope.mysword_path}/error", "_self", {error: response.responseText});
                    } else {
                        commit(true);
                    }
                }
            });
        }
    };
    var dataAdapter = new $.jqx.dataAdapter(source,{
        formatData: function (data) {
            return data;
        },
        downloadComplete: function (data, status, xhr) {
            if (!source.totalRecords) {
                source.totalRecords = parseInt(data["totalCount"]);
            }
        },
        loadError: function (xhr, status, error) {
            post("/${mysword_path}/error", "_self", {error: xhr.responseText});
        }
    });
    $("#table").jqxDataTable({source: dataAdapter, altrows: true, enableBrowserSelection: true, columnsResize: true, pageable:true, pageSize: 12, pageSizeOptions: ['12', '50', '100', '200', '400'], pagerPosition:'bottom',
        pagerMode: "advanced", serverProcessing: true, theme: theme, showToolbar: isAdmin,
        renderToolbar: function (toolBar) {
            toolBar.html("");
            toolBar.append("<div style='margin: 2px'><button id='deleteBt'>Delete</button>&nbsp;<button id='approveBt'>Approve</button>&nbsp;<button id='holdBt'>Hold</button></div>");
            $("#deleteBt, #approveBt, #holdBt").jqxButton({height: 27, width: 70, theme: theme});
            $("#deleteBt, #approveBt, #holdBt").on('click', function (event) {
                var selection = $("#table").jqxDataTable('getSelection');
                for(var i=0; i<selection.length; i++) {
                    if (this.id == "deleteBt") {
                        $("#table").jqxDataTable('deleteRow', selection[i].index-1);
                    } else if(this.id == "approveBt") {
                        selection[i].approval = 'Y';
                        $("#table").jqxDataTable('updateRow', selection[i].index-1, selection[i]);
                    } else if(this.id == "holdBt") {
                        selection[i].approval = 'H';
                        $("#table").jqxDataTable('updateRow', selection[i].index-1, selection[i]);
                    }
                }
            });
        },
        columns: [
            { dataField: 'index', editable: false, width: 20, renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>No</div>";}, cellsRenderer: function (row, column, value, rowData) { rowData.index = row+1; return row+1;}},
            { dataField: 'share_Id', renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold; font-family: Arial'>Share ID</div>";},  cellsRenderer: function (row, column, value, rowData) {
                return "<a href='KnowledgeSharing!showKnowledgeSharing?share_Id=" + value + "&filename=" + rowData.filename + "'>" + value + "</a>";
            }, editable: false, width: 145 },
            { dataField: 'subject', editable: false, width: 550, renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>Subject</div>";} },
            { columntype: 'template', dataField: 'approval', resizable: false, width: 40, renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>Appr</div>";}},
            { dataField: 'poster', editable: false, width: 100, renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>Poster</div>";} },
            { dataField: 'post_Date', editable: false, width: 160, renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>Post Date</div>";}  },
            { dataField: 'approved_By', editable: false, width: 100, renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>Appr By</div>";} },
            { dataField: 'approved_Date', editable: false, width: 160, renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>Appr DT</div>";} }
        ]
    });
    $('#table').on('bindingComplete', function (event) {;
        resetWindowHeight();
    });
    resetWindowHeight();
}

function initialPage(isAdmin) {
    var startTime = new Date();
    startTime.setDate(startTime.getDate() - 7);
    var endTime = new Date();
    $("#shareId").jqxInput({ width: '190px', height: '25px', theme: theme });
    $("#subject").jqxInput({ width: '190px', height: '25px', theme: theme });
    $("#content").jqxInput({ width: '190px', height: '25px', theme: theme });
    $("#startTime").jqxDateTimeInput({ width: '190px', value: startTime, height: '25px', theme: theme, formatString: 'yyyy-MM-dd HH:mm:ss' });
    $("#endTime").jqxDateTimeInput({ width: '190px', value: endTime, height: '25px', theme: theme, formatString: 'yyyy-MM-dd HH:mm:ss' });
    if(isAdmin) {
        $("#approval").jqxDropDownList({ source: [{label: 'All', value: null}, {label: 'Y', value: 'Y'}, {label: 'N', value: 'N'}, {label: 'H', value: 'H'}], displayMember: 'label', valueMember: 'value', selectedIndex: 0, width: 50, height: 25, dropDownHeight: 105, theme: theme });
    } else {
        $("#approval").html("Y");
    }
    $("#search").jqxButton({ width: '80px', height: '30px', theme: theme });
    $("#search").on('click', function () {
        $("#loading").css("display", "inline");
        $("#search").jqxButton({disabled: true});
        filter.approval = $("#approval").val();
        filter.share_Id = $("#shareId").val();
        filter.subject = $("#subject").val();
        filter.poster = $("#poster").val();
        filter.content = $("#content").val();
        filter.startTime = $("#startTime").val();
        filter.endTime = $("#endTime").val();
        showKnowledgeSharingList();
        $("#loading").css("display", "none");
        $("#search").jqxButton({disabled: false});
    });
}