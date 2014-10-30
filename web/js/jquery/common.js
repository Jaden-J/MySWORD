jQuery.stringUtils = {
    charAt: function(str, index) {
        if( !this.empty(str) && ((typeof str) == 'string') && ($.format.positiveInteger(index) || index === 0) && str.length > index) {
            return str[index];
        } else {
            return null;
        }
    },
    endWith: function(str1, str2) {
		if( this.empty(str1) || this.empty(str2) ) {
			return false;
		}
        if(str1.match(new RegExp(str2 + "$")) !== null) {
            return true;
        } else {
            return false;
        }
    },
    empty: function(str) {
        if(str === "" || str ===null) {
            return true;
        } else {
            return false;
        }
    },
    startsWith: function(str1, str2) {
		if( this.empty(str1) || this.empty(str2) ) {
			return false;
		}
        if(str1.match(new RegExp("^" + str2)) !== null) {
            return true;
        } else {
            return false;
        }
    },
    trim: function(str1, character) {
        if( (!this.empty(character) && character.length > 1) || this.empty(str1) || this.empty(character) ) {
            return str1;
        }
        if(str1.length > 0) {
            var reg = new RegExp("(^" + (this.empty(character)?' ':character) + "+)|(" + (this.empty(character)?' ':character) + "+$)", "g");
            return str1.replace(reg, "");
            //return str1.replace(/(^ )|( $)/g, "");
        } else {
            return str1;
        }
    },
    trimLeft: function(str1, character) {
        if( (!this.empty(character) && character.length > 1) || this.empty(str1) || this.empty(character) ) {
            return str1;
        }
        if(str1.length > 0) {
            var reg = new RegExp("^"+(this.empty(character)?' ':character) + "+");
            return str1.replace(reg, "");
        }    else {
            return str1;
        }
    }  ,
    trimRight: function(str1, character) {
        if( (!this.empty(character) && character.length > 1) || this.empty(str1) || this.empty(character) ) {
            return str1;
        }
        if(str1.length > 0) {
            var reg = new RegExp((this.empty(character)?' ':character) + "+$");
            return str1.replace(reg, "");
            //return str1.replace(/ $/, "");
        }        else {
            return str1;
        }
    },
    padLeft: function(str1, character, length) {
        if( (!this.empty(character) && character.length > 1) || (str1.length >= length) || this.empty(character) ) {
            return str1;
        }
		if( this.empty(str1) ) {
			str1='';
		}
		for(var i = str1.length; i < length; i++) {
			str1 = character + str1;
		}
		return str1;
    },
	padRight: function(str1, character, length) {
        if( (!this.empty(character) && character.length > 1) || (str1.length >= length) || this.empty(character) ) {
            return str1;
        }
		if( this.empty(str1) ) {
			str1='';
		}
		for(var i = str1.length; i < length; i++) {
			str1 = str1 + character;
		}
		return str1;
    }
};

jQuery.dateUtils = {
    getDateString: function(pattern) {
        var date = new Date();
        var tz;
        var TZ;
        if (date.getTimezoneOffset() < 0) {
            TZ = "GMT+" + ((date.getTimezoneOffset()/60)<-9?(date.getTimezoneOffset()/60)*-1:("0"+(date.getTimezoneOffset()/60).toString().replace('-', ''))) + ":00";
            tz = "GMT+" + (date.getTimezoneOffset()/60)*-1;
        } else if (date.getTimezoneOffset() === 0) {
            TZ = "GMT+00:00";
            tz = "GMT";
        } else {
            TZ = "GMT-" + ((date.getTimezoneOffset()/60)>9?(date.getTimezoneOffset()/60):("0"+(date.getTimezoneOffset()/60).toString())) + ":00";
            tz = "GMT-" + (date.getTimezoneOffset()/60);
        }
        var result = pattern.replace(/y!y|^y{3,}/g, date.getFullYear().toString());
        result = result.replace(/yy/g, date.getFullYear().toString().substr(2, 2));
        result = result.replace(/M{2,}/g, (date.getMonth() + 1).toString().length==2?(date.getMonth() + 1).toString():("0" + (date.getMonth() + 1).toString()));
        result = result.replace(/M/g, (date.getMonth() + 1)>9?(date.getMonth() + 1).toString().substr(1, 2):(date.getMonth() + 1).toString());
        result = result.replace(/d{2,}/g, date.getDate().toString().length==2?date.getDate().toString():("0" + date.getDate().toString()));
        result = result.replace(/d/g, date.getDate()>9?date.getDate().toString().substr(1, 2):date.getDate().toString());
        result = result.replace(/H{2,}/g, date.getHours().toString().length==2?date.getHours().toString():("0" + date.getHours().toString()));
        result = result.replace(/H/g, date.getHours()>9?date.getHours().toString().substr(1, 2):date.getHours().toString());
        result = result.replace(/h{2,}/g, (date.getHours() - 12)>9?(date.getHours() - 12).toString():("0" + (date.getHours() - 12).toString()));
        result = result.replace(/h/g, (date.getHours() - 12)%10);
        result = result.replace(/m{2,}/g, date.getMinutes().toString().length==2?date.getMinutes().toString():("0" + date.getMinutes().toString()));
        result = result.replace(/m/g, date.getMinutes()>9?date.getMinutes().toString().substr(1, 2):date.getMinutes().toString());
        result = result.replace(/s{2,}/g, date.getSeconds().toString().length==2?date.getSeconds().toString():("0" + date.getSeconds().toString()));
        result = result.replace(/s/g, date.getSeconds()>9?date.getSeconds().toString().substr(1, 2):date.getSeconds().toString());
        result = result.replace(/S{3,}/g, date.getMilliseconds().toString().length==2?date.getSeconds().toString():("0" + date.getSeconds().toString()));
        result = result.replace(/SS/g, date.getSeconds().toString().substr(1, 3));
        result = result.replace(/S/g, date.getSeconds().toString().substr(2, 3));
        result = result.replace(/z/g, tz);
        result = result.replace(/Z/g, TZ);
        return result;
    }
};

jQuery.format = {
    integer: function(number) {
        return new RegExp("^-?\\d+$").test(number);
    },
    positiveInteger: function(number) {
        return new RegExp("^[0-9]*[1-9][0-9]*$").test(number);
    },
    negativeInteger: function(number) {
        var reg = new RegExp("^-[0-9]*[1-9][0-9]*$");
        return new RegExp("^-[0-9]*[1-9][0-9]*$").test(number);
    },
    floating: function(number) {
        return new RegExp("^(-?)[0-9]+\.[0-9]+$").test(number);
        //return new RegExp("^(-?\\d+)(\\.\\d+)?$").test(number);
    },
    positiveFloating: function(number) {
        return new RegExp("^[0-9]+\.[0-9]+$").test(number);
        //return new RegExp("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$").test(number);
    },
    negativeFloating: function(number) {
        return new RegExp("^-[0-9]+\.[0-9]+$").test(number);
        //return new RegExp("^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$").test(number);
    },
    email: function(email) {
        return new RegExp("^[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$").test(email);
    },
    age: function(age) {
        return new RegExp("^(?:[1-9][0-9]?|1[01][0-9]|120)$").test(age);
    }
};

jQuery.url = {
    getParam: function(name) {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r !== null) {
            return decodeURI(r[2]);
        }
        return '';
    }
};

$.validator.addMethod('datetime', function(value, element, params){
	return this.optional(element) || value.match('[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}');
}, "Please input the datetime as 'yyyy-MM-dd HH:mm:ss'");