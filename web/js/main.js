function openItem(url, container) {
	if(container === null || container === '') {
		container = '_self';
	}
	window.open(url, container);
}

function post(url, container, params) {
    var temp = document.createElement("form");
    url = url.replace(/^\/+/g, "/");
    temp.action = url;
    temp.target = container;
    temp.method = "post";
    temp.style.display = "none";
    for (var x in params) {
        var opt = document.createElement("textarea");
        opt.name = x;
        opt.value = params[x];
        temp.appendChild(opt);
    }
    document.body.appendChild(temp);
    temp.submit();
    document.body.removeChild(temp);
    return temp;
}

function resetWindowHeight(height){
    if(height) {
        window.frameElement.height = height;
        return;
    }
    if(window.frameElement.height < window.document.body.clientHeight) {
        window.frameElement.height = window.document.body.clientHeight + 20;
    } else if(window.document.body.clientHeight<600) {
        window.frameElement.height = 600;
    } else {
        window.frameElement.height = window.document.body.clientHeight + 20;
    }
}

function getX(e) {
    e = e || window.event;
    return e.pageX || e.clientX + document.body.scrollLeft;
}

function getY(e) {
    e = e|| window.event;
    return e.pageY || e.clientY + document.body.scrollTop;
}

function existId(id) {
    if(document.getElementById(id)){return true;}else{return false;}
}