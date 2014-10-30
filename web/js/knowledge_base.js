/*
 Please be noted that resetWinodwHeight() function is in main.js.
 */

function showComsys(environment, theme) {
    var source = {
        datatype: "json",
        url: "KnowledgeBase!listComsys",
        data: {'environment': environment},
        datafields: [
            { name: 'environment' },
            { name: 'title' },
            { name: 'instance' },
            { name: 'system' },
            { name: 'lasteditby' },
            { name: 'lasteditdt' }
        ],
        root: 'comsysList'
    };
    var dataAdapter = new $.jqx.dataAdapter(source);
    $('#' + environment).jqxDataTable({ width: 650, theme: theme, source: dataAdapter, enableBrowserSelection: true, altrows: true,
        columns: [
            { text: 'Title', datafield: 'title', width: 200 },
            { text: 'Instance', datafield: 'instance', width: 300 },
            { text: 'System', datafield: 'system', width: 150 }
        ]
    });
    $('#' + environment).on('bindingComplete', function (event) {
        resetWindowHeight();
    });
}

function showHowTo(theme) {
    var source = {
        url: "HowTo!howToFileList",
        async: false,
        dataType: "json",
        dataFields: [
            {name: "filename", type: "string"},
            {name: "fileSize", type: "number"},
            {name: "fileTimestamp", type: "date"},
            {name: "fileGroup", type: "string"},
            {name: "fileType", type: "string"},
            {name: "username", type: "string"},
            {name: "directory", type: "bool"},
            {name: "file", type: "bool"},
            {name: "unknown", type: "bool"}
        ],
        id: "filename"
    };
    var dataAdapter = new $.jqx.dataAdapter(source, {
        loadError: function (xhr, status, error) {
            post("/${mysword_path}/error", "_self", {error: xhr.responseText});
        }
    });
    $("#dataTable").jqxDataTable({source: dataAdapter, theme: theme, altRows: true,
        columns: [
            { text: 'Type', dataField: 'fileType', width: 50, cellsalign: 'center', cellsRenderer: function (row, column, value, rowData){
                var imgFile;
                if(rowData.unknown) {
                    imgFile = "../image/file_logo/unknow.png";
                } else {
                    imgFile = "../image/file_logo/" + value.toLowerCase() + ".png";
                }
                return "<img class='howToImg' title='Click to download' onclick='window.open(\"HowTo!downloadFile?filename=" + rowData.filename + "\",\"_blank\")' src='" + imgFile + "'/>";
            }},
            { text: 'Name', dataField: 'filename', width: 400 },
            { text: 'Size', dataField: 'fileSize', width: 130, cellsRenderer: function (row, column, value, rowData){
                if(value < 950) {
                    return value + "Byte";
                } else if(value > 950 && value < 1024*1024) {
                    return (value/1024).toFixed(2) + "KB";
                } else if(value>1024*1024) {
                    return (value/1024/1024).toFixed(2) + "MB";
                }
            }},
            { text: 'Date(GMT+8)', dataField: 'fileTimestamp', cellsFormat: 'yyyy-MM-dd HH:mm:ss', width: 200 }
        ]
    });
}

function showServer(environment, theme) {
    var source =
    {
        datatype: "json",
        url: "KnowledgeBase!listServer",
        data: {'environment': environment},
        datafields: [
            { name: 'title' },
            { name: 'address' },
            { name: 'hostname' },
            { name: 'description' },
            { name: 'lasteditby' },
            { name: 'lasteditdt' }
        ],
        root: 'serverList'
    };
    var dataAdapter = new $.jqx.dataAdapter(source);
    dataAdapter.dataBind();
    var test = dataAdapter.records;
    $('#' + environment).jqxDataTable(
        {
            width: 1150,
            theme: theme,
            source: dataAdapter,
            enableBrowserSelection: true,
            altrows: true,
            columns: [
                { text: 'Title', datafield: 'title', width: 200 },
                { text: 'Address', datafield: 'address', width: 300 },
                { text: 'Hostname', datafield: 'hostname', width: 150 },
                { text: 'Description', datafield: 'description', width: 500 }
            ]
        });
    $('#' + environment).on('bindingComplete', function (event) {
        resetWindowHeight();
    });
    resetWindowHeight();
}